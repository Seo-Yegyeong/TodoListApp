package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public class TodoItem {
	private int id;
	private String category;
    private String title;
    private String desc;
    private String due_date;
    private String current_date;
    private int is_completed;
    


	/**
     * Constructor
     * @param title
     * @param desc
     */
    public TodoItem(String cate, String title, String desc, String due_date) {
		// TODO Auto-generated constructor stub
    	
    	this.category=cate;
    	this.title=title;
        this.desc=desc;
        this.due_date=due_date;
        SimpleDateFormat format1 = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        this.current_date=format1.format(new Date());
        this.is_completed = 0;
        
	}
    
    /**
     * Using when file uploaded.
     * @param nextToken
     * @param nextToken2
     * @param nextToken3
     * @param nextToken4
     * @param nextToken5
     */
	public TodoItem(int id, String nextToken, String nextToken2, String nextToken3, String nextToken4, String nextToken5) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.category=nextToken;
    	this.title=nextToken2;
        this.desc=nextToken3;
        this.due_date=nextToken4;
        this.current_date=nextToken5;
        this.is_completed = 0;
	}
	
	public TodoItem(int id, String nextToken, String nextToken2, String nextToken3, String nextToken4, String nextToken5, int comp) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.category=nextToken;
    	this.title=nextToken2;
        this.desc=nextToken3;
        this.due_date=nextToken4;
        this.current_date=nextToken5;
        this.is_completed = comp;
	}

	/**
     * Getter, Setter
     * @return
     */
	public int getIsCompleted() {
		return is_completed;
	}

	public void setIsCompleted(int comp) {
		this.is_completed= comp;
	}
	
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		String sf=null, temp_title=null;
    	if(is_completed==1) //if it is conducted, then do next instruction.
    		temp_title = this.getTitle() + "[√]";
    	else
    		temp_title = this.getTitle();
    	
    	sf = String.format("%-2d [%-5s] %-15s %-20s %-10s (%s)\n",
				this.id, this.getCategory(), temp_title, this.getDesc(), this.getDue_date(), this.getCurrent_date());    	
    	return sf;
	}
    
	//for saving file
    public String toSaveString() {
    	//우선은 이렇게 하고 json 공부하면 추가해보자!
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
}
