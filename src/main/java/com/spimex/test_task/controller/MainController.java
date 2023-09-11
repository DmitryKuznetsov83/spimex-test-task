package com.spimex.test_task.controller;

import com.spimex.test_task.dto.BankAccountDto;
import com.spimex.test_task.dto.BankTransactionDto;
import com.spimex.test_task.enums.payment_type.ClientPaymentType;
import com.spimex.test_task.mapper.BankAccountMapper;
import com.spimex.test_task.mapper.BankTransactionMapper;
import com.spimex.test_task.service.account_info.BankAccountInformationService;
import com.spimex.test_task.service.transaction_processing.BankTransactionProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@RequiredArgsConstructor
@Validated
public class MainController {

	private final BankTransactionProcessingService bankTransactionProcessingService;
	private final BankAccountInformationService bankAccountInformationService;

	@PostMapping("/topUpAccount/{sum}")
	@ResponseStatus(HttpStatus.CREATED)
	public BankTransactionDto topUpAccount(@PathVariable @Positive Integer sum) {
		return BankTransactionMapper.mapToTransactionDto(bankTransactionProcessingService.topUpAccount(sum));
	}

	@PostMapping("/payment/{clientPaymentType}/{sum}")
	@ResponseStatus(HttpStatus.CREATED)
	public List<BankTransactionDto> doPayment(@PathVariable ClientPaymentType clientPaymentType, @PathVariable @Positive Integer sum) {
		return BankTransactionMapper.mapToTransactionDtoList(bankTransactionProcessingService.doPayment(clientPaymentType, sum));
	}

	@GetMapping("/bankAccountOfEMoney")
	public Integer getBonuses() {
		return bankAccountInformationService.getBalance().getBonus();
	}

	@GetMapping("/money")
	public BankAccountDto getMoney() {
		return BankAccountMapper.mapToAccountDto(bankAccountInformationService.getBalance());
	}

	@GetMapping("/transactions")
	public List<BankTransactionDto> getAllTransactions() {
		return BankTransactionMapper.mapToTransactionDtoList(bankAccountInformationService.getTransactions());
	}

}