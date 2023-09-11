package com.spimex.test_task.enums.enum_converter;

import com.spimex.test_task.enums.payment_type.ClientPaymentType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToClientPaymentTypeConverter implements Converter<String, ClientPaymentType> {
	@Override
	public ClientPaymentType convert(String action) {
			return ClientPaymentType.valueOf(action.toUpperCase());
	}
}
