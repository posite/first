package org.kpu.ticketbox.cinema;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.kpu.ticketbox.payment.*;
import org.kpu.ticketbox.payment.MovieTicket;
import org.kpu.ticketbox.util.*;

public abstract class Screen {
	protected int nTicketPrice;
	protected int nRowMax;
	protected int nColMax;
	protected String strMovieName;
	protected String strMovieStory;
	protected MovieTicket[][] seatArray;
	private int nCurrentReservedId = 100;
	private HashMap<Integer, Receipt>receiptMap = new HashMap<Integer,Receipt>();
	public abstract void showMovieInfo();
	
	Screen(String name, String story, int price, int row, int col){
		this.strMovieName=name;
		this.strMovieStory=story;
		this.nTicketPrice=price;
		this.nRowMax=row;
		this.nColMax=col;
		seatArray= new MovieTicket[nRowMax][nColMax];
		for(int i=0;i<nRowMax;i++) {
			for(int j=0;j<nColMax;j++) {
				seatArray[i][j]= new MovieTicket();
				seatArray[i][j].setSeat(row+1, col+1);
			}
		}
	}
	public void showScreenMenu() {
		System.out.println("-------------------------------");
		System.out.println("��ȭ �޴� -"+this.strMovieName);
		System.out.println("-------------------------------");
		System.out.println("1. �� ��ȭ ����");
		System.out.println("2. �¼� ���� ��Ȳ");
		System.out.println("3. �¼� ���� �ϱ�");
		System.out.println("4. �¼� ���� �ϱ�");
		System.out.println("5. ���� �޴� �̵�");
	}
	public void showSeatMap() {
		System.out.println("\t[ �¼� ���� ��Ȳ]");
		System.out.print("\t");
		for(int i=0;i<nRowMax;i++) {
			System.out.print("["+(i+1)+"]");
		}
		System.out.println("");
		for(int i=0;i<nRowMax;i++) {
			System.out.print("["+(i+1)+"]\t");
			for(int j=0;j<nColMax;j++) {
				System.out.print("["+seatArray[i][j].getcStatus()+"]");
			}
			System.out.println();
		}
	}
	public void reserveTicket() {
		Scanner scan = new Scanner(System.in);
		System.out.println("[ �¼� ����]");
		System.out.print("�¼� �� ��ȣ �Է� (1 - "+nRowMax+") : ");
		int row = scan.nextInt();
		System.out.print("�¼� �� ��ȣ �Է� (1 - "+nColMax+") : ");
		int col = scan.nextInt();
		if(seatArray[row-1][col-1].getcStatus()==MovieTicket.SEAT_RESERVED_MARK||seatArray[row-1][col-1].getcStatus()==MovieTicket.SEAT_PAY_COMPLETION_MARK) {
			System.out.println("�̹� ����� �¼��Դϴ�.");
			return;
		}
		seatArray[row-1][col-1].setReservedId(nCurrentReservedId);
		seatArray[row-1][col-1].setcStatus(MovieTicket.SEAT_RESERVED_MARK);
		System.out.println("��["+row+"] ��["+col+"] "+nCurrentReservedId+"���� ��ȣ�� �����Ǿ����ϴ�.");
		nCurrentReservedId++;
	}
	public void payment() {
		Scanner scan = new Scanner(System.in);
		Receipt receipt;
		while(true) {
			System.out.println(" [ �¼� ���� ���� ]");
			System.out.print("���� ��ȣ �Է� ");
			int id = scan.nextInt();
			for(int i=0;i<nRowMax;i++) {
				for(int j=0;j<nColMax;j++) {
					if(id==seatArray[i][j].getnReservedId()) {
						if(seatArray[i][j].getcStatus()==MovieTicket.SEAT_RESERVED_MARK) {
							System.out.println("-------------------------------");
							System.out.println("���� ��� ����");
							System.out.println("-------------------------------");
							System.out.println("1. ���� ��ü");
							System.out.println("2. ī�� ����");
							System.out.println("3. ����� ����");
							System.out.print("���� ��� �Է�(1~3) : ");
							try {
								int select = scan.nextInt();
								switch(select) {
								case Pay.BANK_TRANSFER_PAYMENT:
									System.out.println("[ ���� ��ü ]");
									System.out.print("�̸� �Է�: ");
									String bName = scan.nextLine();
									System.out.print("���� ��ȣ �Է�(12�ڸ���) : ");
									String bNumber = scan.nextLine();
									Pay bPay = new BankTransfer();
									receipt = bPay.charge(bName, strMovieName, bNumber,nTicketPrice);
									receiptMap.put(id, receipt);
									seatArray[i][j].setcStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
									System.out.println("���� ������ �Ϸ�Ǿ����ϴ�. : "+receipt.getTotalAmount()+"��");
									return;
								case Pay.CREDIT_CARD_PAYMENT:
									System.out.println("[ ī�� ���� ]");
									System.out.print("�̸� �Է�: ");
									String cName = scan.nextLine();
									System.out.print("ī�� ��ȣ �Է�(12�ڸ���) : ");
									String cNumber = scan.nextLine();
									Pay cPay = new CardPay();
									receipt = cPay.charge(cName, strMovieName, cNumber,nTicketPrice);
									receiptMap.put(id, receipt);
									seatArray[i][j].setcStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
									System.out.println("ī�� ������ �Ϸ�Ǿ����ϴ�. : "+receipt.getTotalAmount()+"��");
									return;
								case Pay.MOBILE_PHONE_PAYMENT:
									System.out.println("[ ����� ���� ]");
									System.out.print("�̸� �Է�: ");
									String mName = scan.nextLine();
									System.out.print("�ڵ��� ��ȣ �Է�(11�ڸ���) : ");
									String mNumber = scan.nextLine();
									Pay mPay = new MobilePay();
									receipt = mPay.charge(mName, strMovieName, mNumber,nTicketPrice);
									receiptMap.put(id, receipt);
									seatArray[i][j].setcStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
									System.out.println("����� ������ �Ϸ�Ǿ����ϴ�. : "+receipt.getTotalAmount()+"��");
									return;
								}
							}catch(InputMismatchException e) {
								System.out.println("������ �Է����ּ���");
								scan = new Scanner(System.in);
								continue;
							}
						}else if(seatArray[i][j].getcStatus()==MovieTicket.SEAT_PAY_COMPLETION_MARK) {
							System.out.println("�̹� ����� �¼��Դϴ�.");
							return;
						}
					}
				}
			}
			System.out.println("����� ��ȣ�� �ƴմϴ�.");
			break;
		}
	}
	public void Backup(String file) {
		BackupWriter b = new BackupWriter();
		b.backupFile(file,receiptMap);
	}
	public double totalPrice() {
		double total=0;
		Set<Integer> key = receiptMap.keySet();
		Iterator<Integer> it = key.iterator();
		while(it.hasNext()) {
			total = receiptMap.get(it.next()).getTotalAmount();
		}
		return total;
	}
	public int ticketAmount() {
		int count=0;;
		Set<Integer> key = receiptMap.keySet();
		Iterator<Integer> it = key.iterator();
		while(it.hasNext()) {
			it.next();
			count++;
		}
		return count;
	}
}
