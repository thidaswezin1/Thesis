package com.example.polrestaurantguide;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
 
public class Getlocation extends Service implements LocationListener {
 
    private Context Context;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    int flag = 0;
    Location location;
    Notification notification;
    Location mylocation = new Location("");
    Location dest_location = new Location("");
    float distance;
    NotificationManager notifier;
    public double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 40;// 40 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 2;
 
    // update location within a time period of 2 minutes
 
    @Override
    public void onCreate() {
        
        super.onCreate();
        Log.i("Tag", "on create");
 
    }
 
    @Override
    public void onDestroy() {
        
        Log.i("Indestroy", "destroyed");
        flag = 0;
        stopSelf();
        stopUsingGPS();
        super.onDestroy();
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        
        Context = this;
        Log.i("tag", "on start");
        mylocation = getLocation(Context);
 
        Double msg = mylocation.getLatitude();
        Log.i("my long", msg.toString());
 
        Double dest_lat = intent.getDoubleExtra("lat", 0.0);
        Double dest_lon = intent.getDoubleExtra("lon", 0.0);
        Log.i("get lat", dest_lat.toString());
        Log.i("get lon", dest_lon.toString());
 
        this.dest_location.setLatitude(dest_lat);
        this.dest_location.setLongitude(dest_lon);
        Log.i("get lon", dest_lon.toString());
 
        return START_NOT_STICKY;
    }
 
    protected LocationManager locationManager;
 
    public Location getLocation(Context Context) {
 
        try {
            locationManager = (LocationManager) Context
                    .getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.i("No gps and No Network ",
                        "No gps and No Network is enabled enable either one of them");
                Toast.makeText(this, "Enable either Network or GPS",
                        Toast.LENGTH_LONG).show();
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        return location;
    }
 
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(Getlocation.this);
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
 
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
 
    @Override
    public void onLocationChanged(Location location) {
 
        mylocation = getLocation(Context);
        Log.i("Tag", "location changed");
        distance = mylocation.distanceTo(dest_location);
        Log.i("Tag", "" + distance);
        if (flag == 0) {
            Intent intent1 = new Intent("com.example.mc_project");
            intent1.putExtra("lati", mylocation.getLatitude());
            intent1.putExtra("longi", mylocation.getLongitude());
            sendBroadcast(intent1);
 
            //Toast.makeText(this, "broadcasted", Toast.LENGTH_LONG).show();
            if ((distance) < 600) {
                Log.i("Distance", "dist. b/w < 1km");
                Log.d("location", "" + distance);
                NotificationManager notificationManager = (NotificationManager) Context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        Context, 1, new Intent(Context, MainActivity.class), 0);
                Notification notification = new Notification(
                        R.drawable.ic_launcher,
                        "You areached ur destination!!",
                        System.currentTimeMillis());
                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.setLatestEventInfo(Context,
                        "You areached ur destination!!", "You areached ur destination!!",
                        pendingIntent);
 
                notificationManager.notify(11, notification);
                Vibrator vi = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
 
                vi.vibrate(1000);
                flag = 1;
                onDestroy();
            }
        }
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
    public void onStartService() {
        
 
    }
 
    public void onStopService() {
        
 
    }
 
    public void onReceiveMessage(Message msg) {
        
 
    }
 
}
