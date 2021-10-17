package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.Collections;

import com.todo.service.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	//private List<TodoItem> list;
	
	Connection conn;
	private static PreparedStatement pstmt;
	private static ResultSet rs;
	
	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public void importData(String filename) {
		
		try {
			
			FileReader f = new FileReader(filename);
			BufferedReader br = new BufferedReader(f);
			int record = 0;
			
			String sql = "Insert into list (category , title, memo, due_date, current_date)"
					+ " values (?, ?, ?, ?, ?);";
			String line;
			while((line = br.readLine()) != null) {
				
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, category);
				pstmt.setString(2, title);
				pstmt.setString(3, description);
				pstmt.setString(4, due_date);
				pstmt.setString(5, current_date);
				
				int count = pstmt.executeUpdate();
				if(count > 0)	record++;
			}
			
			if(record==0)
				System.out.println("There is nothing to read!");
			else if(record==1)
				System.out.println("1 recode are read!");
			else
				System.out.println(record + " records are read!");

			f.close();
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int addItem(TodoItem t) {
		String add_sql = "Insert into list (title, memo, category, current_date, due_date)"
				+ " values (?, ?, ?, ?, ?);";
		int flag=0;
		try {
			
			pstmt = conn.prepareStatement(add_sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			
			flag = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}

	public int deleteItem(int index) {
		String del_sql = "Delete from list where id=?;";
		int check = 0;
		
		try {
			
			pstmt = conn.prepareStatement(del_sql);
			pstmt.setInt(1, index);
			check = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return check;
	}

	public int updateItem(TodoItem t, int index) {
		String sql = "update list set category=?, title=?, memo=?, due_date=?, current_date=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getCategory());
			pstmt.setString(2, t.getTitle());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getDue_date());
			pstmt.setString(5, t.getCurrent_date());
			pstmt.setInt(6, index);
			
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	/*
	 * get all items in DB and return that ArrayList
	 */
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		TodoItem item = null;
		
		try {
			
			pstmt = conn.prepareStatement(new String("Select * from list"));
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				
				item = new TodoItem(id, category, title, desc, due_date, current_date);
				list.add(item);
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int getSize() {
		
		int count = 0;
		try {
			Statement stat = conn.createStatement();
			String sql = "Select count(id) from list;";
			rs = stat.executeQuery(sql);
			
			rs.next();
			count = rs.getInt("count(id)");
			stat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	/**
	 * This is a method to find elements.
	 * 
	 * @param flag is identifier to verify kind of function (with index, category one..)  
	 * @param index is for case 1
	 * @param category is for case 3
	 * @param searchString is for case 4
	 * @return
	 */
	public ArrayList<TodoItem> findItem(int flag, int index, String category, String word) {
		String searchString=null;
		String sql=null;
		TodoItem item = null;
		ArrayList<TodoItem> a = new ArrayList<>();
		pstmt = null;
		rs = null;
		
		//set query_sentence; 
		switch (flag) {
		case 1: //Query for finding items with index
			
			sql = "Select * from list where id = " + index + ";";
			break;
			
		case 2: //Query for finding all kind of category
			
			sql = "Select Distinct category from list;";
			break;
			
		case 3: //Query for finding items with category name
			
			sql = "Select * from list where category = \'" + category + "\';";
			searchString = category;
			break;
			
		case 4: //Query for finding items with '%' key
			
			sql = "Select * from list"
					+ " where title like \'%?%\' or memo like \'%?%\' or category like \'%?%\' or due_date like \'%?%\'";
			searchString = word;
			break;
			
		}
		
		
		//in case 1, it will return a TodoItem object and stop.
		//in this case, we don't have to print out.
		if(flag == 1) {
			try {
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				//examine result
				if(rs == null) {
					//System.out.println("일치하는 정보가 없습니다!");
					return null;
				}
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String cate = rs.getString("category");
					String title = rs.getString("title");
					String memo = rs.getString("memo");
					String current = rs.getString("current_date");
					String due = rs.getString("due_date");
					item = new TodoItem(id, cate, title, memo, due, current);
					a.add(item);
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return a;
		}
		
		//in case 2,3,4, it will print all of ResultSet.
		try {
			
			//add word for '?' part in the case 4!
			if(flag == 4) {
				pstmt = conn.prepareStatement(sql);
				for(int i=1; i<=4; i++) {
					System.out.println("Hey!");
					pstmt.setString(i, word);
				}
			}
			
			//execute Query
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			//examine result
			if(rs == null) {
				//System.out.println("일치하는 정보가 없습니다!");
				return null;
			}

			//create new array for item list.
			while(rs.next()) {
				int id = rs.getInt("id");
				String cate = rs.getString("category");
				String title = rs.getString("title");
				String memo = rs.getString("memo");
				String current = rs.getString("current_date");
				String due = rs.getString("due_date");
				item = new TodoItem(id, cate, title, memo, due, current);
				a.add(item);
				//item = new TodoItem(rs.getInt(1), rs.getString(1), rs.getString(2), rs.getString(3),  rs.getString(5), rs.getString(6));
				//a.add(item);
			}
			return a;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
	}

	/*public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}*/
		
	/*
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}
	
	public void sortByReverseDate() {
		Collections.sort(list, new TodoSortByDate());
		Collections.reverse(list);
	}
	
	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}
*/
	public Boolean isDuplicate(String title) {
		
		String sql = "Select title from list;";
		
		Statement stm;
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			String title_in_table;
			while (rs.next()) {
				title_in_table = rs.getString("title");
				if(title_in_table == title)
					return true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void closeConnection() {
		DbConnect.closeConnection();
	}
}
