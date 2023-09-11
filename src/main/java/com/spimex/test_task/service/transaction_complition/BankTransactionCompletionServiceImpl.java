package com.spimex.test_task.service.transaction_complition;

import com.spimex.test_task.enums.transaction_status.TransactionStatus;
import com.spimex.test_task.enums.payment_type.ServicePaymentType;
import com.spimex.test_task.enums.TransactionType;
import com.spimex.test_task.enums.transaction_status.TransactionStatusInternalModel;
import com.spimex.test_task.internal_model.BankTransactionInternalModel;
import com.spimex.test_task.model.BankAccount;
import com.spimex.test_task.model.BankTransaction;
import com.spimex.test_task.repository.BankAccountJpaRepository;
import com.spimex.test_task.repository.BankTransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BankTransactionCompletionServiceImpl implements BankTransactionCompletionService {
	private final BankTransactionJpaRepository bankTransactionJpaRepository;
	private final BankAccountJpaRepository bankAccountJpaRepository;
	private final Long ACCOUNT_ID = 1L;

	@Override
	@Transactional
	public List<BankTransaction> completeTransaction(BankTransactionInternalModel calculatedTransaction) {
		if (!TransactionStatusInternalModel.CALCULATED.equals(calculatedTransaction.getTransactionStatus())) {
			throw new IllegalArgumentException("Incorrect transaction status");
		}
		TransactionStatus transactionStatus = validateTransaction(calculatedTransaction);
		List<BankTransaction> bankTransactionList = transformTransaction(calculatedTransaction, transactionStatus);
		return databaseUpdate(bankTransactionList, transactionStatus);
	}

	@PostConstruct
	@Transactional
	public void createAccount() {
		if (!bankAccountJpaRepository.findById(ACCOUNT_ID).isPresent()) {
			bankAccountJpaRepository.save(new BankAccount(0, 0));
		}
	}

	// PRIVATE
	private TransactionStatus validateTransaction(BankTransactionInternalModel transaction) {
		if (transaction.getTransactionType().equals(TransactionType.REPLENISHMENT)) {
			return TransactionStatus.OK;
		} else {
			Integer currentBalance;
			Integer wrightOffAmount;
			BankAccount bankAccount = bankAccountJpaRepository.findById(ACCOUNT_ID).get();
			if (!transaction.getServicePaymentType().equals(ServicePaymentType.BONUS)) {
				currentBalance = bankAccount.getSum();
				wrightOffAmount = transaction.getWithdrawSum() + transaction.getCommission();
			} else {
				currentBalance = bankAccount.getBonus();
				wrightOffAmount = transaction.getWithdrawBonus();
			}
			return (currentBalance >= wrightOffAmount ? TransactionStatus.OK : TransactionStatus.FAILED);
		}
	}

	private List<BankTransaction> transformTransaction(BankTransactionInternalModel calculatedTransaction, TransactionStatus transactionStatus) {
		// основная транзакция
		BankTransaction mainTransaction = BankTransaction.builder()
				.transactionType(calculatedTransaction.getTransactionType())
				.servicePaymentType(calculatedTransaction.getServicePaymentType())
				.transactionStatus(transactionStatus)
				.createdOn(calculatedTransaction.getCreatedOn())
				.replenishmentSum(Optional.ofNullable(calculatedTransaction.getReplenishmentSum()).orElse(0))
				.withdrawSum(Optional.ofNullable(calculatedTransaction.getWithdrawSum()).orElse(0))
				.replenishmentBonus(Optional.ofNullable(calculatedTransaction.getReplenishmentBonus()).orElse(0))
				.withdrawBonus(Optional.ofNullable(calculatedTransaction.getWithdrawBonus()).orElse(0))
				.build();
		//BankTransaction savedMainTransaction = bankTransactionJpaRepository.save(mainTransaction);

		// коммисия
		Integer commission = calculatedTransaction.getCommission();
		if (commission > 0) {
			BankTransaction commissionTransaction = BankTransaction.builder()
					.transactionType(TransactionType.WITHDRAW)
					.servicePaymentType(ServicePaymentType.COMMISSION)
					.transactionStatus(transactionStatus)
					.createdOn(calculatedTransaction.getCreatedOn())
					.replenishmentSum(0)
					.withdrawSum(commission)
					.replenishmentBonus(0)
					.withdrawBonus(0)
					.baseTransaction(mainTransaction)
					.build();
			//BankTransaction savedCommissionTransaction = bankTransactionJpaRepository.save(commissionTransaction);
			return List.of(mainTransaction, commissionTransaction);
		} else {
			return Collections.singletonList(mainTransaction);
		}
	}

	private List<BankTransaction> databaseUpdate(List<BankTransaction> bankTransactionList, TransactionStatus transactionStatus) {
		// сохраняем транзакции
		List<BankTransaction> savedTransactionList = new ArrayList<>(2);
		bankTransactionList.forEach(t -> {
			BankTransaction savedTransaction = bankTransactionJpaRepository.save(t);
			savedTransactionList.add(savedTransaction);
		});

		// изменяем баланс
		if (transactionStatus.equals(TransactionStatus.OK)) {
			int replenishmentSum = bankTransactionList.stream().mapToInt(BankTransaction::getReplenishmentSum).sum();
			int withdrawSum = bankTransactionList.stream().mapToInt(BankTransaction::getWithdrawSum).sum();
			int replenishmentBonus = bankTransactionList.stream().mapToInt(BankTransaction::getReplenishmentBonus).sum();
			int withdrawBonus = bankTransactionList.stream().mapToInt(BankTransaction::getWithdrawBonus).sum();

			BankAccount bankAccount = bankAccountJpaRepository.findById(ACCOUNT_ID).get();
			bankAccount.setSum(bankAccount.getSum() + replenishmentSum - withdrawSum);
			bankAccount.setBonus(bankAccount.getBonus() + replenishmentBonus - withdrawBonus);
			bankAccountJpaRepository.save(bankAccount);
		}

		return savedTransactionList;
	}

}
