package org.kpu.ticketbox.payment;

public class CardPay implements Pay {
	public static final double CARD_COMMISION = 0.15;
	@Override
	public Receipt charge(String name, String product, String number, double amount) {
		Receipt receipt= new Receipt(name,product,"CardPay",amount,(amount*CARD_COMMISION+amount));
		return receipt;
	}

}
