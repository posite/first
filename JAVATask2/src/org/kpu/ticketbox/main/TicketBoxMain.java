package org.kpu.ticketbox.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.kpu.ticketbox.cinema.Screen;

public class TicketBoxMain {
	public static void main(String []args) {
		TicketBox ticketBox = new TicketBox();
		Scanner scan = new Scanner(System.in);
		Screen screen = null;
		boolean bMainMenu = true;
		ticketBox.initScreen();
		
		while(true) {
			if(bMainMenu) {
				screen = ticketBox.selectScreen();
				if(screen==null) {
					System.out.print("�ȳ��� ������");
					scan.close();
					System.exit(0);
				}
				bMainMenu=false;
			}
			screen.showScreenMenu();
			System.out.print("�޴��� �����ϼ��� >>");
			try {
				int select = scan.nextInt();
				switch(select) {
				case 1:
					screen.showMovieInfo();
					break;
				case 2:
					screen.showSeatMap();
					break;
				case 3:
					screen.reserveTicket();
					break;
				case 4:
					screen.payment();
					break;
				case 5:
					System.out.println("���� �޴��� ���ư��ϴ�.");
					bMainMenu=true;
					break;
				}
			}catch(InputMismatchException e) {
				System.out.println("������ �Է����ּ���!!");
				scan=new Scanner(System.in);
				continue;
			}
		}
	}
}
