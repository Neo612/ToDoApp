package com.example.todolist;

import java.util.ArrayList;
import java.util.Date;

public class customItem {
	public String todoitem_txt;
	public String due_date;
	
	public customItem(String itemTxt, String date) {
		this.todoitem_txt = itemTxt;
		this.due_date = date;
	}
	
	public static ArrayList<customItem> getcustomItem(){
		ArrayList<customItem> customItems = new ArrayList<customItem>();
		customItems.add(new customItem("aaaaaa","23/01/2014" ) );
		customItems.add(new customItem("bbbbbb","24/12/2014" ) );
		
		return customItems;
	}
	
	@Override
    public String toString() {
        return "TodoItem [item=" + todoitem_txt + ", Date=" + due_date + "]";
    }

}
