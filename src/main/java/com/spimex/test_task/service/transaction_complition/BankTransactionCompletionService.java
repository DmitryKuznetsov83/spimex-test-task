package com.spimex.test_task.service.transaction_complition;

import com.spimex.test_task.internal_model.BankTransactionInternalModel;
import com.spimex.test_task.model.BankTransaction;

import java.util.List;

public interface BankTransactionCompletionService {
	List<BankTransaction> completeTransaction(BankTransactionInternalModel calculatedTransaction);
}
