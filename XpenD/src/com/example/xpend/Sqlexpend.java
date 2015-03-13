package com.example.xpend;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Sqlexpend extends Activity {
	//we want our column names never to change..so static
	public static final String key_rowid = "_id"; 
	public static final String key_expend = "person_expend";
	public static final String key_on = "expend_on";
	public static final String key_date = "expend_date";
	public static final String key_desc = "expend_desc";
	// NOW SETTING UP DATABASE.
	public static final String databse_name = "sqlextenddb"; // our db name
	private static final String database_table = "expendtable"; // table hold
	private static final int database_version = 6; // put an number.just a
	
	private dbhelper ourhelper;
	private final Context ourcontext; // gets content of expenditure
	public SQLiteDatabase ourdb;
	public static int entries=0;
	String expend1,onwhom1,date1,desc;
	
	private static class dbhelper extends SQLiteOpenHelper {
		public dbhelper(Context context) { // CONSTTRUCTOR
			super(context, databse_name, null, database_version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) { // ONLY CALLED WHEN FIRST TIME
				db.execSQL("CREATE TABLE " + database_table + " (" + key_rowid
					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ // we set up first column//reference by those lines
					key_expend + " TEXT NOT NULL, " + key_on
					+ " TEXT NOT NULL," + key_date + " TEXT NOT NULL, " + key_desc + " TEXT NOT NULL);"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) { 
			db.execSQL("DROP TABLE IF EXISTS " + database_table);
			onCreate(db);
		}
		}


	public Sqlexpend(Context c) {
		// TODO Auto-generated constructor stub
		ourcontext = c; // this context receives from sqliteeg.java
		}
	
	public Sqlexpend open() throws SQLException {
		// TODO Auto-generated method stub
		ourhelper = new dbhelper(ourcontext); 
		ourdb = ourhelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		// TODO Auto-generated method stub
		ourhelper.close();
	}

	
	public long createEntry(String expend, String onwhom,String date,String desc) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues(); 
		cv.put(key_expend, expend); // (where we wanna save it in db,)
		cv.put(key_on, onwhom);
		cv.put(key_date, date);
		cv.put(key_desc, desc);
		return ourdb.insert(database_table, null, cv); // basically insert all// values into table with columns.
		//row_ID returned if success
	}

	public String getdata() {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,key_expend,key_on,key_date,key_desc};
		//CURSOR USED TO READ DATA
		Cursor c= ourdb.query(database_table, columns, null, null, null, null,null);
		String result=""; //string returned when this method called	
		
		int irow=c.getColumnIndex(key_rowid);		//cursor will be lookin for col with this id n return its int
		int iexpend=c.getColumnIndex(key_expend);
		int iexpendon=c.getColumnIndex(key_on);
		int idate=c.getColumnIndex(key_date);
		int idesc=c.getColumnIndex(key_desc);
	
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())		//read first to last
		{
			result=result+ c.getString(irow) + " " + c.getString(iexpend) + " " +c.getString(iexpendon) +c.getString(idate)+ c.getString(idesc) +"\n";
		}
		return result;
	}
		
		
	
	
	public String getexpend(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,key_expend,key_on,key_date,key_desc};
		Cursor c=ourdb.query(database_table, columns, key_rowid + "=" + l , null,null,null,null);
		if(c!=null){
			c.moveToFirst();
			String expend=c.getString(1);		//string data of COLUMN 1		//first 0-rowid
			return expend;
		}	
		return null;
	}
	
	public String getexpendon(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,key_expend,key_on,key_date,key_desc};
		Cursor c=ourdb.query(database_table, columns, key_rowid + "=" + l , null,null,null,null);
		if(c!=null){
			c.moveToFirst();
			String on=c.getString(2);		//string data of COLUMN 1		//first 0-rowid
			return on;
		}	
		return null;
	}
	
	public String getexpenddate(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,key_expend,key_on,key_date,key_desc};
		Cursor c=ourdb.query(database_table, columns, key_rowid + "=" + l , null,null,null,null);
		if(c!=null){
			c.moveToFirst();
			String date=c.getString(3);		//string data of COLUMN 1		//first 0-rowid
			return date;
		}	
		return null;
	}
	
	public String getexpenddesc(long l) throws SQLException {
		// TODO Auto-generated method stub
		String[] columns=new String[]{key_rowid,key_expend,key_on,key_date,key_desc};
		Cursor c=ourdb.query(database_table, columns, key_rowid + "=" + l , null,null,null,null);
		if(c!=null){
			c.moveToFirst();
			String desc=c.getString(4);		//string data of COLUMN 1		//first 0-rowid
			return desc;
		}	
		return null;
	}

	public void updateentry(long lrow, String name, String hotness,String date,String desc) throws SQLException {		//tut-123
		// TODO Auto-generated method stub
		ContentValues cvupdate=new ContentValues();
		cvupdate.put(key_expend, name);
		cvupdate.put(key_on, hotness);
		cvupdate.put(key_date,date);
		cvupdate.put(key_desc,desc);
		ourdb.update(database_table, cvupdate, key_rowid +"="+ lrow, null);
	}
	
	public void deleteentry(long lrow1) throws SQLException {
		// TODO Auto-generated method stub
		ourdb.delete(database_table, key_rowid + "=" + lrow1, null);
		
	}
	
	public void deleteallentry() throws SQLException {
		// TODO Auto-generated method stub
		
		/*AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		 
        // Setting Dialog Title
        alertDialog.setTitle("Save File...");

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to save this file?");
        alertDialog.setCancelable(false);
        // Setting Icon to Dialog
       //alertDialog.setIcon(R.drawable.save);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // User pressed YES button. Write Logic Here
            Toast.makeText(getApplicationContext(), "You clicked on YES",
                                Toast.LENGTH_SHORT).show();*/
            ourdb.execSQL("DROP TABLE IF EXISTS " + database_table);
    	    ourdb.execSQL("CREATE TABLE IF NOT EXISTS " + database_table + " (" + key_rowid
    					+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
    					+ // we set up first column//reference by those lines
    					key_expend + " TEXT NOT NULL, " + key_on
    					+ " TEXT NOT NULL," + key_date + " TEXT NOT NULL," + key_desc + " TEXT NOT NULL);"
    					);
      
	//ourdb.deleteDatabase(file)	
       /*     }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // User pressed No button. Write Logic Here
            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
            }
        });

        // Setting Netural "Cancel" Button
        alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            // User pressed Cancel button. Write Logic Here
            Toast.makeText(getApplicationContext(), "You clicked on Cancel",
                                Toast.LENGTH_SHORT).show();
            }
        });

        // Showing Alert Message
        alertDialog.create().show();
            	*/
			
                
		
	}

	public void vacuum() {
		// TODO Auto-generated method stub
		ourdb.execSQL("VACUUM");
	}
	
	
	
}
