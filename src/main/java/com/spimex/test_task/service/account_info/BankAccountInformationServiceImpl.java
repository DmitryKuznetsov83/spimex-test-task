package com.spimex.test_task.service.account_info;

import com.spimex.test_task.model.BankAccount;
import com.spimex.test_task.model.BankTransaction;
import com.spimex.test_task.repository.BankAccountJpaRepository;
import com.spimex.test_task.repository.BankTransactionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankAccountInformationServiceImpl implements BankAccountInformationService {

	private final BankTransactionJpaRepository bankTransactionJpaRepository;
	private final BankAccountJpaRepository bankAccountJpaRepository;
	private final Long ACCOUNT_ID = 1L;

	@Override
	public BankAccount getBalance() {
		return bankAccountJpaRepository.findById(ACCOUNT_ID).get();
	}

	@Override
	public List<BankTransaction> getTransactions() {
		return bankTransactionJpaRepository.findAll();
	}
}