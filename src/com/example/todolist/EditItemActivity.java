package com.example.todolist;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;

public class EditItemActivity extends ActionBarActivity {

	private String itm;
	//private Adapter edt_adapter;
	private EditText edt_itm; 
	private View edItemView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		itm = getIntent().getStringExtra("EditItem");
		edt_itm = (EditText) findViewById(R.id.edtItemtext);
		edt_itm.setText(itm);
		edt_itm.setSelection(edt_itm.getText().length());
		//finish();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	public void onSaveItem(View v) {
		EditText etText = (EditText) findViewById(R.id.edtItemtext);
		Intent data = new Intent();
		data.putExtra("editedText", etText.getText().toString());
		setResult(RESULT_OK, data);
		finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
