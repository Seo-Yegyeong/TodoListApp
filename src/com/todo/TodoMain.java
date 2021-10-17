package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todolist.txt");
		
		boolean quit = false;
		
		System.out.println("안녕하세요! To do List 관리 어플입니다. 유익한 시간 되세요!^^");
		/*try {
			TodoUtil.loadList(l, "todolist.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.nextLine().trim();
			
			switch (choice) {

			case "add":
				TodoUtil.createItem(l, sc);
				break;
			
			case "del":
				TodoUtil.deleteItem(l, sc);
				break;
				
			case "edit":
				TodoUtil.updateItem(l, sc);
				break;
				
			case "ls":
				TodoUtil.listAll(l, null, 0);
				break;

			case "ls_name_asc":
				System.out.println("제목순 정렬입니다.");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순 정렬입니다.");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("날짜순 정렬입니다.");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜역순 정렬입니다.");
				TodoUtil.listAll(l, "due_date", 0);			
				break;
			
			case "find_item_id":
				TodoUtil.findItems(l, 1, sc);
				break;
				
			case "find_item_cate":
				TodoUtil.findItems(l, 3, sc);
				break;
			
			case "find_item":
				TodoUtil.findItems(l, 4, sc);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
			
			case "comp":
				TodoUtil.completeItem(l, sc);
				break;
				
			case "ls_comp":
				TodoUtil.printCompleteItem(l);
				break;
				
			case "ls_not_comp":
				TodoUtil.printNotCompleteItem(l);
				break;
				
			case "help":
				Menu.displaymenu();
				break;
			
			case "exit":
				quit = true;
				//TodoUtil.saveList(l, "todolist.txt");
				break;
							
			default:
				System.out.println("잘못 입력하셨습니다. 메뉴를 확인하시고 해당 단어를 입력해주세요!");
				break;
			}
		} while (!quit);
		
		l.closeConnection();
		sc.close();
	}
}
