package com.spimex.test_task.repository;

import com.spimex.test_task.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionJpaRepository extends JpaRepository<BankTransaction, Long> {


}
