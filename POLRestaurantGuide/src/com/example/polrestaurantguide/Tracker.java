package com.example.polrestaurantguide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
 
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
 
public class Tracker extends Activity {
	Double slat,slon,elat,elon;
    double latitude;
    double longi;
    private static final LatLng JLNStadium = new LatLng(28.590401000000000000,
            77.233255999999980000);
    LatLng JLNS = new LatLng(28.55, 77.54);
    private double pLati, plongi, dLati, dlongi;// previous latitude and
                                                // longitude
 
    private GoogleMap map;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectmap);
        slat=getIntent().getDoubleExtra("slat",0.0);
        slon=getIntent().getDoubleExtra("slon",0.0);
        elat=getIntent().getDoubleExtra("elat",0.0);
        elon=getIntent().getDoubleExtra("elon",0.0);
        //final double src_lati = getIntent().getDoubleExtra("src_lati", 0.0);
       // Log.i("Tracker_Src_lati", src_lati + "");
        //final double src_longi = getIntent().getDoubleExtra("src_longi", 0.0);
        //final double dest_lati = getIntent().getDoubleExtra("dest_lati", 0.0);
        //final double dest_longi = getIntent().getDoubleExtra("dest_longi", 0.0);
 
        //pLati = src_lati;// intialize latitude and longitude here from intent data.
        //plongi = src_longi;
 
        // & get destination lai and longi. from intent data
        //dLati = dest_lati;
        //dlongi = dest_longi;
 
        try {
            // Loading map
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Intent loc_intent;
        loc_intent = new Intent(getBaseContext(), Getlocation.class);
        loc_intent.putExtra("lat", dLati);
        loc_intent.putExtra("lon", dlongi);
        startService(loc_intent);*/
 
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
 
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            Log.i("Here", "onreceived");
            if (bundle != null) {
 
                latitude = bundle.getDouble("lati");
                Log.i("Tag", latitude + "");
 
                longi = bundle.getDouble("longi");
                Log.i("tag", longi + "");
                drawmap(latitude, longi);
            }
        }
    };
 
    public void drawmap(double latid, double longid) {
        // draw on map here
        // draw line from intial to final location and draw tracker location map
 
        Log.i("Tag", "map");
 
        // add line b/w current and prev location.
 
        LatLng prev = new LatLng(pLati, plongi);
        LatLng my = new LatLng(latid, longid);
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(my, 15));
        // map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
 
        Polyline line = map.addPolyline(new PolylineOptions().add(prev, my)
                .width(5).color(Color.BLUE));
        pLati = latid;
        plongi = longid;
 
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter("com.example.mc_project"));
    }
 
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        this.onStop();
    }
 
    @Override
    protected void onStop() {
        
        super.onStop();
    }
 
    
    
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @SuppressLint("NewApi")
    private void initilizeMap() {
        if (map == null) {
            map = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
            Log.i("Tag", "map");
            // draw line b/w intial and final location.
 
            LatLng strt = new LatLng(slat, slon);
            LatLng end = new LatLng(elat, elon);
            Marker start_m = map.addMarker(new MarkerOptions().position(strt)
                    .title("START"));
            Marker end_m = map.addMarker(new MarkerOptions().position(end)
                    .title("JLN"));
 
            //map.moveCamera(CameraUpdateFactory.newLatLngZoom(strt, 15));
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
 
            Polyline line = map.addPolyline(new PolylineOptions()
                    .add(strt, end).width(5).color(Color.RED));
 
            // check if map is created successfully or not
            if (map == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
}
