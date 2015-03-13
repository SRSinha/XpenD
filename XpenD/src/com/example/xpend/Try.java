package com.example.xpend;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;



public class Try extends Activity {
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trymain);

        LinearLayout myLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT,    LayoutParams.WRAP_CONTENT);
        TextView[] pairs=new TextView[4];
        for(int l=0; l<4; l++)
        {
            pairs[l] = new TextView(this);
            pairs[l].setTextSize(15);
            pairs[l].setLayoutParams(lp);
            pairs[l].setId(l);
            pairs[l].setText((l + 1) + ": something");
            myLayout.addView(pairs[l]);
        }

    }
}