package com.example.polrestaurantguide;



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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class NearestRestaurant extends Activity implements View.OnClickListener {
	Button search;
	EditText range;
	
	
	double lati,longi,lati1,longi1;
	GPSTracker gps;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.nearestrestaurant);
       
        search=(Button)findViewById(R.id.button1);
        range=(EditText)findViewById(R.id.editText1);
       
        search.setOnClickListener(this);
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
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
	public void onClick(View view){
    	
    	 
    	 if(view==search){	 
    		 gps = new GPSTracker(NearestRestaurant.this);
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
                
                /*if(range.getText().toString().equals("")){
    	 			new AlertDialog.Builder(this)
    	 			.setMessage("Impossible for empty distance.\nPlease submit completely.")
    	 			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
    	 				
    	 				public void onClick(DialogInterface arg0, int arg1) {
    	 						
    	 				}
    	 		
    	 			}).show();
                }*/
                if(range.getText().toString().equals("0") || range.getText().toString().equals("00")||range.getText().toString().equals("000")||range.getText().toString().equals("0000")||range.getText().toString().equals("00000")||range.getText().toString().equals("000000") || range.getText().toString().equals("0.0") || range.getText().toString().equals("0.00") || range.getText().toString().equals("0.000") || range.getText().toString().equals("0.0000") ){
            		new AlertDialog.Builder(this)
            		.setMessage("Zero distance shouldn't be possible.\n Please submit again.")
            		.setPositiveButton("OK",new DialogInterface.OnClickListener() {
 				
            			public void onClick(DialogInterface arg0, int arg1) {
 					
            			}
 		
            		}).show();
            	}
                else{
                	Intent intent=new Intent(this,NearestRestaurantInfo.class);
                	intent.putExtra("lati", String.valueOf(lati1));
                	intent.putExtra("longi", String.valueOf(longi1));
                	System.out.println(lati1);
                	System.out.println(longi1);
                	intent.putExtra("range", range.getText().toString());
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
}
