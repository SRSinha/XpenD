package com.example.xpend;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class IngredientHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_PATH="/data/data/com.example.xpend/databases/";
	private static final String DATABASE_NAME="mydtbs.db";
	private static final int SCHEMA_VERSION =1;
	public static final String TABLE_NAME = "mydb";
	public static final String COLUMN_ID = "_id";
	//public static final String COLUMN_TITLE = "mydb_name";
	
	public static final String key_expend = "person_expend";
	public static final String key_on = "expend_on";
	public static final String key_date = "expend_date";
	public static final String key_desc = "expend_desc";
	
	public SQLiteDatabase dbsqlite;
	
	private final Context mycontext;

	public IngredientHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
		// TODO Auto-generated constructor stub
		this.mycontext=context;
		//check if exists and copy from resources
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldver, int newver) {
		// TODO Auto-generated method stub
		
	}
	
	public void createDatabase(){
		createDB();
	}

	private void createDB() {
		// TODO Auto-generated method stub
		boolean dbexists= DBExists();
		if(!dbexists){
			//By calling this method we create an empty database into the default system location 
			//We need this so we can overwrite that database with our database.
			this.getReadableDatabase();
			copyDBFromResource();
			
		}
		
	}

	private boolean DBExists() {
		// TODO Auto-generated method stub
		SQLiteDatabase db= null;
		
		try{
			String databasepath=DATABASE_PATH + DATABASE_NAME;
			db=SQLiteDatabase.openDatabase(databasepath, null, SQLiteDatabase.OPEN_READWRITE);
			db.setLocale(Locale.getDefault());
			db.setLockingEnabled(true);
			db.setVersion(1);
		}
		catch(SQLiteException e){
			Log.e("SqlHelper", "database not found");
		}
		if(db!=null){
			db.close();
		}
		return db != null ? true : false;
	}

	private void copyDBFromResource() {
		// TODO Auto-generated method stub
		InputStream instream= null;
		OutputStream outstream= null;
		String dbfilepath= DATABASE_PATH + DATABASE_NAME;
		
		try{
			instream = mycontext.getAssets().open(DATABASE_NAME);
			outstream= new FileOutputStream(dbfilepath);
			
			byte[] buffer= new byte[1024];
			int length;
			while((length=instream.read(buffer))>0){
				outstream.write(buffer, 0, length);
				
			}
			outstream.flush();
			outstream.close();
			instream.close();
		}
		catch(IOException e){
			throw new Error("Problem copying DB from resource file");
		}
	}
	 public void openDatabase() throws SQLException{
		 String mypath= DATABASE_PATH + DATABASE_NAME;
		 dbsqlite = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
	 }
	 @Override
	 public synchronized void close(){
		 if(dbsqlite != null)
			 dbsqlite.close();
		 super.close();
	 }
	 public Cursor getCursor(){
		 SQLiteQueryBuilder querybuilder = new SQLiteQueryBuilder();
		 querybuilder.setTables(TABLE_NAME);
		 String[] ascoltoreturn = new String[] {COLUMN_ID, key_date};
		 Cursor mCursor= querybuilder.query(dbsqlite, ascoltoreturn, null, null, null, null, "mydb_name ASC");
		 return mCursor;
	 }
	 public String getName(Cursor c){
			return(c.getString(1));
		}

}
