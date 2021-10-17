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
	 * flag == 0; print all items in list
	 * flag == 1; print completed items
	 * flag == 2; print items not completed yet
	 */
	public ArrayList<TodoItem> getList(int flag) {
		ArrayList<TodoItem> list =  new ArrayList<TodoItem>();
		TodoItem item = null;
		String sql="SELECT * FROM list";
		
		if(flag==1) //for completed items 
			sql += " where is_completed = 1";
		else if(flag==2)
			sql += " where is_completed = 0";
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("memo");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				int comp = rs.getInt("is_completed");
				
				item = new TodoItem(id, category, title, desc, due_date, current_date, comp);
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
	
	public ArrayList<String> getCategories() {
		// TODO Auto-generated method stub
		String sql = "Select Distinct category from list;";
		ArrayList<String> array = new ArrayList<>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				array.add(rs.getString("category"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return array;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		// TODO Auto-generated method stub
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		TodoItem item = null;
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			
			if(ordering==0)
				sql += " desc";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				item = new TodoItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),  rs.getString(6), rs.getString(5));
				list.add(item);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int markCompleted(int id) {
		// TODO Auto-generated method stub
		String sql = "Update list set is_completed=1 where id = ?;";
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			count =  pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			
		case 3: //Query for finding items with category name
			
			sql = "Select * from list where category = \'" + category + "\';";
			break;
			
		case 4: //Query for finding items with '%' key
			
			sql = "Select * from list"
					+ " where title like ? or memo like ? or category like ? or due_date like ? ";
			break;
		}
		
		
		if(flag == 1) { //in case 1
			try {
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				//examine result
				if(rs == null) {
					return null;
				}
				
				while(rs.next()) {
					item = new TodoItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),  rs.getString(6), rs.getString(5));
					a.add(item);
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			return a;
		}
		else { //in case 3,4
			try {
				
				pstmt = conn.prepareStatement(sql);
				
				//add word for '?' part in the case 4!
				if(flag == 4) {
					for(int i=1; i<=4; i++) {
						System.out.println("!!TEST!!");
						pstmt.setString(i, "%" + word + "%");
					}
				}
				
				//execute Query
				rs = pstmt.executeQuery();
				
				//examine result
				if(rs == null) {
					return null;
				}
				
				System.out.println("conduct here1!");
				//create new array for item list.
				while(rs.next()) {
					item = new TodoItem(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),  rs.getString(6), rs.getString(5));
					a.add(item);
				}
				System.out.println("conduct here2!");
				return a;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("conduct here3!");
		return a;
	}
	
		
	/*public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
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
	}*/
	
	
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
