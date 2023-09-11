package com.spimex.test_task.service.transaction_calculation;

import com.spimex.test_task.enums.payment_type.ServicePaymentType;
import com.spimex.test_task.enums.transaction_status.TransactionStatusInternalModel;
import com.spimex.test_task.internal_model.BankTransactionInternalModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankTransactionCalculationServiceImpl implements BankTransactionCalculationService{
	private static final Integer BONUS_SHOP = 10;
	private static final Integer BONUS_ONLINE_WHEN_PAYMENT_LESS_OR_EQUALS_300 = 17;
	private static final Integer BONUS_ONLINE_WHEN_PAYMENT_MORE_THEN_300 = 30;
	private static final Integer COMMISSION_ONLINE_WHEN_PAYMENT_LESS_OR_EQUALS_20 = 10;

	@Override
	public BankTransactionInternalModel CalculateTransaction(BankTransactionInternalModel transaction) {
		if (!TransactionStatusInternalModel.NEW.equals(transaction.getTransactionStatus())) {
			throw new IllegalArgumentException("Incorrect transaction status");
		}

		Integer withdrawSum = transaction.getWithdrawSum();

		// бонус
		Integer bonus = 0;
		if (ServicePaymentType.SHOP.equals(transaction.getServicePaymentType())) {
			bonus = BONUS_SHOP;
		} else if (ServicePaymentType.ONLINE.equals(transaction.getServicePaymentType())) {
			if (withdrawSum <= 300) {
				bonus = BONUS_ONLINE_WHEN_PAYMENT_LESS_OR_EQUALS_300;
			} else {
				bonus = BONUS_ONLINE_WHEN_PAYMENT_MORE_THEN_300;
			}
		}
		transaction.setReplenishmentBonus(withdrawSum * bonus / 100);

		// коммиссия
		Integer commission = 0;
		if (ServicePaymentType.ONLINE.equals(transaction.getServicePaymentType())) {
			if (withdrawSum <= 20) {
				commission = COMMISSION_ONLINE_WHEN_PAYMENT_LESS_OR_EQUALS_20;
			}
		}
		transaction.setCommission(withdrawSum * commission / 100);
		transaction.setTransactionStatus(TransactionStatusInternalModel.CALCULATED);

		return transaction;
	}
}
