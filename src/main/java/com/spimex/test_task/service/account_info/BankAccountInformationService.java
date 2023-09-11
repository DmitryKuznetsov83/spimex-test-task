package com.spimex.test_task.service.account_info;

import com.spimex.test_task.model.BankAccount;
import com.spimex.test_task.model.BankTransaction;

import java.util.List;

public interface BankAccountInformationService {
	BankAccount getBalance();

	List<BankTransaction> getTransactions();
}
