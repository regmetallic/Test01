package org.android.gpsprogressbar;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class GpsProgressbarActivity extends ListActivity {
	/** Called when the activity is first created. */
	String s[] = { "Page 1", "Page 2" , "Page 3", "Page 4", "Page 3", "Page 3", "Page 3", "Page 3" };
	

	    Button searchBtn = null;
	    Intent locatorService = null;
	    AlertDialog alertDialog = null;
	    AlertDialog alert= null;

	    /** Called when the activity is first created. */
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //setContentView(R.layout.main);
	        setListAdapter(new ArrayAdapter <String>(this, android.R.layout.simple_list_item_1, s));
	        
	    }

	        @Override
	    	protected void onListItemClick(ListView l, View v, int position, long id) {
	    		// TODO Auto-generated method stub
	    		//super.onListItemClick(l, v, position, id);	
	    		
	    		/* (non-Javadoc)
	    		 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	    		 */
	    		
	                if (!startService()) {
	                    CreateAlert("Error!", "Service Cannot be started");
	                } else {
	                    Toast.makeText(GpsProgressbarActivity.this, "Service Started",
	                            Toast.LENGTH_LONG).show();
	                }

	            }
	        @Override
			public boolean onCreateOptionsMenu(Menu menu) {
    			// TODO Auto-generated method stub
    			super.onCreateOptionsMenu(menu);
    			MenuInflater blw = getMenuInflater();
    			blw.inflate(R.menu.menu_layout, menu);
    			return true;
    			
    			
    		} // TODO Auto-generated method stub


	    

	    public boolean stopService() {
	        if (this.locatorService != null) {
	            this.locatorService = null;
	        }
	        return true;
	    }

	    public boolean startService() {
	        try {
	            FetchCordinates fetchCordinates = new FetchCordinates();
	            fetchCordinates.execute();
	            return true;
	        } catch (Exception error) {
	            return false;
	        }

	    }

	    public AlertDialog CreateAlert(String title, String message) {
	        AlertDialog alert = new AlertDialog.Builder(this).create();

	        alert.setTitle(title);

	        alert.setMessage(message);
	        alert.show();

	        return alert;
	    }
	    
	    public AlertDialog CreateAlert( String message) {
	        AlertDialog alert = new AlertDialog.Builder(this).create();

	        

	        alert.setMessage(message);
	        alert.setCanceledOnTouchOutside(true);
	        alert.show();
	        

	        return alert;
	    }

	    

	    public class FetchCordinates extends AsyncTask<String, Integer, String> {
	        ProgressDialog progDailog = null;

	        public double lati = 0;
	        public double longi = 0;

	        public LocationManager mLocationManager;
	        public VeggsterLocationListener mVeggsterLocationListener;

	        @Override
	        protected void onPreExecute() {
	            mVeggsterLocationListener = new VeggsterLocationListener();
	            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

	            mLocationManager.requestLocationUpdates(
	                    LocationManager.GPS_PROVIDER, 10, 10,
	                    mVeggsterLocationListener);

	            progDailog = new ProgressDialog(GpsProgressbarActivity.this);
	            progDailog.setMessage("Loading...");
	            progDailog.setIndeterminate(true);
	            progDailog.setCancelable(false);
	            progDailog.show();
	            progDailog.getWindow().setLayout(200, 160);

	        }

	        protected void onPostExecute(String result) {
	            
	            if (result.equals("no")){
	            	CreateAlert( "Cannot get GPS Location!!!");
	            	//Toast.makeText(getBaseContext(), "Unable to get gps location", Toast.LENGTH_SHORT).show();
	            	progDailog.dismiss();
	            }
	            
	            else if(result.equals("yes")) {
	            Intent i = new Intent(GpsProgressbarActivity.this, SecondPage.class);
	    		i.putExtra("latitude",lati);
	    		i.putExtra("longitude", longi);
	    		i.putExtra("Time", t);
	    		progDailog.dismiss();
	    		startActivity(i);
	            }
	    		

	          //  Toast.makeText(GpsProgressbarActivity.this,
	            //        "LATITUDE :" + lati + " LONGITUDE :" + longi,
	              //      Toast.LENGTH_LONG).show();
	        }

	        @Override
	        protected String doInBackground(String... params) {
	            // TODO Auto-generated method stub
	        	Long t = Calendar.getInstance().getTimeInMillis();
	            while (lati == 0  && Calendar.getInstance().getTimeInMillis()-t<10000) {

	            
	            }
	            if(lati == 0){
	            	return "no";
	            }
	            else 
	            return "yes";
	        }
	        long t ;
	       public class VeggsterLocationListener implements LocationListener {

	            @Override
	            public void onLocationChanged(Location location) {

	               // String info = location.getProvider();
	            	Log.d("nLocate","Fix received at time: "+Long.toString(location.getTime()/1000)+"  Now: "+Long.toString((System.currentTimeMillis())/1000));
	            	
	                try {

	                    lati = location.getLatitude();
	                    longi =  location.getLongitude();
	                    

	                } catch (Exception e) {
	                    progDailog.dismiss();
	                     Toast.makeText(getApplicationContext(),"Unable to get Location"
	                     , Toast.LENGTH_LONG).show();
	                }
	                t = location.getTime();

	            }

	            @Override
	            public void onProviderDisabled(String provider) {
	                Log.i("OnProviderDisabled", "OnProviderDisabled");
	            }

	            @Override
	            public void onProviderEnabled(String provider) {
	                Log.i("onProviderEnabled", "onProviderEnabled");
	            }

	            @Override
	            public void onStatusChanged(String provider, int status,
	                    Bundle extras) {
	            	Log.i("onStatusChanged", "onStatusChanged");

	            }

	        }

	    }



		/* (non-Javadoc)
		 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
		 */
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		alert = new AlertDialog.Builder(this).create();

		alert.setMessage("The  Coordinates  location\nused in this application are approximate. If any Mistake occurs in case of coordinates, Nepways is not liable for it.");
		// alert.setOnKeyListener(onKeyListener)
		alert.setCanceledOnTouchOutside(true);

		alert.show();

		return false;

	}
		

		

	    }
	
	