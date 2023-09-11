package com.spimex.test_task.mapper;

import com.spimex.test_task.dto.BankTransactionDto;
import com.spimex.test_task.model.BankTransaction;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BankTransactionMapper {

	private static final ModelMapper modelMapper = new ModelMapper();

	public static BankTransactionDto mapToTransactionDto(BankTransaction bankTransaction) {
		return modelMapper.map(bankTransaction, BankTransactionDto.class);
	}

	public static List<BankTransactionDto> mapToTransactionDtoList(List<BankTransaction> transactions) {
		return transactions.stream().map(BankTransactionMapper::mapToTransactionDto).collect(Collectors.toList());
	}
}
