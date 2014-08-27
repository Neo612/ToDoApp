package com.example.todolist;

import java.util.ArrayList;

import com.example.todolist.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class todoExtendedAdapter extends ArrayAdapter<customItem>{

	public todoExtendedAdapter(Context context, ArrayList<customItem> customItems){
		super(context, 0, customItems);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		customItem item = getItem(position) ;
		
		if(convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_extended, parent, false);
		}
		TextView tvtodoTxt = (TextView) convertView.findViewById(R.id.tvTodoItemTxt);
		TextView tvDate = (TextView) convertView.findViewById(R.id.tvDate);
		
		//tvtodoTxt.setText(customItem.todoitem_txt);
		tvtodoTxt.setText(item.todoitem_txt);
		tvDate.setText(item.due_date);
		return convertView;
	}
}