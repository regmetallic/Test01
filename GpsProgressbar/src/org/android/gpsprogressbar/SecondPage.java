package org.android.gpsprogressbar;



import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondPage extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.secondpage);
		TextView tv = (TextView) findViewById(R.id.textView1);
		//Long t1 = Calendar.getInstance().getTimeInMillis();
		Long t1 = System.currentTimeMillis();
		//while(Calendar.getInstance().getTimeInMillis()-t1<10000);
		//Long t2 = Calendar.getInstance().getTimeInMillis();
		
		double latit = getIntent().getDoubleExtra("latitude", -1.0);
		double longt = getIntent().getDoubleExtra("longitude", -1.0);
		long time = getIntent().getLongExtra("Time", -1);
		long time1 = t1 - time;
		String res =  "LATITUDE :" + latit + "\nLONGITUDE :" + longt +"\nTIME:" + time1;
		
		tv.setText(res);
		
		
		
		
	}
	
	
}
