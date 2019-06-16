package com.example.polrestaurantguide;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint("NewApi") public class NearestRestaurantInfo extends Activity {
	double lat1,long1,lat2,long2,distance,range1;float latt,longg;
	TextView tv1,tv2,tv3;ListView listView;String name;
	ArrayList<HashMap<String,String>> fill;
	ArrayList<HashMap<String,String>> newfill;
	ArrayList<Double> distanceArray;
	HashMap<String,String> map1;
	double addDist;
	int j=0;
	boolean flag=true;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.nearestrestaurantinfo);
        tv1=(TextView)findViewById(R.id.tv);
        
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>Nearest Restaurants</font>"));
       // ArrayList<String> listItem=new ArrayList<String>();
       
        //tv1=(TextView)findViewById(R.id.tvname);
        this.listView = (ListView) findViewById(R.id.listView);
       // tv2=(TextView)findViewById(R.id.textView1);
        //tv3=(TextView)findViewById(R.id.textView2);
        String[] from=new String[]{"rowid","col"};
        int[] to=new int[]{R.id.name_tv,R.id.dist_tv};
        fill=new ArrayList<HashMap<String,String>>();
        newfill=new ArrayList<HashMap<String,String>>();
        distanceArray=new ArrayList<Double>();
        String lati=getIntent().getStringExtra("lati");
        String longi=getIntent().getStringExtra("longi");
        String range=getIntent().getStringExtra("range");
       
        
 		System.out.println("Range of user input :"+range1);
        System.out.println("latitude1 Carry "+lati);
        System.out.println("longitude1 Carry "+longi);
        lat1=Double.parseDouble(lati);
        long1=Double.parseDouble(longi);
       
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<RestaurantInfo> location = databaseAccess.getRestaurantInformation();
        databaseAccess.close();
        if(!range.equals("")){
        	range1=Double.parseDouble(range);
        	for(RestaurantInfo i:location){
       
        	latt=i.getLatitude();
        	longg=i.getLongitude();
        	System.out.println("Latitude2"+latt);
        	System.out.println("Longitude2"+longg);
        	distance=getDistanceFromLatLonInKm(lat1, long1, latt, longg);
        	System.out.println("Distance"+(++j)+" "+distance);
        	distanceArray.add(distance);
        	
        	
        	if(distance<=range1){
        		
        		double d=((double)((int)(distance*100.0)))/100.0;
        		HashMap<String,String>map=new HashMap<String,String>();
        		map.put("rowid", ""+i.getName());
        		map.put("col", String.valueOf(d)+" km");
        		fill.add(map);
        		Collections.sort(fill,new Comparator<HashMap<String,String>>(){
        			public int compare(HashMap<String,String>l,HashMap<String,String>r){
        				return l.get("col").compareTo(r.get("col"));
        			}
        		});
        		System.out.println("Restaurnt name:"+i.getName());
        		//listItem.add(i.getName());
        		
        		
        		/*tv1.append(i.getName());
        		tv1.append("\n"+i.getAddress());
        		tv1.append("\n"+i.getTime());
        		tv1.append("\n\n");*/
        		/*DatabaseAccess dbAccess = DatabaseAccess.getInstance(this);
                dbAccess.open();
                System.out.println(i);
                List<String> info = dbAccess.getRestaurantInfoLatLong(latt,longg);
                dbAccess.close();
                tv1.setText(info.get(0));
                tv2.setText(info.get(1));
                tv3.setText(info.get(2));*/
        		System.out.println("This restaurant is located in your wanted range.");
        	}
        	/*else{
        		addDist=range1+1;
        		while(addDist<=distance){
					if(distance<=addDist){
						tv1.setText("If you give the range "+(int)addDist+ "km, you can find the restauarants");
						
					}
					addDist+=1;
				}
        		tv1.setText("If you give the range "+(int)(addDist-1)+ "km, you can find the restauarants");
        	}*/
       }SimpleAdapter adap=new SimpleAdapter(this,fill,R.layout.list_item,from,to);
		listView.setAdapter(adap);
		 //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
	       // this.listView.setAdapter(adapter);
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	public void onItemClick(AdapterView<?> parent,View view,int position,long id){
	        		HashMap<String,String> map=(HashMap<String,String>)listView.getItemAtPosition(position);
	        		String nameOfRestaurant=map.get("rowid");
	        		String distance1=map.get("col");
	        		Log.i("Value of restaurant name ",nameOfRestaurant);
	        		System.out.println(nameOfRestaurant);
	        		Intent intent1=new Intent(getApplicationContext(),ShowingNearestDetail.class);
	        		intent1.putExtra("n", nameOfRestaurant);
	        		intent1.putExtra("dist", distance1);
	        		
	        		startActivity(intent1);
	        	}
	        } );
      }
        else if(range.equals("")){
        	for(RestaurantInfo i:location){
        	       
            	latt=i.getLatitude();
            	longg=i.getLongitude();
            	distance=getDistanceFromLatLonInKm(lat1, long1, latt, longg);
            	double d=((double)((int)(distance*100.0)))/100.0;
        		HashMap<String,String>map=new HashMap<String,String>();
        		map.put("rowid", ""+i.getName());
        		map.put("col", String.valueOf(d)+" km");
        		fill.add(map);
        		Collections.sort(fill,new Comparator<HashMap<String,String>>(){
        			public int compare(HashMap<String,String>l,HashMap<String,String>r){
        				return l.get("col").compareTo(r.get("col"));
        			}
        		});
        	}
        	for(int i=0;i<7;i++){
        		map1=fill.get(i);
        		newfill.add(map1);
        	}
        	SimpleAdapter adap=new SimpleAdapter(this,newfill,R.layout.list_item,from,to);
    		listView.setAdapter(adap);
    		 //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
    	       // this.listView.setAdapter(adapter);
    	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	        	public void onItemClick(AdapterView<?> parent,View view,int position,long id){
    	        		HashMap<String,String> map=(HashMap<String,String>)listView.getItemAtPosition(position);
    	        		String nameOfRestaurant=map.get("rowid");
    	        		String distance1=map.get("col");
    	        		Log.i("Value of restaurant name ",nameOfRestaurant);
    	        		System.out.println(nameOfRestaurant);
    	        		Intent intent1=new Intent(getApplicationContext(),ShowingNearestDetail.class);
    	        		intent1.putExtra("n", nameOfRestaurant);
    	        		intent1.putExtra("dist", distance1);
    	        		
    	        		startActivity(intent1);
    	        	}
    	        } );
        }
        
        
        if(fill.isEmpty()){
        	Collections.sort(distanceArray);
        	double d=distanceArray.get(0)+1;
        	tv1.setText("                  ***SORRY***\n No Restauants can exist within this range.\n\n\n\n              ***RECOMMENDATION***\n-Restaurants can be found within around "+(int)d+" km.\n-You can search again!!!.");
       	//Toast.makeText(getApplicationContext(),"Sorry!No restaurants can exist within this range.\nသည္အကြာအေ၀းအတြင္း မည္သည့္ စားေသာက္ဆိုင္မွ မရွိပါ",Toast.LENGTH_LONG).show();
        	bar.setTitle(Html.fromHtml("<font color='#ffffff'>No Restaurant</font>"));
        }
 		
	}
	private double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2, double lon2) {
				
				
		int R = 6371; // Radius of the earth in km
		double dLat=deg2rad(lat2-lat1);  // deg2rad below
		double dLon=deg2rad(lon2-lon1); 
		double a= (Math.sin(dLat/2) * Math.sin(dLat/2)) + (Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2)); 
		//double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double c=2*Math.asin(Math.sqrt(a));
		double d = R * c; // Distance in km
		return d;
	}

	private double deg2rad(double d) {
					 
	return d * (Math.PI/180);

	}
}
