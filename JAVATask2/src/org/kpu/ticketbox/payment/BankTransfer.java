package org.kpu.ticketbox.payment;

public class BankTransfer implements Pay {
	public static final double BANK_TRANSFER_COMMISION = 0.1;
	
	public Receipt charge(String name, String product, String number, double amount) {
		Receipt receipt= new Receipt(name,product,"BankTransfer",amount,(amount*BANK_TRANSFER_COMMISION+amount));
		return receipt;
	}

}
