package com.example.polrestaurantguide;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

@SuppressLint("NewApi") public class ConnectMap extends FragmentActivity implements OnMyLocationButtonClickListener {
	GoogleMap map;
	double elat1,elon1;
	String name;
	GPSTracker gps;
	//ArrayList<LatLng> markerPoints;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectmap1);
		android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
		elat1=getIntent().getDoubleExtra("elat",0.0);
        elon1=getIntent().getDoubleExtra("elon",0.0);
        System.out.println("Final Lat "+elat1);
        System.out.println("Final Lon "+elon1);
       
        name=getIntent().getStringExtra("resname");
        System.out.println(name);
		//markerPoints = new ArrayList<LatLng>();
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
		map=mapFragment.getMap();
		
	//gmap.setOnMapLoadedCallback(this);
	
	if(map!=null){
		 
        // Enable MyLocation Button in the Map
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        LatLng end = new LatLng(elat1, elon1);
        map.moveCamera(CameraUpdateFactory.newLatLng(end));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        Marker start_m = map.addMarker(new MarkerOptions().position(end)
                .title(name));
	}
}
	

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}
@Override
public boolean onMyLocationButtonClick() {
	gps = new GPSTracker(ConnectMap.this);
	if(gps.canGetLocation()) {
		Toast.makeText(getApplicationContext(), "My Location", Toast.LENGTH_LONG).show();
	}
	else {
        gps.showSettingsAlert();
     } 
	return false;
}




	/*public void onMapLoaded() {
		// TODO Auto-generated method stub
		LatLng pol = new LatLng(22.038532, 96.471474);
        gmap.addMarker(new MarkerOptions().position(pol).title("Marker in POL"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(pol));
	}*/

}
