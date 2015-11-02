package com.example.grabhouse;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;


public class GPSTracker extends Service implements LocationListener {

    private static String TAG = GPSTracker.class.getName();

    private final Context mContext;
    
    boolean isGPSEnabled = false;

  
    boolean isNetworkEnabled = false;

 
    boolean isGPSTrackingEnabled = false;

  Location location;
  static double lat;
  static double log;
  static double latitude;
  static double longitude;
  static boolean check = false;

  
    int geocoderMaxResults = 1;

    
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

   
    protected LocationManager locationManager;

   
    private String provider_info;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    /**
     * Try to get my current location by GPS or Network Provider
     */
    public void getLocation() {

        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

           
            if (isGPSEnabled) {
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use GPS Service");

              
                provider_info = LocationManager.GPS_PROVIDER;

            } else if (isNetworkEnabled) {
                this.isGPSTrackingEnabled = true;

                Log.d(TAG, "Application use Network State to get GPS coordinates");

              
                provider_info = LocationManager.NETWORK_PROVIDER;

            } 

            
            if (!provider_info.isEmpty()) {
                locationManager.requestLocationUpdates(
                    provider_info,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, 
                    this
                );

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider_info);
                    updateGPSCoordinates();
                }
            }
        }
        catch (Exception e)
        {
           
            Log.e(TAG, "Impossible to connect to LocationManager", e);
        }
    }

   
    public void updateGPSCoordinates() {
        if (location != null) {
            lat = location.getLatitude();
            latitude = Math.round(lat * 100.0) / 100.0;
            log = location.getLongitude();
            longitude = Math.round(log * 100.0) / 100.0;
            
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }

  
    public boolean getIsGPSTrackingEnabled() {

        return this.isGPSTrackingEnabled;
    }

   
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public void showSettingsAlert() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				mContext);
		dialog.setMessage("Turn on your GPS?");
		dialog.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface paramDialogInterface,
							int paramInt) {
						
						
						Intent myIntent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						mContext.startActivity(myIntent);	
						
					}							
				});
		
		dialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(
							DialogInterface paramDialogInterface,
							int paramInt) {
						
					}
				});
		dialog.show();
		
		
    }

    
  
    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}