package com.example.todolist;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends ActionBarActivity {
	private String add_itm_txt;
	private EditText edt_itm;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		add_itm_txt = getIntent().getStringExtra("AddItemTxt");
		edt_itm = (EditText) findViewById(R.id.AddTxt1);
		edt_itm.setText(add_itm_txt);
		edt_itm.setSelection(edt_itm.getText().length());
		
	}
	public void onAddedItemActivity(View v) {
		EditText etText = (EditText) findViewById(R.id.AddTxt1);
		EditText etDate = (EditText) findViewById(R.id.AddDate);
		Intent data = new Intent();
		data.putExtra("addedText", etText.getText().toString());
		data.putExtra("addedDate", etDate.getText().toString());
		setResult(RESULT_OK, data);
		finish();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_item, menu);
		return true;
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