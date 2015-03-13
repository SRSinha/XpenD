package com.example.xpend;

import android.app.Activity;
import android.os.Bundle;

public class Mimir1 extends Activity{
	private IngredientHelper dbinghelp= null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mimain);
		
		dbinghelp = new IngredientHelper(this);
		dbinghelp.createDatabase();	//to check if never created db before orr never used before
	}

}
