package com.spimex.test_task.model;

import com.spimex.test_task.enums.payment_type.ServicePaymentType;
import com.spimex.test_task.enums.transaction_status.TransactionStatus;
import com.spimex.test_task.enums.TransactionType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Table(name = "BankTransaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionType transactionType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ServicePaymentType servicePaymentType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TransactionStatus transactionStatus;

	@Column(nullable = false)
	private LocalDateTime createdOn;

	@ManyToOne
	private BankTransaction baseTransaction;

	@PositiveOrZero
	private Integer replenishmentSum;

	@PositiveOrZero
	private Integer withdrawSum;

	@PositiveOrZero
	private Integer replenishmentBonus;

	@PositiveOrZero
	private Integer withdrawBonus;
}