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
			System.out.println("������ title�� �ߺ��ؼ� ����Ͻ� �� �����ϴ�. :)");
			return;
		}
		
		System.out.print("Description > ");
		desc = mine.nextLine().trim();
		
		System.out.print("Due date (e.g. 210926) > ");
		due_date = mine.nextLine().trim();
		
		TodoItem t = new TodoItem(cate, title, desc, due_date);
		if(list.addItem(t)>0)
			System.out.print("========== Item Created! ==========\n\n");
	}

	public static void deleteItem(TodoList l, Scanner mine) {
		
		int index=0, check=0;
		TodoItem item;
		
		System.out.print("\n"
				+ "========== Delete Item Section ==========\n"
				+ "The number of item to remove > ");
		index = mine.nextInt();
		
		ArrayList<TodoItem> array = l.findItem(1, index, null, null);
		item = array.get(0);
		if(item == null)	return;
		
		System.out.print(item.toString() + 
						"\n���� �����Ͻðڽ��ϱ�? �����Ͻ÷��� y�� �����ּ���! > ");
		char flag = mine.next().charAt(0);
		if(flag != 'y') {
			System.out.println("========== Canceled! ==========\n\n");
			mine.nextLine();
			return;
		}
		
		check = l.deleteItem(index);
		if(check>0)
			System.out.print("========== Item Deleted! ==========\n\n");
		else
			System.out.print("========= Fail to Delete! =========\n\n");
		mine.nextLine();
	}


	public static void updateItem(TodoList l, Scanner mine) {
		
		TodoItem item=null;
		String new_cate, new_title, new_description, new_due_date;
		int index;
		
		System.out.print("\n"
				+ "========== Edit Item Section ==========\n"
				+ "The number of item to update > ");
		index = mine.nextInt();
		
		ArrayList<TodoItem> array = l.findItem(1, index, null, null);
		item = array.get(0);
		
		if(item == null)	return;
		
		
		System.out.print(item.toString() + "\n������ �׸��Դϴ�. ������ ����Ͻ÷��� n�� �����ּ���! > ");
		char flag = mine.next().charAt(0);
		if(flag == 'n') {
			System.out.println("========== Canceled! ==========");
			mine.nextLine();
			return;
		}
		mine.nextLine();
		
		
		System.out.print("\nThe new category > ");
		new_cate = mine.nextLine().trim();
		
		do {
			System.out.print("The new title > ");
			new_title = mine.nextLine().trim();
			if(l.isDuplicate(new_title))
				System.out.println("������ Title�� �ߺ��ؼ� ����Ͻ� �� �����ϴ�. :)\n�ٽ� �Է����ּ���!");
		}
		while (l.isDuplicate(new_title));
		
		System.out.print("The new description > ");
		new_description = mine.nextLine().trim();
		
		System.out.print("The new due date > ");
		new_due_date = mine.nextLine().trim();
		
		l.updateItem(new TodoItem(new_cate, new_title, new_description, new_due_date), index);
		System.out.print("========== Item Updated! ==========\n\n");
		
	}
	
