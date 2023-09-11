package com.spimex.test_task.repository;

import com.spimex.test_task.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountJpaRepository extends JpaRepository<BankAccount, Long> {

}
