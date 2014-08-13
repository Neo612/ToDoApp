package com.example.todolist;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class ToDoActivity extends ActionBarActivity {
private ArrayList<String> todoItems;
private ArrayAdapter<String> todoAdapter;
private ListView lvItems;
private EditText etNewItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        //populateArrayItems();
        readItems();
        todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
    }

    private void setupListViewListener() {
		// TODO Auto-generated method stub
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapater, View item,
					int pos, long id) {
				// TODO Auto-generated method stub
				todoItems.remove(pos);
				todoAdapter.notifyDataSetChanged();
				writeItems();
				return true;
			}
    		
		});
    	lvItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapater, View item, int pos,
					long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				i.putExtra("EditItem", todoItems.get(pos));
				startActivityForResult(i, 0);
				todoItems.remove(pos);	
				todoAdapter.notifyDataSetChanged();
				writeItems();
			}
    		
		});
    	
    	/*
    	 * lvItems.setOnClickListener(new OnClickListener() {
    	 
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				i.putExtra("Item", todoItems.get(pos));
				
			}
		});
		*/
		
	}

	/*private void populateArrayItems(){
    	todoItems = new ArrayList<String>();
    	todoItems.add("Items 1");
    	todoItems.add("Items 2");
    	todoItems.add("Items 3");
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == 0) {
    		String edited_txt = data.getExtras().getString("editedText");
    		todoItems.add(edited_txt);
    		etNewItem.setText("");
    		todoAdapter.notifyDataSetChanged();
    		writeItems();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }
    public void onAddedItem(View v){
    	String itemText = etNewItem.getText().toString();
    	todoAdapter.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    }

    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e){
    		todoItems = new ArrayList<String>();
    	}
    }
    
    private void writeItems() {
       	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, todoItems);
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }
    //@Override
    //public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();
        //if (id == R.id.action_settings) {
          //  return true;
        //}
        //return super.onOptionsItemSelected(item);
    //}
}
