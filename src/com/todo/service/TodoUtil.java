package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list, Scanner mine) {
		
		String title, desc;
		
		System.out.print("\n"
				+ "========== Create item Section ==========\n"
				+ "enter the title > ");
		title = mine.nextLine().trim();
		
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.print("enter the description > ");
		desc = mine.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		
	}

	public static void deleteItem(TodoList l, Scanner mine) {
		
		String title = "";
		
		System.out.print("\n"
				+ "========== Delete Item Section ==========\n"
				+ "enter the title of item to remove > ");
		
		title = mine.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l, Scanner mine) {
		
		System.out.print("\n"
				+ "========== Edit Item Section ==========\n"
				+ "enter the title of the item you want to update > ");
		String title = mine.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("title doesn't exist");
			return;
		}

		System.out.print("enter the new title of the item > ");
		String new_title = mine.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.print("enter the new description > ");
		String new_description = mine.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("item updated");
			}
		}
	}

	public static void listAll(TodoList l) {
		if(l.getList().isEmpty())
			System.out.println("There is nothing has stored");
		for (TodoItem item : l.getList()) {
			System.out.println("Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc());
		}
	}
}
