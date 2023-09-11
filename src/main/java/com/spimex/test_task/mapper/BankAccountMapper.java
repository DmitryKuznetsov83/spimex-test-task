package com.spimex.test_task.mapper;

import com.spimex.test_task.dto.BankAccountDto;
import com.spimex.test_task.model.BankAccount;
import org.modelmapper.ModelMapper;

public class BankAccountMapper {
	private static final ModelMapper modelMapper = new ModelMapper();

	public static BankAccountDto mapToAccountDto(BankAccount bankAccount) {
		return modelMapper.map(bankAccount, BankAccountDto.class);
	}

}
