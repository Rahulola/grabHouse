package com.example.grabhouse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class update extends Activity{
	
	ImageView iv;
	TextView tvlat,tvlog, tvcity;
	boolean check = false;
    GPSTracker gps;   
    String lat,log,add,ADDRESS;
    Geocoder geocoder;
    List<Address> addresses;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update);
	
		    iv = (ImageView)findViewById(R.id.image);
	        tvlat = (TextView)findViewById(R.id.lat);
	        tvlog = (TextView)findViewById(R.id.log);
	        tvcity = (TextView)findViewById(R.id.city);
	        iv.setImageBitmap(MainActivity.bp);	      
	        tvcity.setText(MainActivity.city);
	
	        Button bt = (Button) findViewById(R.id.getdetail);
	        bt.setOnClickListener(new Button.OnClickListener() {
	   
		public void onClick(View v) 
	    {		  
	    		    	    	
	    	try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    	
	    	  gps = new GPSTracker(getApplicationContext());
		        if (gps.getIsGPSTrackingEnabled())
		        {	        	
		             lat = String.valueOf(GPSTracker.latitude);    
		             log = String.valueOf(GPSTracker.longitude);    		                          
		        }
		       
		        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		        try {
					addresses = geocoder.getFromLocation(GPSTracker.latitude,GPSTracker.longitude, 2);
					String add = addresses.get(0).getAddressLine(0); 
					String locality = addresses.get(0).getLocality();
					String state = addresses.get(0).getAdminArea();
				    String country = addresses.get(0).getCountryName();
				    String postalCode = addresses.get(0).getPostalCode();
				   	
				   	ADDRESS = add+locality+state+country+postalCode;
				   	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        tvlat.setText(lat);
		        tvlog.setText(log);
		        tvcity.setText(ADDRESS);
	    }
	    
	    });
	 
	 Button cancle = (Button)findViewById(R.id.cancle);
	 cancle.setOnClickListener(new Button.OnClickListener() {
		   
			public void onClick(View v) 
		    {		  
		       Intent i = new Intent(getBaseContext(), MainActivity.class);
		       startActivity(i);
		    }
		    
		    });
	  }
	
	}	

