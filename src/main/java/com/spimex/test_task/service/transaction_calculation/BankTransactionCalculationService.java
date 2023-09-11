package com.spimex.test_task.service.transaction_calculation;

import com.spimex.test_task.internal_model.BankTransactionInternalModel;

public interface BankTransactionCalculationService {
	BankTransactionInternalModel CalculateTransaction(BankTransactionInternalModel transaction);
}
