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
		System.out.println("영화 메뉴 -"+this.strMovieName);
		System.out.println("-------------------------------");
		System.out.println("1. 상영 영화 정보");
		System.out.println("2. 좌석 예약 현황");
		System.out.println("3. 좌석 예약 하기");
		System.out.println("4. 좌석 결제 하기");
		System.out.println("5. 메인 메뉴 이동");
	}
	public void showSeatMap() {
		System.out.println("\t[ 좌석 예약 현황]");
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
		System.out.println("[ 좌석 예약]");
		System.out.print("좌석 행 번호 입력 (1 - "+nRowMax+") : ");
		int row = scan.nextInt();
		System.out.print("좌석 열 번호 입력 (1 - "+nColMax+") : ");
		int col = scan.nextInt();
		if(seatArray[row-1][col-1].getcStatus()==MovieTicket.SEAT_RESERVED_MARK||seatArray[row-1][col-1].getcStatus()==MovieTicket.SEAT_PAY_COMPLETION_MARK) {
			System.out.println("이미 예약된 좌석입니다.");
			return;
		}
		seatArray[row-1][col-1].setReservedId(nCurrentReservedId);
		seatArray[row-1][col-1].setcStatus(MovieTicket.SEAT_RESERVED_MARK);
		System.out.println("행["+row+"] 열["+col+"] "+nCurrentReservedId+"예약 번호로 접수되었습니다.");
		nCurrentReservedId++;
	}
	public void payment() {
		Scanner scan = new Scanner(System.in);
		Receipt receipt;
		while(true) {
			System.out.println(" [ 좌석 예약 결제 ]");
			System.out.print("예약 번호 입력 ");
			int id = scan.nextInt();
			for(int i=0;i<nRowMax;i++) {
				for(int j=0;j<nColMax;j++) {
					if(id==seatArray[i][j].getnReservedId()) {
						if(seatArray[i][j].getcStatus()==MovieTicket.SEAT_RESERVED_MARK) {
							System.out.println("-------------------------------");
							System.out.println("결제 방식 선택");
							System.out.println("-------------------------------");
							System.out.println("1. 은행 이체");
							System.out.println("2. 카드 결제");
							System.out.println("3. 모바일 결제");
							System.out.print("결제 방식 입력(1~3) : ");
							try {
								int select = scan.nextInt();
								switch(select) {
								case Pay.BANK_TRANSFER_PAYMENT:
									System.out.println("[ 은행 이체 ]");
									System.out.print("이름 입력: ");
									String bName = scan.nextLine();
									System.out.print("은행 번호 입력(12자리수) : ");
									String bNumber = scan.nextLine();
									Pay bPay = new BankTransfer();
									receipt = bPay.charge(bName, strMovieName, bNumber,nTicketPrice);
									receiptMap.put(id, receipt);
									seatArray[i][j].setcStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
									System.out.println("은행 결제가 완료되었습니다. : "+receipt.getTotalAmount()+"원");
									return;
								case Pay.CREDIT_CARD_PAYMENT:
									System.out.println("[ 카드 결제 ]");
									System.out.print("이름 입력: ");
									String cName = scan.nextLine();
									System.out.print("카드 번호 입력(12자리수) : ");
									String cNumber = scan.nextLine();
									Pay cPay = new CardPay();
									receipt = cPay.charge(cName, strMovieName, cNumber,nTicketPrice);
									receiptMap.put(id, receipt);
									seatArray[i][j].setcStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
									System.out.println("카드 결제가 완료되었습니다. : "+receipt.getTotalAmount()+"원");
									return;
								case Pay.MOBILE_PHONE_PAYMENT:
									System.out.println("[ 모바일 결제 ]");
									System.out.print("이름 입력: ");
									String mName = scan.nextLine();
									System.out.print("핸드폰 번호 입력(11자리수) : ");
									String mNumber = scan.nextLine();
									Pay mPay = new MobilePay();
									receipt = mPay.charge(mName, strMovieName, mNumber,nTicketPrice);
									receiptMap.put(id, receipt);
									seatArray[i][j].setcStatus(MovieTicket.SEAT_PAY_COMPLETION_MARK);
									System.out.println("모바일 결제가 완료되었습니다. : "+receipt.getTotalAmount()+"원");
									return;
								}
							}catch(InputMismatchException e) {
								System.out.println("정수를 입력해주세요");
								scan = new Scanner(System.in);
								continue;
							}
						}else if(seatArray[i][j].getcStatus()==MovieTicket.SEAT_PAY_COMPLETION_MARK) {
							System.out.println("이미 예약된 좌석입니다.");
							return;
						}
					}
				}
			}
			System.out.println("예약된 번호가 아닙니다.");
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
