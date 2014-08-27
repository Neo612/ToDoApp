package com.example.todolist;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Currency;
import java.util.LinkedList;
import java.util.List;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

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
import android.widget.TextView;
import android.view.ViewGroup;


public class ToDoActivity extends ActionBarActivity {
//private ArrayList<String> todoItems;
//private ArrayAdapter<String> todoAdapter;
//private todoExtendedAdapter todoExtAdptr;
private ListView lvItems;
private EditText etNewItem;
private ArrayList<customItem> arrayofItems;
private todoExtendedAdapter custom_adapter;
private static final int TWO = 2;
private static final int THREE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_to_do);
        //lvItems = (ListView) findViewById(R.id.lvItems);
        //populateArrayItems();
        //lvItems.setAdapter(todoAdapter);
        
        
     
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        
		arrayofItems = customItem.getcustomItem();//Put read function here
        //readItems();
		custom_adapter = new todoExtendedAdapter(this, arrayofItems);
		lvItems.setAdapter(custom_adapter);
		setupListViewListener();
        
        //populateArrayItems();
       
        
        //todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
        //todoExtAdptr = new todoExtendedAdapter(this, (String[]) todoItems.toArray());
        //lvItems.setAdapter(todoAdapter);
                
        
       /* ArrayAdapter<String[]> adapter = new ArrayAdapter<String[]>(this, android.R.layout.simple_list_item_2, 
        		android.R.id.text1, itemList){
        	@Override
        	public View getView(int position, View convertView, ViewGroup parent){
        		View view = super.getView(position, convertView, parent);
        		String[] entry = itemList.get(position);
        		TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        		TextView text2 = (TextView) view.findViewById(android.R.id.text2);
        		text1.setText(entry[0]);
        		text2.setText(entry[1]);
 
        		return view;
        		
        	}
        
        };
        lvItems.setAdapter(adapter); 
        */
        
    }

	private void setupListViewListener() {
		// TODO Auto-generated method stub
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapater, View item,
					int pos, long id) {
				// TODO Auto-generated method stub
				arrayofItems.remove(pos);
				custom_adapter.notifyDataSetChanged();
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
				i.putExtra("EditItem", arrayofItems.get(pos).todoitem_txt);
				i.putExtra("EditDate", arrayofItems.get(pos).due_date);
				startActivityForResult(i, TWO);
				arrayofItems.remove(pos);	
				custom_adapter.notifyDataSetChanged();
				writeItems();
			}
    		
		});
    		
	}

	private void populateArrayItems(){
		/*ArrayList<customItem> arrayofItems = customItem.getcustomItem();
		todoExtendedAdapter custom_adapter = new todoExtendedAdapter(this, arrayofItems);
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(custom_adapter);
		
		
    	todoItems = new ArrayList<String>();
    	todoItems.add("Items 1");
    	todoItems.add("Items 2");
    	todoItems.add("Items 3");
    	*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (resultCode == RESULT_OK && requestCode == TWO) {
    		String edited_txt = data.getExtras().getString("editedText");
    		String edited_date = data.getExtras().getString("editedDate");
    		arrayofItems.add(new customItem(edited_txt, edited_date));
    		custom_adapter.notifyDataSetChanged();
    		etNewItem.setText("");
    		//todoAdapter.notifyDataSetChanged();
    		writeItems();
    	}else if (resultCode == RESULT_OK && requestCode == THREE) {
    		//String itemText = etNewItem.getText().toString();
    		String itemText = data.getExtras().getString("addedText");
    		String itemDate = data.getExtras().getString("addedDate");
    		arrayofItems.add(new customItem(itemText, itemDate));
    		custom_adapter.notifyDataSetChanged();
        	etNewItem.setText("");
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
    	
    	Intent i = new Intent(ToDoActivity.this, AddItemActivity.class);
		i.putExtra("AddItemTxt", itemText);
		startActivityForResult(i, THREE);
		
		
    }

   private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	arrayofItems = new ArrayList<customItem>();
    	try {
    		InputStream instream = openFileInput("todo.txt");
    		if(instream != null){
    			InputStreamReader inputreader = new InputStreamReader(instream);
    		    BufferedReader buffreader = new BufferedReader(inputreader);
    		    String prev_line = null;
    		    String curr_line;
    		    
    		    int count = 1;
    		    while (( curr_line = buffreader.readLine()) != null) {
    		    	if(count == 2){
    		    	arrayofItems.add(new customItem(prev_line,curr_line));
    		    	count = 1;
    		    	prev_line = null; 
    		    	}else {
    		    		prev_line = curr_line;
    		    		count = 2;
    		    	}
    		    
    		    }
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	/*try {
    		//arrayofItems = new ArrayList<customItem>(FileUtils.readLines(todoFile));
    		arrayofItems = new ArrayList<customItem>();
    		FileUtils.readLines(
    	} catch (IOException e){
    		arrayofItems = new ArrayList<customItem>();
    	}*/
    	
    }
    
    private void writeItems() {
       	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, arrayofItems);
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
