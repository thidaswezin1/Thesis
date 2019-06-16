package com.example.polrestaurantguide;

import java.util.List;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("NewApi") public class ShowRestaurantFood extends Activity implements View.OnClickListener {
	Spinner spinfood;
	EditText price,distance;
	Button btn;
	String userFood;
	GPSTracker gps;
	double lati1,longi1;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showrestaurantfood);
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        spinfood=(Spinner)findViewById(R.id.spinner1);
        price=(EditText)findViewById(R.id.editText1);
        distance=(EditText)findViewById(R.id.editText2);
        btn=(Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        String foodType=getIntent().getStringExtra("food");
        databaseAccess.open();
        List<String> foodname = databaseAccess.getFood(foodType);
        databaseAccess.close();
        for (String item:foodname){
        	Log.i("Food name ",item);
        }
        
        
        ArrayAdapter<String> aa=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,foodname);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinfood.setAdapter(aa);
        spinfood.setOnItemSelectedListener(new OnItemSelectedListener() {
        	public void onItemSelected(AdapterView<?> parent,View v,int position,long id){
        		userFood=(String)spinfood.getItemAtPosition((position));
        	}
        	public void onNothingSelected(AdapterView<?> parent){
        		userFood=(String)spinfood.getItemAtPosition((0));
        	}
		});
	}
	private boolean haveNetworkConnection() {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
	public void onClick(View v){
		 gps = new GPSTracker(ShowRestaurantFood.this);
		 if(haveNetworkConnection()==true){
			 if(gps.canGetLocation()) {

             lati1 = gps.getLatitude();
            longi1 = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is - \nLatitude: " + lati1 + "\nLongitude: " + longi1, Toast.LENGTH_LONG).show();
            if(lati1==0.0 || longi1==0.0){
            	new AlertDialog.Builder(this)
	 			.setMessage("Your location is not received till now.\nPlease wait and press the button again.")
	 			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
	 				
	 				public void onClick(DialogInterface arg0, int arg1) {
	 						
	 				}
	 		
	 			}).show();
            }
            else{
            	if(price.getText().toString().equals("")){
            		new AlertDialog.Builder(this)
            		.setMessage("Empty price shouldn't possible.\n Please submit.")
            		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
 				
            			public void onClick(DialogInterface arg0, int arg1) {
 					
            			}
 		
            		}).show();
            	}
            	else if(distance.getText().toString().equals("0") || distance.getText().toString().equals("00")||distance.getText().toString().equals("000")||distance.getText().toString().equals("0000")||distance.getText().toString().equals("00000")||distance.getText().toString().equals("000000") || distance.getText().toString().equals("0.0") || distance.getText().toString().equals("0.00") || distance.getText().toString().equals("0.000") || distance.getText().toString().equals("0.0000") ){
            		new AlertDialog.Builder(this)
            		.setMessage("Zero distance shouldn't possible.\n Please submit again.")
            		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
 				
            			public void onClick(DialogInterface arg0, int arg1) {
 					
            			}
 		
            		}).show();
            	}
            	else if(Integer.parseInt(price.getText().toString())<300){
            		new AlertDialog.Builder(this)
            		.setMessage("Impossible price.\n The price should be much.")
            		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
 				
            			public void onClick(DialogInterface arg0, int arg1) {
 					
            			}
 		
            		}).show();
            	}
            	else{
            		Intent intent=new Intent(this,ShowRestaurantFoodDetail.class);
            		intent.putExtra("lat", lati1);
                	intent.putExtra("lon", longi1);
            		intent.putExtra("userfood",userFood);
            		intent.putExtra("price", price.getText().toString());
            		intent.putExtra("distance",distance.getText().toString());
            		startActivity(intent);
            	}
            }
		 }
		 else {
             gps.showSettingsAlert();
          }
		}
		 else{
			 new AlertDialog.Builder(this)
 	 			.setMessage("Offline!!!Do you want to go to settings menu?")
 	 			.setPositiveButton("Settings",new DialogInterface.OnClickListener() {
 	 				
 	 				public void onClick(DialogInterface arg0, int arg1) {
 	 					Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);	
 	 					startActivity(intent);
 	 				}
 	 		
 	 			})
 	 			.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
 	 				
 	 				public void onClick(DialogInterface arg0, int arg1) {
 	 					
 	 				}
 	 		
 	 			})
 	 			.show();
		}
	}
	
}