public static void findItems(TodoList l, int findCase, Scanner mine) {
		
	int index=0;
	String searchString=null;
	TodoItem item = null;
	ArrayList<TodoItem> array = null;
	
	if(findCase==1) {
		System.out.print("Enter number what you want to search! > ");
		index = mine.nextInt();
		mine.nextLine();
	}
	else if (findCase == 3 || findCase == 4) {
		System.out.print("Enter the word what you want to search! > ");
		searchString = mine.nextLine().trim();
	}
	
	switch (findCase) {
		case 1:
			array = l.findItem(findCase, index, null, null);
			break;
		case 2:
			array = l.findItem(findCase, 0, null, null);
			break;
		case 3:
			array = l.findItem(findCase, 0, searchString, null);
			break;
		case 4:
			array = l.findItem(findCase, 0, null, searchString);
	}
	
	if(array.isEmpty()) {
		System.out.println("��ġ�ϴ� ������ �����ϴ�!");
		return;
	}
	
	//print result
	if (findCase == 1)
		System.out.printf("\n--------------------------- �˻��Ͻ� �׸��Դϴ�! :) ---------------------------\n");
	else if (findCase == 2)
		System.out.printf("\n--------------------------- ī�װ� �����Դϴ�! :) ---------------------------\n");
	else
		System.out.printf("\n----------------- �˻��Ͻ� ���� \'%s\'��(��) ���Ե� �׸��Դϴ�! :) -----------------\n", searchString);
	Iterator<TodoItem> it = array.iterator();
	while(it.hasNext()) {
		item = it.next();
		item.printItemWithFormat();
	}
	System.out.println("------------------------------------------------------------------------\n");
	
	
		//previous codes (this code is for week6, but it does not fit with professor's demand.)
		/*String searchText;
		TodoItem temp;
		List numList = new ArrayList<Integer>();
		
		for(int i=1; i<=l.getSize(); i++) {
			temp = l.find_item(i);
			searchText = temp.getTitle() + temp.getTitle() + temp.getDesc() + temp.getCurrent_date() + temp.getDue_date();
			if(searchText.contains(words))
				numList.add(i);
		}*/
		
		//previous code (maybe week5)
		/*TodoList temp_l = new TodoList(); //�˻�� ���Ե� �׸���� ��Ƽ� ���� list�� �����ϱ� ����.
		for(TodoItem a : l.getList()) {
			if( a.getCategory().contains(words) == true
					|| a.getTitle().contains(words) == true
					|| a.getDesc().contains(words) == true
					|| a.getDue_date().contains(words) == true ) {
				//System.out.println("TEST!!!" + a.toString());
				temp_l.addItem(a);
			}
		}
		if(temp_l.getList().isEmpty()) 
			System.out.println("��ġ�ϴ� ������ �����ϴ�!");
		else {
			System.out.printf("\n----------------- �˻��Ͻ� ���� \'%s\'��(��) ���Ե� �׸��Դϴ�! :) -----------------\n", words);
			int i=0;
			for (TodoItem a : temp_l.getList()) {
				i++;
				System.out.printf("%-2d %-5s %-10s %-20s %-10s (%-10s)\n",
						i, a.getCategory(), a.getTitle(), a.getDesc(), a.getDue_date(), a.getCurrent_date());
			}
			System.out.println("-----------------------------------------------------------------------\n");
		}*/
	}

	public static void listAll(TodoList l) {
		if(l.getSize() == 0) {
			System.out.println("To do list�� ���� ����ֽ��ϴ�. �� �ϵ��� �����ּ���! :)\n");
			return;
		}
		String title = "Title";
		String desc = "Description";
		System.out.printf("\n----------------- Total Items -----------------\n     %-20s      %-20s\n", title, desc);

		for (TodoItem myitem : l.getList()) {
			System.out.printf("%d. %-20s | %-20s\n", myitem.getId(), myitem.getTitle(), myitem.getDesc());
		}
		System.out.print("------------------------------------------------\n");
		
	}
	
	//Implement File I/O
	/*public static void saveList(TodoList l, String filename) {
		File file = new File(filename);
		try(FileWriter f = new FileWriter(file)) {
			for(int i=0; i<l.getList().size(); i++) {
				TodoItem tmp = l.getList().get(i);
				//System.out.println("TEST!!" + tmp.toSaveString());
				f.write(tmp.toSaveString());
			}
			System.out.println("\n**********************"
							+ "\n*��� �����Ͱ� ����Ǿ����ϴ�!*\n"
							+ "**********************");
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("**************************"
					+ "\n*todolist.txt ������ �����ϴ�.*\n"
					+ "**************************");
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
			
			System.out.println("*******************\n*"+
						l.getList().size() + "���� �׸��� �о����ϴ�!*\n"
								+ "*******************");
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("**************************"
							+ "\n*todolist.txt ������ �����ϴ�.*\n"
							+ "**************************");
		}
		
	}*/
	
}
