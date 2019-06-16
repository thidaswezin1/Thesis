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

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


public class DirectionRoute extends FragmentActivity implements OnMyLocationButtonClickListener,OnClickListener{
	Button btnroute;
	GoogleMap map;
	ArrayList<LatLng> markerPoints;
	LatLng point1,point2;
	double slat1,slon1,elat1,elon1;
	String name;
	 private LocationManager locationManager;
	 Location location;
	 private String provider;
	 GPSTracker gps;
	 boolean hasNetwrokProvider;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectmap);
		Button btnroute=(Button)findViewById(R.id.button);
		btnroute.setOnClickListener(this);
		
		
		
		android.app.ActionBar bar = getActionBar();
		gps=new GPSTracker(DirectionRoute.this);
		
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
		slat1=getIntent().getDoubleExtra("slat",0.0);
		slon1=getIntent().getDoubleExtra("slon",0.0);
		elat1=getIntent().getDoubleExtra("elat",0.0);
        elon1=getIntent().getDoubleExtra("elon",0.0);
        name=getIntent().getStringExtra("name");
		// Initializing 
		markerPoints = new ArrayList<LatLng>();
		
		// Getting reference to SupportMapFragment of the activity_main
		SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
		
		// Getting Map for the SupportMapFragment
		map = fm.getMap();		
		//LatLng point1=new LatLng(slat1,slon1);
		point1=new LatLng(slat1,slon1); 
		point2 = new LatLng(elat1, elon1);
		map.moveCamera(CameraUpdateFactory.newLatLng(point1));
		map.animateCamera(CameraUpdateFactory.zoomTo(10));
		if(map!=null){
			map.setOnMyLocationButtonClickListener(this);
			// Enable MyLocation Button in the Map
			map.setMyLocationEnabled(true);
			//map.getUiSettings().setAllGesturesEnabled(true);
			//Location myLocation = map.getMyLocation();
			Marker start_m = map.addMarker(new MarkerOptions().position(point2)
	                .title(name));
			
			
				}
			
					
	}
	
	private String getDirectionsUrl(LatLng origin,LatLng dest){
					
		// Origin of route
		String str_origin = "origin="+origin.latitude+","+origin.longitude;
		
		// Destination of route
		String str_dest = "destination="+dest.latitude+","+dest.longitude;		
		
					
		// Sensor enabled
		String sensor = "sensor=false";			
					
		// Building the parameters to the web service
		String parameters = str_origin+"&"+str_dest+"&"+sensor;
					
		// Output format
		String output = "json";
		
		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
		
		
		return url;
	}
	
	/** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
                URL url = new URL(strUrl);

                // Creating an http connection to communicate with url 
                urlConnection = (HttpURLConnection) url.openConnection();

                // Connecting to url 
                urlConnection.connect();

                // Reading data from url 
                iStream = urlConnection.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

                StringBuffer sb  = new StringBuffer();

                String line = "";
                while( ( line = br.readLine())  != null){
                        sb.append(line);
                }
                
                data = sb.toString();

                br.close();

        }catch(Exception e){
                Log.d("Exception while downloading url", e.toString());
        }finally{
                iStream.close();
                urlConnection.disconnect();
        }
        return data;
     }

	
	
	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String>{			
				
		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {
				
			// For storing data from web service
			String data = "";
					
			try{
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			}catch(Exception e){
				Log.d("Background Task",e.toString());
			}
			return data;		
		}
		
		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);			
			
			ParserTask parserTask = new ParserTask();
			
			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
				
		}		
	}
	
	/** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
    	
    	// Parsing the data in non-ui thread    	
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
			
			JSONObject jObject;	
			List<List<HashMap<String, String>>> routes = null;			           
            
            try{
            	jObject = new JSONObject(jsonData[0]);
            	DirectionsJSONParser parser = new DirectionsJSONParser();
            	
            	// Starts parsing data
            	routes = parser.parse(jObject);    
            }catch(Exception e){
            	e.printStackTrace();
            }
            return routes;
		}
		
		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			MarkerOptions markerOptions = new MarkerOptions();
			
			// Traversing through all the routes
			for(int i=0;i<result.size();i++){
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();
				
				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);
				
				// Fetching all the points in i-th route
				for(int j=0;j<path.size();j++){
					HashMap<String,String> point = path.get(j);					
					
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);	
					
					points.add(position);						
				}
				
				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);	
				
			}
			
			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);							
		}			
    }   
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	public boolean onMyLocationButtonClick() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "My Location", Toast.LENGTH_LONG).show();
		return false;
	}
	

    /* Remove the locationlistener updates when Activity is paused */
    
   
   
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng =  location.getLongitude();
        
    }

public LocationListener locationListenerNetwork = new LocationListener() {
    public void onLocationChanged(Location location) {
        // locationResult.gotLocation(location);
        locationManager.removeUpdates(this);
        //locationManager.removeUpdates(locationListenerGps);

    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extra) {
    }

};
@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	LatLng point1=new LatLng(slat1,slon1);
	LatLng point2=new LatLng(elat1,elon1);
	map.setMyLocationEnabled(true);
	map.moveCamera(CameraUpdateFactory.newLatLng(point1));
    map.animateCamera(CameraUpdateFactory.zoomTo(10));
	Marker start_m = map.addMarker(new MarkerOptions().position(point1).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
	
	// Setting onclick event listener for the map
	
			// TODO Auto-generated method stub
			if(markerPoints.size()>1){
				markerPoints.clear();
				map.clear();					
			}
			markerPoints.add(point1);
			markerPoints.add(point2);
			MarkerOptions options = new MarkerOptions();
			
			// Setting the position of the marker
			options.position(point1);
			options.position(point2);
		
			
			// Already two locations				
			
			
			// Adding new item to the ArrayList
			//markerPoints.add(point1);
			
			
			/** 
			 * For the start location, the color of marker is GREEN and
			 * for the end location, the color of marker is RED.
			 */
			if(markerPoints.size()==1){
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				options.title("My Location");
			}else if(markerPoints.size()==2){
				options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				options.title(name);
			}
						
			
			// Add new marker to the Google Map Android API V2
			map.addMarker(options);
			
			// Checks, whether start and end locations are captured
			if(markerPoints.size() >= 2){					
				LatLng origin = markerPoints.get(0);
				LatLng dest = markerPoints.get(1);
				
				// Getting URL to the Google Directions API
				String url = getDirectionsUrl(origin, dest);				
				
				DownloadTask downloadTask = new DownloadTask();
				
				// Start downloading json data from Google Directions API
				downloadTask.execute(url);
			}
	}
}
