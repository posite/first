package org.kpu.ticketbox.payment;

public class MobilePay implements Pay {
	public static final double MOBILE_COMMISION = 0.2;
	@Override
	public Receipt charge(String name, String product, String number, double amount) {
		Receipt receipt= new Receipt(name,product,"MobilePay",amount,(amount*MOBILE_COMMISION+amount));
		return receipt;
	}

}
