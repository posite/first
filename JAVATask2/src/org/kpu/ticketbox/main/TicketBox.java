package org.kpu.ticketbox.main;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.kpu.ticketbox.cinema.*;

public class TicketBox {
	private FamilyScreen familyScreen;
	private AnimationScreen animationScreen;
	private PremiumScreen premiumScreen;
	public static final String ADMIN_PASSWORD = "admin";
	Scanner scan = new Scanner(System.in);
	
	public void initScreen() {
		familyScreen = new FamilyScreen("굿 월 헌팅","천재적 두뇌를 가진 불우한 반항아",8000,10,10);
		animationScreen = new AnimationScreen("아담스 패밀리","세상에서 가장 무섭고 사랑스러운 가족 어드벤처",10000,10,10);
		premiumScreen = new PremiumScreen("매트릭스","인공 두뇌를 가진 컴퓨터가 지배하는 가상현실 공간 매트릭스",30000,5,5);			
	}
	public Screen selectScreen() {
		int choise;
		System.out.println("-------------------------------");
		System.out.println("-상영관 선택 메인메뉴");
		System.out.println("-------------------------------");
		System.out.println("가족 영화 1관");
		System.out.println("애니메이션 영화 2관");
		System.out.println("프리미엄 영화 3관 (식사 커피 제공)");
		System.out.println("관리자 메뉴");
		System.out.println("  선택(1~3, 9)외 종료");
		System.out.print("상영관 선택 :  ");
		choise = scan.nextInt();
		while(true) {
			try {
				switch(choise) {
				case 1:
					return familyScreen;
				case 2:
					return animationScreen;
				case 3:
					return premiumScreen;
				case 9:
					System.out.print("암호 입력");
					String password = scan.next();
					if(password.equals(ADMIN_PASSWORD)==true) {
						managerMode();
						System.out.println("C:\\Temp\\receipt.txt 백업 시작합니다.");
						familyScreen.Backup("C:\\Temp\\receipt.txt");
						System.out.println("가족 영화관 매출 백업 완료");
						animationScreen.Backup("C:\\Temp\\receipt.txt");
						System.out.println("애니메이션 영화관 매출 백업 완료");
						premiumScreen.Backup("C:\\Temp\\receipt.txt");
						System.out.println("프리미엄 영화관 매출 백업 완료");
						return null;
					}else {
						System.out.println("비밀번호가 틀렸습니다.");
						break;
					}
				}
			}catch(InputMismatchException e) {
				System.out.println("정수를 입력해주세요!!");
				scan=new Scanner(System.in);
				continue;
			}
			return null;
		}
	}
	void managerMode() {
		int ticket=0;
		System.out.println("-------------------------------");
		System.out.println("----        관리자 기능       ----");
		System.out.println("-------------------------------");
		ticket = familyScreen.ticketAmount()+animationScreen.ticketAmount()+premiumScreen.ticketAmount();
		System.out.println("가족 영화관 결제 총액 : "+familyScreen.totalPrice());
		System.out.println("애니메이션 영화관 결제 총액 : "+animationScreen.totalPrice());
		System.out.println("프리미엄 영화관 결제 총액 : "+premiumScreen.totalPrice());
		System.out.println("영화관 총 티켓 판매량 : "+ticket);
	}
}
