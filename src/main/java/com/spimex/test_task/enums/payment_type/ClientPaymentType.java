package com.spimex.test_task.enums.payment_type;

public enum ClientPaymentType {
	SHOP(ServicePaymentType.SHOP),
	ONLINE(ServicePaymentType.ONLINE),
	BONUS(ServicePaymentType.BONUS);

	private final ServicePaymentType servicePaymentType;

	ClientPaymentType(ServicePaymentType servicePaymentType) {
		this.servicePaymentType = servicePaymentType;
	}

	public ServicePaymentType getServicePaymentType() {
		return servicePaymentType;
	}
}
