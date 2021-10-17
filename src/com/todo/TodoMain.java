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
		
		boolean isList = false;
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
			isList = false;
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
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				//l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				//l.sortByName();
				//l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				//l.sortByDate();
				isList = true;
				break;
				
			case "ls_date_desc":
				//l.sortByReverseDate();			
				isList = true;
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
				TodoUtil.findItems(l, 2, sc);
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
			if(isList) TodoUtil.listAll(l);
		} while (!quit);
		
		l.closeConnection();
		sc.close();
	}
}
