package com.example.xpend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Expenditure extends Activity implements OnItemSelectedListener,
		OnClickListener {

	Spinner spinner;
	TextView tvduration, tvtextout,textout;
	EditText sqlexpend,etdescription;
	Button baddexpen, bviewdb,/* bremove, */bedit,bexpendback;
	LinearLayout container;
	String spin,returndate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenditure);
		init();

		Intent i = getIntent();
		Bundle extras = i.getExtras();
		returndate = extras.getString("datekey");
		tvduration.setText("The Expenditure date is " + returndate);
		tvduration.setTextColor(Color.WHITE);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.expendonwhom,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

	}

	private void init() {
		// TODO Auto-generated method stub
		spinner = (Spinner) findViewById(R.id.spinexpend);
		sqlexpend = (EditText) findViewById(R.id.etex1);
		etdescription = (EditText) findViewById(R.id.etdescription);
		baddexpen = (Button) findViewById(R.id.baddxpend);
		bviewdb = (Button) findViewById(R.id.bviewdb);
		bedit = (Button) findViewById(R.id.bedit);
		bexpendback = (Button) findViewById(R.id.bexpendback);
		
		// bremove = (Button) findViewById(R.id.bremove);
		container = (LinearLayout) findViewById(R.id.llcontainer);
		tvduration = (TextView) findViewById(R.id.tvdurationis);
		tvtextout = (TextView) findViewById(R.id.tvtextout);
		textout = (TextView) findViewById(R.id.textout);
		
		bviewdb.setOnClickListener(this);
		baddexpen.setOnClickListener(this);
		spinner.setOnItemSelectedListener(this);
		bedit.setOnClickListener(this);
		bexpendback.setOnClickListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int pos,
			long arg3) {
		// TODO Auto-generated method stub
		spin = parent.getItemAtPosition(pos).toString();
		Toast.makeText(parent.getContext(),
				parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.baddxpend:
			boolean diditwork = true;
			//Date now = new Date();
			// Date alsoNow = Calendar.getInstance().getTime();
			//String nowdate = new SimpleDateFormat("dd-MM-yyyy").format(now);

			/*LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View addView = layoutInflater.inflate(R.layout.row, null);
			TextView textOut = (TextView) addView.findViewById(R.id.tvtextout);*/
			String ex = sqlexpend.getText().toString();

			if (ex.trim().equals("")) {
				Toast.makeText(this, "Please enter a value ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			textout.setText("Rs. " + sqlexpend.getText().toString()
					+ " Spent on " + spin + " on " + returndate);
			textout.setTextColor(Color.WHITE);

			// Button bremove = (Button) addView.findViewById(R.id.bremove);
			/*
			 * bremove.setOnClickListener(new OnClickListener() {
			 * 
			 * @Override public void onClick(View v) { ((LinearLayout)
			 * addView.getParent()).removeView(addView); } });
			 */
			// bremove.setOnClickListener(this);

			//container.addView(addView);

			try {
				String expend = "     Rs. " + sqlexpend.getText().toString()+" ";
				String expendon = spin+" " ;
				String desc = "-" + etdescription.getText().toString();

				Sqlexpend entry = new Sqlexpend(Expenditure.this); // INSERTING
																	// IN DB
				// entry.entries++;
				entry.open();
				entry.createEntry(expend, expendon, returndate, desc);
				entry.close();
			} catch (Exception e) {
				diditwork = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("Dang it..Couldn't add in DB!!");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			} finally {
				if (diditwork == true) {
					Dialog d = new Dialog(this);
					d.setTitle("XpenD ADDED");
					TextView tv = new TextView(this);
					tv.setText("Success");
					d.setContentView(tv);
					d.show();
				}
			}
			break;

		case R.id.bviewdb:
			//Intent i = new Intent("com.example.xpend.SQLVIEW");
			//startActivity(i);
			new loadSomeStuff().execute();
			break;
			
		case R.id.bedit:
			Intent j = new Intent("com.example.xpend.EDIT");
			startActivity(j);
			break;
			
		case R.id.bexpendback:
			finish();
			break;
			
		
		}
	}
		class loadSomeStuff extends AsyncTask<String, Integer, String> { // (what// we// passing,// ,returning)
			ProgressDialog dialog;
			protected void onPreExecute() {
				dialog = new ProgressDialog(Expenditure.this); // context of// our main// class
				dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				dialog.setMax(100);
				dialog.show();
			}

			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub

				for(int i=0; i<10; i++)				
				{
					publishProgress(10);			
					try {
						Thread.sleep(55);		
					} catch (InterruptedException e) {
						e.printStackTrace();
					}			
				}
			dialog.dismiss();
				// when done so close dialog box
				try {
					Intent i = new Intent("com.example.xpend.SQLVIEW");
					startActivity(i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				return null;
			}

			protected void onProgressUpdate(Integer... progress) { // .... is // for array
					dialog.incrementProgressBy(progress[0]); //
			}

			protected void onPostExecute(String result) { // executed when
															// COLLECTED
															// RETURNED
				//dataresult.setText(result);
			}
			

		}

	
}
