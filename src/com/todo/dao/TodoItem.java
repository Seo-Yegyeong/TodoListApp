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
	}
    
    /**
     * Using when file uploaded.
     * @param nextToken
     * @param nextToken2
     * @param nextToken3
     * @param nextToken4
     * @param nextToken5
     */
	public TodoItem(String nextToken, String nextToken2, String nextToken3, String nextToken4, String nextToken5) {
		// TODO Auto-generated constructor stub
		//this.id=token;
		this.category=nextToken;
    	this.title=nextToken2;
        this.desc=nextToken3;
        this.due_date=nextToken4;
        this.current_date=nextToken5;
	}

	/**
     * Getter, Setter
     * @return
     */
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
    	return "[" + category + "] " + title + " - " + desc + " - due: " + due_date
				+ " (" + current_date + ")";
	}

    public void printItemWithFormat() {
		System.out.printf("%-2d %-5s %-10s %-20s %-10s (%-10s)\n",
				this.id, this.getCategory(), this.getTitle(), this.getDesc(), this.getDue_date(), this.getCurrent_date());
	}
    
	//for saving file
    public String toSaveString() {
    	//우선은 이렇게 하고 json 공부하면 추가해보자!
    	return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "\n";
    }
}
