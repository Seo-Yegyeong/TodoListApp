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
		
		String cate, due_date, title, desc;
		
		System.out.print("\n"
				+ "========== Create item Section ==========\n"
				+ "Category > ");
		cate = mine.nextLine().trim();
		
		System.out.print("Title > ");
		title = mine.nextLine().trim();
		
		if (list.isDuplicate(title)) {
			System.out.println("동일한 title을 중복해서 사용하실 수 없습니다. :)");
			return;
		}
		
		System.out.print("Description > ");
		desc = mine.nextLine().trim();
		
		System.out.print("Due date (e.g. 210926) > ");
		due_date = mine.nextLine().trim();
		
		TodoItem t = new TodoItem(cate, title, desc, due_date);
		list.addItem(t);
		
		System.out.print("Item Created!\n\n");
	}

	public static void deleteItem(TodoList l, Scanner mine) {
		
		int index;
		System.out.print("\n"
				+ "========== Delete Item Section ==========\n"
				+ "The number of item to remove > ");
		index = mine.nextInt() - 1;
		while(index<0 || l.size()<index) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요! > ");
			index = mine.nextInt() - 1;
		}
		
		TodoItem tmp = l.get(index);
		System.out.print(tmp.toString() + "\n정말 삭제하시겠습니까? 삭제를 취소하시려면 n을 눌러주세요! > ");
		char flag = mine.next().charAt(0);
		if(flag == 'n') {
			System.out.println("Canceled!");
			return;
		}
		
		l.remove(index);
		System.out.print("Item Deleted!\n\n");
	}


	public static void updateItem(TodoList l, Scanner mine) {
		
		System.out.print("\n"
				+ "========== Edit Item Section ==========\n"
				+ "The number of item to update > ");
		int index = mine.nextInt() - 1;
		while(index<0 || l.size()<index) {
			System.out.println("잘못 입력하셨습니다. 다시 입력해주세요! > ");
			index = mine.nextInt() - 1;
		}
		
		TodoItem tmp = l.getList().get(index);
		System.out.print(tmp.toString() + "\n수정할 항목입니다. 수정을 취소하시려면 n을 눌러주세요! > ");
		char flag = mine.next().charAt(0);
		if(flag == 'n') {
			System.out.println("Canceled!");
			return;
		}
		
		System.out.print("\nThe new Category of the item > ");
		String new_cate = mine.nextLine().trim();
		
		System.out.print("\nThe new Title of the item > ");
		String new_title = mine.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("동일한 Title을 중복해서 사용하실 수 없습니다. :)");
			return;
		}
		
		System.out.print("The new description > ");
		String new_description = mine.nextLine().trim();
		
		System.out.print("The new due date > ");
		String new_due_date = mine.nextLine().trim();
		
		l.getList().remove(index);
		l.add(new TodoItem(new_cate, new_title, new_description, new_due_date));
		System.out.print("Item Updated!\n\n");
		
	}

	public static void listAll(TodoList l) {
		if(l.getList().isEmpty()) {
			System.out.println("To do list가 현재 비어있습니다. 예쁘게 채워주세요! :)\n");
			return;
		}

		System.out.printf("\n--------------------------- Total Items ---------------------------\n");
		//		+ "No. %-20s   %-20s   %-20s   %-20s (%-20s)\n", "Category", "Title", "Description", "Due Date", "Saved time");
		int i=1;
		for (TodoItem item : l.getList()) {
			System.out.printf("%-2d %-5s %-10s %-20s %-10s (%-10s)\n",
					i, item.getCategory(), item.getTitle(), item.getDesc(), item.getDue_date(), item.getCurrent_date());
			i++;
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
			System.out.println("todolist.txt 파일이 없습니다.");
			//e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) throws IOException {
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			StringTokenizer st;
			do {				
				if(!line.isEmpty()) {
					st = new StringTokenizer(line, "#");
					TodoItem item = new TodoItem(st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken());
					l.addItem(item);
				}
				line = br.readLine();
			}
			while(line!=null);
			
			System.out.println(l.size() + "개의 항목을 읽었습니다!");
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("todolist.txt 파일이 없습니다.");
		}
		
	}
	
}
