package org.kpu.ticketbox.payment;

public class Receipt {
	String client;
	String productName;
	String payMethod;
	double subTotalAmount;
	double totalAmount;
	public Receipt(String client, String productName, String payMethod, double subTotal, double total) {
		this.client=client;
		this.productName=productName;
		this.payMethod = payMethod;
		this.subTotalAmount=subTotal;
		this.totalAmount=total;
	}
	public String toString() {
		return "client=" + client + ", productName=" + productName + ",payMethod=" + payMethod + ", subTotalAmount=" + subTotalAmount + ",totalAmount=" + totalAmount+"\n";
	}
	public double getTotalAmount() {
		return totalAmount;
	}
}
