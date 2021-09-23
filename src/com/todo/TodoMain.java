package com.todo;

import java.io.IOException;
import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		
		System.out.println("�ȳ��ϼ���! To do List ���� �����Դϴ�. ������ �ð� �Ǽ���!^^");
		try {
			TodoUtil.loadList(l, "todolist.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;

			case "exit":
				quit = true;
				TodoUtil.saveList(l, "todolist.txt");
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			default:
				System.out.println("�߸� �Է��ϼ̽��ϴ�. �޴��� Ȯ���Ͻð� �ش� �ܾ �Է����ּ���! >");
				break;
			}
			if(isList) l.listAll();
		} while (!quit);
		sc.close();
	}
}
