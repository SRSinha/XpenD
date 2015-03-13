package com.example.xpend;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class Mimir2 extends Activity{
	private IngredientHelper dbinghelp= null;
	private Cursor ourcursor = null;
	private IngredientAdapter adapter = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.mimain);
			
			ListView mylistview = (ListView)findViewById(R.id.mylistview);
			dbinghelp =new IngredientHelper(this);
			dbinghelp.createDatabase();
			dbinghelp.openDatabase();
			ourcursor = dbinghelp.getCursor();
			startManagingCursor(ourcursor);
			adapter= new IngredientAdapter(ourcursor);
			mylistview.setAdapter(adapter);
		}
		catch(Exception e){
			Log.e("ERROR","ERROR IN CODE:"+ e.toString());
			e.printStackTrace();
		}
	}
	
	class IngredientAdapter extends CursorAdapter{
		IngredientAdapter(Cursor c){
			super(Mimir2.this, c);
		}

		@Override
		public void bindView(View row, Context cntxt, Cursor c) {
			// TODO Auto-generated method stub
			IngredientHolder holder= (IngredientHolder)row.getTag();
			holder.populateFrom(c, dbinghelp);
		}

		@Override
		public View newView(Context cntxt, Cursor c, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater= getLayoutInflater();
			View row=inflater.inflate(R.layout.row , parent,false);
			
			IngredientHolder holder=new IngredientHolder(row);
			row.setTag(holder);
			return(row);
		}
		
	}
	static class IngredientHolder { 
		private TextView name=null; 
		IngredientHolder(View row) { 
		name=(TextView)row.findViewById(R.id.tvtextout); 
		} 
		void populateFrom(Cursor c, IngredientHelper r) { 
		name.setText(r.getName(c)); 
		} 
		}
}
