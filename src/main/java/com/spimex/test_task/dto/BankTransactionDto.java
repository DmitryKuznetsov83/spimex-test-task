package com.spimex.test_task.dto;

import com.spimex.test_task.enums.payment_type.ServicePaymentType;
import com.spimex.test_task.enums.transaction_status.TransactionStatus;
import com.spimex.test_task.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransactionDto {
	private Long id;
	private TransactionStatus transactionStatus;
	private TransactionType transactionType;
	private ServicePaymentType servicePaymentType;
	private LocalDateTime createdOn;
	private Long baseTransactionId;
	private Integer replenishmentSum;
	private Integer withdrawSum;
	private Integer replenishmentBonus;
	private Integer withdrawBonus;
}
