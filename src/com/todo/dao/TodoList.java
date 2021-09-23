package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		if(list.isEmpty()) {
			System.out.println("정렬할 항목이 없습니다! Add로 리스트를 채워주세요! :)");
			return;
		}
		String title = "Title";
		String desc = "Description";
		System.out.printf("\n--------- Total Items ---------\n%-20s      %-20s\n", title, desc);
		int i=1;
		for (TodoItem myitem : list) {
			System.out.printf("%d. %-20s | %-20s\n", i, myitem.getTitle(), myitem.getDesc());
			i++;
		}
		System.out.print("\n");
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
}
