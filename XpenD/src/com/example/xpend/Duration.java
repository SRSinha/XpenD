package com.example.xpend;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class Duration extends Activity implements OnClickListener {

	private TextView text_date;
	private DatePicker date_picker;
	private Button bchangedate,bproceed;
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.duration);
	
		setCurrentDate();
		addButtonListener();

	}
	
	public void setCurrentDate() {
		text_date = (TextView) findViewById(R.id.tvdatepicker);
		date_picker = (DatePicker) findViewById(R.id.datepicker1);
		final Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		// set current date into textview
		text_date.setText(new StringBuilder()
		// Month is 0 based, so you have to add 1
		.append(day).append("-")
		.append(month + 1).append("-")
		.append(year).append(" "));
		// set current date into Date Picker
		date_picker.init(year, month, day, null);
		}
	
	public void addButtonListener() {
		bchangedate = (Button) findViewById(R.id.bchangedate);
		bproceed = (Button) findViewById(R.id.bproceed);
		bchangedate.setOnClickListener(this);
		bproceed.setOnClickListener(this);
	}
	
	 @Override
	 protected Dialog onCreateDialog(int id) {
	         switch (id) {
	         case DATE_DIALOG_ID:
	        	 // set date picker as current date
	        	 return new DatePickerDialog(this, datePickerListener, year, month,day);
	         	}
	         return null;
	     }
	
	  private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		  	// when dialog box is closed, below method will be called.
	        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
		 	year = selectedYear;
	 		month = selectedMonth;
	 		day = selectedDay;
	        // set selected date into Text View
	        text_date.setText(new StringBuilder().append(day)
	        .append("-").append(month + 1).append("-").append(year).append(" "));
	 		// set selected date into Date Picker
	 		date_picker.init(year, month, day, null);
	        }
	     };
	 

		
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.bproceed:
			String dategot=text_date.getText().toString();
			Bundle basket=new Bundle();
			basket.putString("datekey", dategot);
			Intent a=new Intent(Duration.this,Expenditure.class);
			a.putExtras(basket);
			startActivity(a);
			break;
		case R.id.bchangedate:
			showDialog(DATE_DIALOG_ID);
			break;
			}
	}
}	
