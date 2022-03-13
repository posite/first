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
					System.out.print("안녕히 가세요");
					scan.close();
					System.exit(0);
				}
				bMainMenu=false;
			}
			screen.showScreenMenu();
			System.out.print("메뉴를 선택하세요 >>");
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
					System.out.println("메인 메뉴로 돌아갑니다.");
					bMainMenu=true;
					break;
				}
			}catch(InputMismatchException e) {
				System.out.println("정수를 입력해주세요!!");
				scan=new Scanner(System.in);
				continue;
			}
		}
	}
}
