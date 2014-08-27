package com.example.todolist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToDoSQLiteHelper extends SQLiteOpenHelper {
	
	// Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ToDoItemsDB";
 
    public ToDoSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Creates todoDbList table
		String CREATE_TO_DO_TABLE = "CREATE TABLE todoDbList ( " + 
					"item TEXT, date TEXT )";
		db.execSQL(CREATE_TO_DO_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS todoDbList");
		this.onCreate(db);
		
	}
	
	/**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */
 
    // Books table name
    private static final String TABLE_TODO_DB_LIST = "todoDbList";
 
    // Books Table Columns names
    private static final String KEY_ITEM = "item";
    private static final String KEY_DATE = "date";
 
    private static final String[] COLUMNS = {KEY_ITEM,KEY_DATE};
 
	public void addItem(customItem item){
        //for logging
		Log.d("addItem", item.toString()); 

		// 1. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
		ContentValues values = new ContentValues();
		values.put(KEY_ITEM, item.todoitem_txt.toString()); // get title 
		values.put(KEY_DATE, item.due_date.toString()); // get author

		// 3. insert
		db.insert(TABLE_TODO_DB_LIST, // table
        null, //nullColumnHack
        values); // key/value -> keys = column names/ values = column values

		// 4. close
		db.close(); 
	}
	
	public customItem getItem(customItem input_item){
		 
	    // 1. get reference to readable DB
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    // 2. build query
	    Cursor cursor = 
	            db.query(TABLE_TODO_DB_LIST, // a. table
	            COLUMNS, // b. column names
	            " item = ?", // c. selections 
	            new String[] { input_item.todoitem_txt.toString() }, // d. selections args
	            null, // e. group by
	            null, // f. having
	            null, // g. order by
	            null); // h. limit
	 
	    // 3. if we got results get the first one
	    if (cursor != null)
	        cursor.moveToFirst();
	 
	    // 4. build book object
	    customItem item = new customItem(cursor.getString(0), cursor.getString(1));
	 
	    //log 
	    Log.d("getItem()", input_item.toString());
	 
	    // 5. return book
	    return item;
	}
    // Get All Items
    public ArrayList<customItem> getAllItems() {
    	ArrayList<customItem> items = new ArrayList<customItem>();
 
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_TODO_DB_LIST; 
 
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
 
        // 3. go over each row, build book and add it to list
        customItem item = null;
        if (cursor.moveToFirst()) {
            do {
            	item = new customItem(cursor.getString(0), cursor.getString(1));
                // Add book to books
            	items.add(item);
            } while (cursor.moveToNext());
        }
 
        Log.d("getAllItems()", items.toString());
 
        // return books
        return items;
    }

    public int updateItem(customItem item) {
    	 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("item", item.todoitem_txt.toString()); // get title 
        values.put("date", item.due_date.toString()); // get author
 
        // 3. updating row
        int i = db.update(TABLE_TODO_DB_LIST, //table
                values, // column/value
                KEY_ITEM+" = ?", // selections
                new String[] {item.todoitem_txt.toString()}); //selection args
 
        // 4. close
        db.close();
 
        return i;
 
    }
 
    // Deleting single item
    public void deleteItem(customItem item) {
 
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        // 2. delete
        db.delete(TABLE_TODO_DB_LIST,
        		KEY_ITEM+" = ?",
                new String[] { item.todoitem_txt.toString() });
 
        // 3. close
        db.close();
 
        Log.d("deleteItem", item.toString());
 
    }
    
    public void deleteAll() {
    	
    	String query = "DELETE  * FROM " + TABLE_TODO_DB_LIST;
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
 
        //db.execSQL(query);
        db.delete(TABLE_TODO_DB_LIST, null, null);
        //Cursor cursor = db.rawQuery(query, null);
        // 2. delete
 
        db.close();
 
        //Log.d("deletedAll", item.toString());
 
    }
    
  

}
