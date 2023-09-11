package com.spimex.test_task.internal_model;

import com.spimex.test_task.enums.transaction_status.TransactionStatusInternalModel;
import com.spimex.test_task.enums.payment_type.ServicePaymentType;
import com.spimex.test_task.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransactionInternalModel {
	@NotNull
	private TransactionType transactionType;
	@NotNull
	private ServicePaymentType servicePaymentType;
	@NotNull
	private TransactionStatusInternalModel transactionStatus;
	@NotNull
	private LocalDateTime createdOn;
	@Builder.Default
	private Integer replenishmentSum = 0;
	@Builder.Default
	private Integer withdrawSum = 0;
	@Builder.Default
	private Integer replenishmentBonus = 0;
	@Builder.Default
	private Integer withdrawBonus = 0;
	@Builder.Default
	private Integer commission = 0;
}
