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
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class GoogleMapRoute extends FragmentActivity {
	Double slat,slon,elat,elon;
	LatLng start,end;
	String resname;
	//private static final LatLng LOWER_MANHATTAN = new LatLng(40.722543,-73.998585);
	//private static final LatLng BROOKLYN_BRIDGE = new LatLng(40.7057, -73.9964);
	

	GoogleMap googleMap;
	final String TAG = "PathGoogleMapActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.googlemaproute);
		slat=getIntent().getDoubleExtra("slat", 0.0);
		slon=getIntent().getDoubleExtra("slon",0.0);
        elat=(double)getIntent().getFloatExtra("elat",0);
        elon=(double)getIntent().getFloatExtra("elon",0);
        System.out.println(slat);
        System.out.println(slon);
        System.out.println(elat);
        System.out.println(elon);
        start=new LatLng(slat,slon);
        end = new LatLng(elat, elon);
        resname=getIntent().getStringExtra("name");
		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = fm.getMap();
		//start=new LatLng(googleMap.getMyLocation().getLatitude(),googleMap.getMyLocation().getLongitude());
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(start));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10)); 
		MarkerOptions options = new MarkerOptions();
		options.position(start);
		options.position(end);
		googleMap.addMarker(options);
		
		Marker start_m = googleMap.addMarker(new MarkerOptions().position(start)
                .title("Current"));
		Marker end_m = googleMap.addMarker(new MarkerOptions().position(end)
                .title(resname));
		//String url = getMapsApiDirectionsUrl();
		//ReadTask downloadTask = new ReadTask();
		//downloadTask.execute(url);
		String url = getDirectionsUrl(start, end);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
		//googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(end,13));
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

	        StringBuffer sb = new StringBuffer();

	        String line = "";
	        while( ( line = br.readLine()) != null){
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
	    	ArrayList<LatLng> points = new ArrayList<LatLng>();;
            PolylineOptions lineOptions = new PolylineOptions();
	        MarkerOptions markerOptions = new MarkerOptions();

	        // Traversing through all the routes
	        for(int i=0;i<result.size();i++){
	            

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
	        googleMap.addPolyline(lineOptions);
	    }

	/*private String getMapsApiDirectionsUrl() {
		String waypoints = "waypoints=optimize:true|"+ start.latitude + "," + start.longitude
				+ "|" + "|" + end.latitude + ","
				+ end.longitude ;

		String sensor = "sensor=false";
		String params = waypoints + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		return url;
	}

	private void addMarkers() {
		if (googleMap != null) {
			googleMap.addMarker(new MarkerOptions().position(start)
					.title("Current"));
			googleMap.addMarker(new MarkerOptions().position(end)
					.title(resname));
			
		}
	}

	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = new ArrayList<LatLng>();;
            PolylineOptions polyLineOptions = new PolylineOptions();;
			LatLng position=null;
            //ArrayList<LatLng> points;
			//PolylineOptions polyLineOptions = null;
			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					position = new LatLng(lat, lng);

					points.add(position);
				}
				googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
	            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
				polyLineOptions.addAll(points);
				polyLineOptions.width(2);
				polyLineOptions.color(Color.RED);
			}

			if(polyLineOptions != null) {
				googleMap.addPolyline(polyLineOptions);
			   }
			else {
			    Log.d("onPostExecute","without Polylines drawn");
			   }
		}*/
	}
}
