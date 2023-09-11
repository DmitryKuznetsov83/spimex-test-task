package com.spimex.test_task.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer sum;
	private Integer bonus;

	public BankAccount(Integer sum, Integer bonus) {
		this.sum = sum;
		this.bonus = bonus;
	}
}

