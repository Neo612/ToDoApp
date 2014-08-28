package com.example.todolist;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditItemActivity extends ActionBarActivity {

	private String itm;
	private String date;
	//private Adapter edt_adapter;
	private EditText edt_itm; 
	private EditText edt_date; 
	private View edItemView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		itm = getIntent().getStringExtra("EditItem");
		date = getIntent().getStringExtra("EditDate");
		edt_itm = (EditText) findViewById(R.id.edtItemtext);
		edt_date = (EditText) findViewById(R.id.AddDate_edit);
		edt_itm.setText(itm);
		edt_date.setText(date);
		edt_itm.setSelection(edt_itm.getText().length());
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.priority_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new SpinnerActivity());
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
		EditText etdate = (EditText) findViewById(R.id.AddDate_edit);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		Intent data = new Intent();
		data.putExtra("editedText", etText.getText().toString());
		data.putExtra("editedDate", etdate.getText().toString());
		data.putExtra("editedPriority", String.valueOf(spinner.getSelectedItem()));
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
