package com.example.todolist;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
		Spinner spinner = (Spinner) findViewById(R.id.spinner_1);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.priority_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new SpinnerActivity());
		
	}
	public void onAddedItemActivity(View v) {
		EditText etText = (EditText) findViewById(R.id.AddTxt1);
		EditText etDate = (EditText) findViewById(R.id.AddDate);
		Spinner spinner = (Spinner) findViewById(R.id.spinner_1);
		Intent data = new Intent();
		data.putExtra("addedText", etText.getText().toString());
		data.putExtra("addedDate", etDate.getText().toString());
		data.putExtra("addedPriority", String.valueOf(spinner.getSelectedItem()));
		Toast.makeText(this, "addedPriority = " + String.valueOf(spinner.getSelectedItem()), 
				Toast.LENGTH_SHORT).show();
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
