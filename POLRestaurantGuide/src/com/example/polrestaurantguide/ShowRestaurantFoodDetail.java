package com.example.polrestaurantguide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class ShowRestaurantFoodDetail extends Activity {
	String food;int p;		//food and price from database
	int fav;		//variable for conditional probability
	double conditionProb;
	TextView tv;
	ListView listview;
	float lat,lon;
	double distance1;
	double currentLat,currentLon;
	double addDist;
	List<HashMap<String, String>> fill;
	ArrayList<Double> distanceArray;
	
	
	android.app.ActionBar bar;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.showrestaurantfooddetail);
        bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        bar.setTitle(Html.fromHtml("<font color='#ffffff'>Nearest Restaurants</font>"));
        listview=(ListView)findViewById(R.id.listView);
        tv=(TextView)findViewById(R.id.tv);
        
        String[] from=new String[]{"rowid","col"};
        int[] to=new int[]{R.id.name_tv,R.id.dist_tv};
        fill=new ArrayList<HashMap<String,String>>();
        distanceArray=new ArrayList<Double>();
        
        currentLat=getIntent().getDoubleExtra("lat",0.0);
        currentLon=getIntent().getDoubleExtra("lon",0.0);
        String userfood=getIntent().getStringExtra("userfood");
        String price=getIntent().getStringExtra("price");
        String distance=getIntent().getStringExtra("distance");
        
        System.out.println("current latitude and Longitude "+currentLat+" "+currentLon);
        System.out.println("UserInputFood "+userfood);
        System.out.println("UserInputPrice "+price);
        System.out.println("UserInputDistance "+distance);
        
        //float currentLat=(float)Double.parseDouble(lati);
        //float currentLon=(float)Double.parseDouble(longi);
        int userPrice=Integer.parseInt(price);
        
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<FoodAndPrice> info = databaseAccess.getFoodAndPrice();
        databaseAccess.close();
        if(!distance.equals("")){
        	double dist=Double.parseDouble(distance);
        	for(FoodAndPrice i:info){
        	System.out.println("Food and price from DB "+i.getFood()+""+i.getPrice());
        	food=i.getFood();
        	p=i.getPrice();
        	if(userfood.equals(food)){
        		
        		
        		if(userPrice>=p){
        			
        			lat=i.getLatitude();
        			lon=i.getLongitude();
        			distance1=getDistanceFromLatLonInKm(currentLat,currentLon,i.getLatitude(),i.getLongitude());
        			distanceArray.add(distance1);
        			if(distance1<=dist){
	            		double d=((double)((int)(distance1*100.0)))/100.0;
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
        			
        				
        			
        		}
        		
        	}		
            		SimpleAdapter adap=new SimpleAdapter(this,fill,R.layout.list_item,from,to);
            		listview.setAdapter(adap);
            		 
            	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            	    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
            	        HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
            	        String nameOfRestaurant=map.get("rowid");
            	        String distance1=map.get("col");
            	        Log.i("Value of restaurant name ",nameOfRestaurant);
            	        System.out.println(nameOfRestaurant);
            	        Intent intent1=new Intent(getApplicationContext(),ShowNearestRestaurantFoodDetail.class);
            	        intent1.putExtra("n", nameOfRestaurant);
            	        intent1.putExtra("dist", distance1);
            	        
            	        startActivity(intent1);
            	        	}
            	        } );
        		
        	
        	
        }
        	if(fill.isEmpty()){
        		if(distanceArray.isEmpty()){
        			tv.setText("                   ***SORRY***\nThe food with your afforable price cannot be available in the restaurants.\n" );
        			bar.setTitle(Html.fromHtml("<font color='#ffffff'>No Restaurant</font>"));
        		}
        		else {
	            	Collections.sort(distanceArray);
	            	double d=distanceArray.get(0)+1;
	            	tv.setText("                   ***SORRY***\n Restaurants cannot be found.\n\n\n\n              ***RECOMMENDATION***\n-Restaurants that have your desirable food exist within around "+(int)d+"  km.\n-You can search again.");
	           	//Toast.makeText(getApplicationContext(),"Sorry!No restaurants can exist within this range.\nသည္အကြာအေ၀းအတြင္း မည္သည့္ စားေသာက္ဆိုင္မွ မရွိပါ",Toast.LENGTH_LONG).show();
	            	bar.setTitle(Html.fromHtml("<font color='#ffffff'>No Restaurant</font>"));
        		}
        	}
        }//end if
        else if(distance.equals("")){
        	for(FoodAndPrice i:info){
            	System.out.println("Food and price from DB "+i.getFood()+""+i.getPrice());
            	food=i.getFood();
            	p=i.getPrice();
            	if(userfood.equals(food)){	
            		if(userPrice>=p){
            			
	            		lat=i.getLatitude();
	            		lon=i.getLongitude();
	            		distance1=getDistanceFromLatLonInKm(currentLat,currentLon,i.getLatitude(),i.getLongitude());
	            		double d=((double)((int)(distance1*100.0)))/100.0;
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
            	}
            	
            	
            	}//end for loop
        	SimpleAdapter adap=new SimpleAdapter(this,fill,R.layout.list_item,from,to);
    		listview.setAdapter(adap);
    		 
    	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	    public void onItemClick(AdapterView<?> parent,View view,int position,long id){
    	        HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
    	        String nameOfRestaurant=map.get("rowid");
    	        String distance1=map.get("col");
    	        Log.i("Value of restaurant name ",nameOfRestaurant);
    	        System.out.println(nameOfRestaurant);
    	        Intent intent1=new Intent(getApplicationContext(),ShowNearestRestaurantFoodDetail.class);
    	        intent1.putExtra("n", nameOfRestaurant);
    	        intent1.putExtra("dist", distance1);
    	        
    	        startActivity(intent1);
    	        	}
    	        } );
    	    if(fill.isEmpty()){
            	tv.setText("\t\t\t\t\t***Sorry!***\n Restaurants that match your desirable food and price cannot be found. \n\n (သင္လိုအပ္ေသာ အစားအစာ ၊ ေစ်းႏႈန္း ႏွင့္ ကိုက္ညီေသာ စားေသာက္ဆိုင္ကိုရွာမေတြ႕ႏိုင္ပါ။)");
            	//Toast.makeText(getApplicationContext(),"Sorry!No restaurants can exist in this range.\nသည္အ႕ြာအေ၀းအတြင္း မည္သည့္ စားေသာက္ဆိုင္မွ မရွိပါ",Toast.LENGTH_LONG).show();
            	bar.setTitle(Html.fromHtml("<font color='#ffffff'>No Restaurant</font>"));
            }
        	
        
        	}
        
        
        
        
        
        
        /*if(fill.isEmpty()){
        	tv.setText("\t\t\t\t\t***Sorry!***\n Restaurants that match your desirable food and price cannot be found. \n\n (သင္လိုအပ္ေသာ အစားအစာ ၊ ေစ်းႏႈန္း ႏွင့္ ကိုက္ညီေသာ စားေသာက္ဆိုင္ကိုရွာမေတြ႕ႏိုင္ပါ။)");
        	//Toast.makeText(getApplicationContext(),"Sorry!No restaurants can exist in this range.\nသည္အ႕ြာအေ၀းအတြင္း မည္သည့္ စားေသာက္ဆိုင္မွ မရွိပါ",Toast.LENGTH_LONG).show();
        	bar.setTitle(Html.fromHtml("<font color='#ffffff'>No Restaurant</font>"));
        }*/
        
	}  
	
	private double getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
		
		
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
