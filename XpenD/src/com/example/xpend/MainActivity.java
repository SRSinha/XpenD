package com.example.xpend;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Thread timer=new Thread()	
		{
			public void run()
			{
				try
				{
					sleep(1500);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();		
				}
				finally
				{								
					Intent opendu=new Intent("com.example.xpend.DURATION");
					startActivity(opendu);		
				}
			}
		};
		timer.start();			
	}

	@Override
	protected void onPause() {
		
		super.onPause();
		finish();
	}
}

	
