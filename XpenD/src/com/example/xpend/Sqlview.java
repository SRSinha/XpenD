package com.example.xpend;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Sqlview extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		TextView tv=(TextView)findViewById(R.id.tvsqlinfo);
		Sqlexpend info=new Sqlexpend(this);		
		info.open();
		String data=info.getdata();
		info.close();
		tv.setText(data);
	}
	
}
