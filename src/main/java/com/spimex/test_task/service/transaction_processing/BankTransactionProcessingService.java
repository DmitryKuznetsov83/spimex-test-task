package com.spimex.test_task.service.transaction_processing;

import com.spimex.test_task.enums.payment_type.ClientPaymentType;
import com.spimex.test_task.model.BankTransaction;

import java.util.List;

public interface BankTransactionProcessingService {

	BankTransaction topUpAccount(Integer sum);

	List<BankTransaction> doPayment(ClientPaymentType paymentType, Integer sum);

}
