package com.example.grabhouse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	ImageView iv;
	static Bitmap bp;
	ListView lv;
	ProgressDialog mProgressDialog;
	Cursor cursor;
	ContentValues values;
	Uri imageUri;
	String imageurl;
	static String lat, city, log;
	Geocoder geocoder;
    List<Address> addresses;
	GPSTracker gpsTracker,gps;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 lv = (ListView) findViewById(R.id.listview);	
		 
		 gpsTracker = new GPSTracker(MainActivity.this);
	        if (gpsTracker.getIsGPSTrackingEnabled())
	        {
	        	
	             lat = String.valueOf(GPSTracker.latitude);    
	             log = String.valueOf(GPSTracker.longitude);    
	                          
	        }
	        else 
	        {         
	            gpsTracker.showSettingsAlert();           
	        }   
	       
           
		iv=(ImageView)findViewById(R.id.image);	    
		gpsTracker = new GPSTracker(MainActivity.this);   
	    }
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
	
 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.add) 
		{	
			gpsTracker = new GPSTracker(MainActivity.this);
			 Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
			 File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
			 intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		     startActivityForResult(intent, 0); 
		  
		  	
		}
			   
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		

			File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");		
			bp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 600);
			startActivityForResult(new Intent(this, update.class), 1);	 			
			}
		
	  public static Bitmap decodeSampledBitmapFromFile(String path,
		        int reqWidth, int reqHeight) {
		    final BitmapFactory.Options options = new BitmapFactory.Options();
		    options.inJustDecodeBounds = true;
		    BitmapFactory.decodeFile(path, options);

		   
		        final int height = options.outHeight;
		        final int width = options.outWidth;
		        options.inPreferredConfig = Bitmap.Config.RGB_565;
		        int inSampleSize = 1;

		        if (height > reqHeight) {
		            inSampleSize = Math.round((float)height / (float)reqHeight);
		        }

		        int expectedWidth = width / inSampleSize;

		        if (expectedWidth > reqWidth) {
		            
		            inSampleSize = Math.round((float)width / (float)reqWidth);
		        }

		    options.inSampleSize = inSampleSize;

		   
		    options.inJustDecodeBounds = false;

		    return BitmapFactory.decodeFile(path, options);
		  }	
	
}
	

