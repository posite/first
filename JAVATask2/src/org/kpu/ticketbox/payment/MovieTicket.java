package org.kpu.ticketbox.payment;

public class MovieTicket {
	public static final char SEAT_EMPTY_MARK ='-';
	public static final char SEAT_RESERVED_MARK='R';
	public static final char SEAT_PAY_COMPLETION_MARK='$';
	private int nRow;
	private int nCol;
	private char cStatus;
	private int nReservedId;
	public MovieTicket() {

		this.cStatus=SEAT_EMPTY_MARK;
	}
	public char getcStatus() {
		return cStatus;
	}
	public void setcStatus(char cStatus) {
		this.cStatus=cStatus;
	}
	public void setSeat(int row, int col) {
		this.nRow=row;
		this.nCol=col;
	}
	public void setReservedId(int id) {
		this.nReservedId=id;
	}
	public int getnReservedId() {return nReservedId;}
}
