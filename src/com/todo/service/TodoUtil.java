package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list, Scanner mine) {
		
		String title, desc;
		
		System.out.print("\n"
				+ "========== Create item Section ==========\n"
				+ "Title > ");
		title = mine.nextLine().trim();
		
		if (list.isDuplicate(title)) {
			System.out.printf("동일한 title을 중복해서 사용하실 수 없습니다. :)");
			return;
		}
		
		System.out.print("Description > ");
		desc = mine.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		
		System.out.print("Item Created!\n\n");
	}

	public static void deleteItem(TodoList l, Scanner mine) {
		
		String title = "";
		
		System.out.print("\n"
				+ "========== Delete Item Section ==========\n"
				+ "The title of item to remove > ");
		
		title = mine.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
		System.out.print("Item Deleted!\n\n");
	}


	public static void updateItem(TodoList l, Scanner mine) {
		
		System.out.print("\n"
				+ "========== Edit Item Section ==========\n"
				+ "The title of the item you want to update > ");
		String title = mine.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("존재하지 않는 항목입니다.");
			return;
		}

		System.out.print("The new title of the item > ");
		String new_title = mine.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("동일한 title을 중복해서 사용하실 수 없습니다. :)");
			return;
		}
		
		System.out.print("The new description > ");
		String new_description = mine.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.print("Item Updated!\n\n");
			}
		}
	}

	public static void listAll(TodoList l) {
		if(l.getList().isEmpty())
			System.out.println("To do list가 현재 비어있습니다. 예쁘게 채워주세요! :)");
		String title = "Title";
		String desc = "Description";
		System.out.printf("\n--------- Total Items ---------\n%-20s      %-20s\n", title, desc);
		for (TodoItem item : l.getList()) {
			System.out.printf("%-20s | %-20s\n", item.getTitle(), item.getDesc());
		}
		System.out.print("\n");
	}
	
	//Implement File I/O
	public static void saveList(TodoList l, String filename) {
		File file = new File(filename);
		try(FileWriter f = new FileWriter(file)) {
			for(int i=0; i<l.size(); i++) {
				TodoItem tmp = l.get(i);
				//System.out.println("TEST!!" + tmp.toSaveString());
				f.write(tmp.toSaveString());
			}
			System.out.println("모든 데이터가 저장되었습니다!");
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) throws IOException {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line, "#");
			while(fr.read()!=-1) {
				TodoItem item = new TodoItem(st.nextToken(), st.nextToken(), st.nextToken());
				l.add(item);
			}
			System.out.println(l.size() + "개의 항목을 읽었습니다!");
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("todolist.txt 파일이 없습니다."); //exception customizing 다시 익히기!
		}
		
	}
	
}
