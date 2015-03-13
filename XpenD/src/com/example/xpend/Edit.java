package com.example.xpend;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Edit extends Activity implements OnClickListener{
	Button bdelete,bupdate,bviewupdated,bgetinfo,bdeleteall,beditback,beditexit,bcalendar;
	EditText etexpendid,etexpendon,etexpenddate,etexpend,etexpenddesc;
	private Calendar cal;
	private int day;
	private int month;
	private int year;
	private EditText et;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		init();
	
	}
	private void init() {
		// TODO Auto-generated method stub
		etexpendid = (EditText) findViewById(R.id.etexpendid);
		etexpenddate = (EditText) findViewById(R.id.etexpenddate);
		etexpendon = (EditText) findViewById(R.id.etexpendon);
		etexpend = (EditText) findViewById(R.id.etexpend);
		etexpenddesc = (EditText) findViewById(R.id.etexpenddesc);
		bdelete = (Button) findViewById(R.id.bdelete);
		bupdate = (Button) findViewById(R.id.bupdate);
		bviewupdated = (Button) findViewById(R.id.bviewupdated);
		bgetinfo = (Button) findViewById(R.id.bget);
		bdeleteall = (Button) findViewById(R.id.bdeleteall);
		beditback = (Button) findViewById(R.id.beditback);
		beditexit = (Button) findViewById(R.id.beditexit);
		bcalendar = (Button) findViewById(R.id.bcalendar);
		bdelete.setOnClickListener(this);
		bupdate.setOnClickListener(this);
		bviewupdated.setOnClickListener(this);
		bgetinfo.setOnClickListener(this);
		bdeleteall.setOnClickListener(this);
		beditback.setOnClickListener(this);
		beditexit.setOnClickListener(this);
		bcalendar.setOnClickListener(this);
		
		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		
		etexpend.setFocusable(false);
		etexpendon.setFocusable(false);
		etexpenddate.setFocusable(false);
		etexpenddesc.setFocusable(false);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.bget:
			boolean diditwork=true;
			String etex=etexpendid.getText().toString();
			if (etex.trim().equals("")) {
				Toast.makeText(this, "Please enter an ID from the Saved Data ",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			etexpend.setFocusableInTouchMode(true);
			etexpendon.setFocusableInTouchMode(true);
			etexpenddate.setFocusableInTouchMode(true);
			etexpenddesc.setFocusableInTouchMode(true);
			
			try{
				String s=etexpendid.getText().toString().trim();
				long l= Long.parseLong(s);
				
				Sqlexpend hon=new Sqlexpend(this);
				hon.open();
				String returnedexpend1= hon.getexpend(l);		//long for which ROW
				String returnedexpend=returnedexpend1.replaceAll("[^0-9]", "");
				String returnedexpendon= hon.getexpendon(l);
				String returnedexpenddate=hon.getexpenddate(l);
				String returnedexpenddesc1=hon.getexpenddesc(l);
				String returnedexpenddesc=returnedexpenddesc1.replace("-", "");
				//String returnedexpenddesc=returnedexpenddesc1.replaceAll("[^0-9]", "");
				hon.close();
				
				etexpend.setText(returnedexpend);
				etexpendon.setText(returnedexpendon);
				etexpenddate.setText(returnedexpenddate);
				etexpenddesc.setText(returnedexpenddesc);
			}
				
				catch(Exception e){
					diditwork=false;
					String error=e.toString(); 		
					Dialog d=new Dialog(this);
					d.setTitle("Dang it!!");
					TextView tv=new TextView(this);
					tv.setText(error); 
					d.setContentView(tv);
					d.show();
				}
			finally{
				if(diditwork==true){		
					Dialog d=new Dialog(this);
					d.setTitle("Data Retrieved!!");
					TextView tv=new TextView(this);
					tv.setText("Success"); 
					d.setContentView(tv);
					d.show();
				}
			}
			break;
		case R.id.bdelete:
			etexpendid.setText("");
			etexpend.setText("");
			etexpendon.setText("");
			etexpenddate.setText("");
			etexpenddesc.setText("");
			try{
				String srow1=etexpendid.getText().toString();
				long lrow1= Long.parseLong(srow1);
				Sqlexpend ex1=new Sqlexpend(this);
				ex1.open();
				ex1.deleteentry(lrow1);
				ex1.close();}
				catch(Exception e){
					diditwork=false;
					String error=e.toString(); 	
					Dialog d=new Dialog(this);
					d.setTitle("Dang it!!");
					TextView tv=new TextView(this);
					tv.setText(error); 
					d.setContentView(tv);
					d.show();
				}
			break;
		case R.id.bupdate:
			try{
				String srow=etexpendid.getText().toString();
				String expend="     Rs."+etexpend.getText().toString();
				String expendon=etexpendon.getText().toString();
				String expenddate=etexpenddate.getText().toString();
				String expenddesc="-"+etexpenddesc.getText().toString();
				long lrow= Long.parseLong(srow);
				
				Sqlexpend ex=new Sqlexpend(this);
				ex.open();
				ex.updateentry(lrow,expend,expendon,expenddate,expenddesc);
				ex.close();}
				catch(Exception e){
					diditwork=false;
					String error=e.toString(); 		
					Dialog d=new Dialog(this);
					d.setTitle("Dang it!!");
					TextView tv=new TextView(this);
					tv.setText(error); 
					d.setContentView(tv);
					d.show();
				}
			break;
		case R.id.bviewupdated:
			Intent i=new Intent("com.example.xpend.SQLVIEW");
			//Intent i=new Intent("com.example.xpend.MIMIR1");
			startActivity(i);
			break;
		case R.id.bdeleteall:
			try{
				etexpendid.setText("");
				etexpend.setText("");
				etexpendon.setText("");
				etexpenddate.setText("");
				etexpenddesc.setText("");
				Sqlexpend ex2=new Sqlexpend(this);
				ex2.open();
				ex2.deleteallentry();
				ex2.close();
				
				
				}
				catch(Exception e){
					//diditwork=false;
					String error=e.toString(); 	
					Dialog d=new Dialog(this);
					d.setTitle("Dang it!!");
					TextView tv=new TextView(this);
					tv.setText(error); 
					d.setContentView(tv);
					d.show();
				}
			break;
			
		case  R.id.beditback:
			finish();
			break;
			
		case R.id.beditexit:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			
			break;
			
		case R.id.bcalendar:
			showDialog(0);
			break;
		}
	
		
	}
		@Override
		 @Deprecated
	protected Dialog onCreateDialog(int id) {
		  return new DatePickerDialog(this, datePickerListener, year, month, day);
		 }

		 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		  public void onDateSet(DatePicker view, int selectedYear,
		    int selectedMonth, int selectedDay) {
		   etexpenddate.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
		     + selectedYear);
		  }
		 };
}
