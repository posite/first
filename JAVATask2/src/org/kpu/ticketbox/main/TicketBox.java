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
		familyScreen = new FamilyScreen("�� �� ����","õ���� �γ��� ���� �ҿ��� ���׾�",8000,10,10);
		animationScreen = new AnimationScreen("�ƴ㽺 �йи�","���󿡼� ���� ������ ��������� ���� ��庥ó",10000,10,10);
		premiumScreen = new PremiumScreen("��Ʈ����","�ΰ� �γ��� ���� ��ǻ�Ͱ� �����ϴ� �������� ���� ��Ʈ����",30000,5,5);			
	}
	public Screen selectScreen() {
		int choise;
		System.out.println("-------------------------------");
		System.out.println("-�󿵰� ���� ���θ޴�");
		System.out.println("-------------------------------");
		System.out.println("���� ��ȭ 1��");
		System.out.println("�ִϸ��̼� ��ȭ 2��");
		System.out.println("�����̾� ��ȭ 3�� (�Ļ� Ŀ�� ����)");
		System.out.println("������ �޴�");
		System.out.println("  ����(1~3, 9)�� ����");
		System.out.print("�󿵰� ���� :  ");
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
					System.out.print("��ȣ �Է�");
					String password = scan.next();
					if(password.equals(ADMIN_PASSWORD)==true) {
						managerMode();
						System.out.println("C:\\Temp\\receipt.txt ��� �����մϴ�.");
						familyScreen.Backup("C:\\Temp\\receipt.txt");
						System.out.println("���� ��ȭ�� ���� ��� �Ϸ�");
						animationScreen.Backup("C:\\Temp\\receipt.txt");
						System.out.println("�ִϸ��̼� ��ȭ�� ���� ��� �Ϸ�");
						premiumScreen.Backup("C:\\Temp\\receipt.txt");
						System.out.println("�����̾� ��ȭ�� ���� ��� �Ϸ�");
						return null;
					}else {
						System.out.println("��й�ȣ�� Ʋ�Ƚ��ϴ�.");
						break;
					}
				}
			}catch(InputMismatchException e) {
				System.out.println("������ �Է����ּ���!!");
				scan=new Scanner(System.in);
				continue;
			}
			return null;
		}
	}
	void managerMode() {
		int ticket=0;
		System.out.println("-------------------------------");
		System.out.println("----        ������ ���       ----");
		System.out.println("-------------------------------");
		ticket = familyScreen.ticketAmount()+animationScreen.ticketAmount()+premiumScreen.ticketAmount();
		System.out.println("���� ��ȭ�� ���� �Ѿ� : "+familyScreen.totalPrice());
		System.out.println("�ִϸ��̼� ��ȭ�� ���� �Ѿ� : "+animationScreen.totalPrice());
		System.out.println("�����̾� ��ȭ�� ���� �Ѿ� : "+premiumScreen.totalPrice());
		System.out.println("��ȭ�� �� Ƽ�� �Ǹŷ� : "+ticket);
	}
}
