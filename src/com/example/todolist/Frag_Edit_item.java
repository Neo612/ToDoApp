package com.example.todolist;

//import android.app.DialogFragment;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class Frag_Edit_item extends DialogFragment implements OnEditorActionListener{
	private EditText mEditText;
	private EditText mEditDate;
	
	public Frag_Edit_item(){
	}
	public interface Frag_Edit_itemListener {
        void onFinishEditDialog(String inputText, String date);
    }
	
	public static Frag_Edit_item newInstance(String item_txt, String date_txt) {
		Frag_Edit_item frag = new Frag_Edit_item();
		Bundle args = new Bundle();
		//args.putString("Edit ToDo", item_txt);
		args.putString("item", item_txt);
		args.putString("date", date_txt);
		frag.setArguments(args);
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_edit_item, container);
		mEditText = (EditText) view.findViewById(R.id.lbl_edit_itm);
		mEditDate = (EditText) view.findViewById(R.id.lbl_edit_dt);
		
		String item = getArguments().getString("item");
		String date = getArguments().getString("date");
		mEditText.setText(item);
		mEditDate.setText(date);
		getDialog().setTitle("Edit ToDo");
 
	  //getDialog().set
		// Show soft keyboard automatically
		mEditText.requestFocus();
		//mEditDate.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		mEditText.setOnEditorActionListener(this);
		mEditDate.setOnEditorActionListener(this);
		return view;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
			Frag_Edit_itemListener listener = (Frag_Edit_itemListener) getActivity();
            listener.onFinishEditDialog(mEditText.getText().toString(),mEditDate.getText().toString() );
            dismiss();
            return true;
        }
        return false;
    }

	
	/*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		 String title = getArguments().getString("Edit ToDoItem");
         String item = getArguments().getString("item");
         String date = getArguments().getString("date");
         AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
         alertDialogBuilder.setTitle(title);
         alertDialogBuilder.setMessage("Are you sure?");
         alertDialogBuilder.setPositiveButton("OK",  new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
               	   // on success
             }
         });
         alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
             }
         });
         
         return alertDialogBuilder.create();
    }*/

}
