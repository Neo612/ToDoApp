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

import com.example.todolist.Frag_Edit_item.Frag_Edit_itemListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import android.widget.Toast;
import android.view.ViewGroup;


public class ToDoActivity extends ActionBarActivity implements Frag_Edit_itemListener{
//private ArrayList<String> todoItems;
//private ArrayAdapter<String> todoAdapter;
//private todoExtendedAdapter todoExtAdptr;
private ListView lvItems;
private EditText etNewItem;
private ArrayList<customItem> arrayofItems;
private todoExtendedAdapter custom_adapter;
private ToDoSQLiteHelper sqlite_db;
private static final int TWO = 2;
private static final int THREE = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        
     
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);
        
        sqlite_db = new ToDoSQLiteHelper (this);
        
        //db.deleteAll();
        
        //db.addItem(new customItem("QQQQ", "12/12/12"));

        // get all books
        arrayofItems = sqlite_db.getAllItems();
 
        // delete one book
        //db.deleteBook(list.get(0));
 
        // get all books
       // db.getAllBooks();
        
		//arrayofItems = customItem.getcustomItem();//Put read function here
        //readItems();
		custom_adapter = new todoExtendedAdapter(this, arrayofItems);
		lvItems.setAdapter(custom_adapter);
		setupListViewListener();
		//custom_adapter.notifyDataSetChanged();
        //populateArrayItems();
        
    }

	private void showEditDialog(String item, String date) {
		// TODO Auto-generated method stub
		  FragmentManager fm = getSupportFragmentManager();
		  Frag_Edit_item editNameDialog = Frag_Edit_item.newInstance(item, date);
	      editNameDialog.show(fm, "fragment_edit_item");
		
	}
	
	@Override
	public void onFinishEditDialog(String inputText, String date) {
		// TODO Auto-generated method stub
		
		
		customItem tmp_itm = new customItem(inputText, date);
		arrayofItems.add(tmp_itm);
		int i = sqlite_db.updateItem(tmp_itm);
		if(i==0) {
			sqlite_db.deleteItem(tmp_itm);
			sqlite_db.addItem(tmp_itm);
			Toast.makeText(this, "Item: " + inputText +", Due Date: "+date + "Added.", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Item: " + inputText +", Due Date: "+date + "Updated.", Toast.LENGTH_SHORT).show();
		}
		custom_adapter.notifyDataSetChanged();
		etNewItem.setText("");
		}
	
	/*private void showAlertDialog() {
	  	
		FragmentManager fm = getSupportFragmentManager();
	  	Frag_Edit_item alertDialog = Frag_Edit_item.newInstance("XXXXX", "02/01/01");
	  	alertDialog.show(fm, "fragment_edit_item");
	  }*/

	private void setupListViewListener() {
		// TODO Auto-generated method stub
    	lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapater, View item,
					int pos, long id) {
				// TODO Auto-generated method stub
				//showAlertDialog();
				customItem del_itm = arrayofItems.remove(pos);
				sqlite_db.deleteItem(del_itm);
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
				showEditDialog(arrayofItems.get(pos).todoitem_txt.toString(),
						arrayofItems.get(pos).due_date.toString());
				arrayofItems.remove(pos);
				//sqlite_db.deleteItem(arrayofItems.get(index));
				
				/*
				Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				i.putExtra("EditItem", arrayofItems.get(pos).todoitem_txt);
				i.putExtra("EditDate", arrayofItems.get(pos).due_date);
				startActivityForResult(i, TWO);
				customItem del_itm = arrayofItems.remove(pos);
				//sqlite_db.deleteItem(del_itm);
				custom_adapter.notifyDataSetChanged();
				writeItems();
				*/
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
    		customItem tmp_itm = new customItem(edited_txt, edited_date);
    		arrayofItems.add(tmp_itm);
    		sqlite_db.updateItem(tmp_itm);
    		custom_adapter.notifyDataSetChanged();
    		etNewItem.setText("");
    		//todoAdapter.notifyDataSetChanged();
    		writeItems();
    	}else if (resultCode == RESULT_OK && requestCode == THREE) {
    		//String itemText = etNewItem.getText().toString();
    		String itemText = data.getExtras().getString("addedText");
    		String itemDate = data.getExtras().getString("addedDate");
    		customItem tmp_itm = new customItem(itemText, itemDate);
    		arrayofItems.add(tmp_itm);
    		sqlite_db.addItem(tmp_itm);
    		//arrayofItems.add(new customItem(itemText, itemDate));
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
    	
    	
    }
    
    private void writeItems() {
       
    	/*	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile, arrayofItems);
    	} catch (IOException e){
    		e.printStackTrace();
    	}*/
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
