package com.spimex.test_task.service.transaction_processing;

import com.spimex.test_task.enums.transaction_status.TransactionStatusInternalModel;
import com.spimex.test_task.enums.payment_type.ClientPaymentType;
import com.spimex.test_task.enums.payment_type.ServicePaymentType;
import com.spimex.test_task.enums.TransactionType;
import com.spimex.test_task.internal_model.BankTransactionInternalModel;
import com.spimex.test_task.model.BankTransaction;
import com.spimex.test_task.service.transaction_complition.BankTransactionCompletionService;
import com.spimex.test_task.service.transaction_calculation.BankTransactionCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BankTransactionProcessingServiceImpl implements BankTransactionProcessingService {

	private final BankTransactionCalculationService bankTransactionCalculationService;
	private final BankTransactionCompletionService bankTransactionCompletionService;

	@Override
	public BankTransaction topUpAccount(Integer sum) {
		BankTransactionInternalModel newTransaction = BankTransactionInternalModel.builder()
				.transactionType(TransactionType.REPLENISHMENT)
				.servicePaymentType(ServicePaymentType.SIMPLE_REPLENISHMENT)
				.transactionStatus(TransactionStatusInternalModel.NEW)
				.createdOn(LocalDateTime.now())
				.replenishmentSum(sum)
				.build();
		return processNewTransaction(newTransaction).get(0);
	}

	@Override
	public List<BankTransaction> doPayment(ClientPaymentType clientPaymentType, Integer sum) {
		BankTransactionInternalModel newTransaction = BankTransactionInternalModel.builder()
				.transactionType(TransactionType.WITHDRAW)
				.servicePaymentType(clientPaymentType.getServicePaymentType())
				.transactionStatus(TransactionStatusInternalModel.NEW)
				.createdOn(LocalDateTime.now())
				.withdrawSum((ServicePaymentType.BONUS.equals(clientPaymentType.getServicePaymentType())) ? 0 : sum)
				.withdrawBonus((ServicePaymentType.BONUS.equals(clientPaymentType.getServicePaymentType())) ? sum : 0)
				.build();
		return processNewTransaction(newTransaction);
	}

	// PRIVATE
	private List<BankTransaction> processNewTransaction(BankTransactionInternalModel newTransaction) {
		if (!TransactionStatusInternalModel.NEW.equals(newTransaction.getTransactionStatus())) {
			throw new IllegalArgumentException("Incorrect transaction status");
		}
		BankTransactionInternalModel calculatedTransaction = calculateTransactionIfNeeded(newTransaction);
		return bankTransactionCompletionService.completeTransaction(calculatedTransaction);
	}

	private BankTransactionInternalModel calculateTransactionIfNeeded(BankTransactionInternalModel newTransaction) {
		if (checkIfTransactionNeedsCalculation(newTransaction)) {
			return bankTransactionCalculationService.CalculateTransaction(newTransaction);
		} else {
			newTransaction.setTransactionStatus(TransactionStatusInternalModel.CALCULATED);
			return newTransaction;
		}
	}

	private boolean checkIfTransactionNeedsCalculation(BankTransactionInternalModel transaction) {
		return !(TransactionType.REPLENISHMENT.equals(transaction.getTransactionType()));
	}
}
