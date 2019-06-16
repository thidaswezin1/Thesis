package com.example.polrestaurantguide;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class ShowingNearestDetail extends Activity implements OnClickListener {
	ImageView img;
	TextView t1,t2,t3,t4;
	Button btnMap,btnMenu;
	double endlat,endlon;
	GPSTracker gps;
	double userLat,userLon;
	//Double startLat,startLon,endLat,endLon;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shownearestdetail);
		img=(ImageView)findViewById(R.id.imageView1);
		t1=(TextView)findViewById(R.id.textView1);
		t2=(TextView)findViewById(R.id.textView2);
		t3=(TextView)findViewById(R.id.textView3);
		t4=(TextView)findViewById(R.id.textView4);
		btnMap=(Button)findViewById(R.id.button1);
		btnMenu=(Button)findViewById(R.id.button2);
		btnMap.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
		android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
		String index=getIntent().getStringExtra("n");
		String dis=getIntent().getStringExtra("dist");
		 //startLat=getIntent().getDoubleExtra("startLat",0.0);
		 //startLon=getIntent().getDoubleExtra("startLon",0.0);
		 //endLat=(double)getIntent().getFloatExtra("endLat",0);
		 //endLon=(double)getIntent().getFloatExtra("endLon",0);
		//System.out.println(startLat);
		//System.out.println(startLon);
		//System.out.println("Restaurant " +endLat);
		//System.out.println(endLon);
		
		//double dis1=(dis*10)/10.0;
		//DecimalFormat df=new DecimalFormat("#.##");
		//double dis1=Double.parseDouble(df.format(dis));
		
		DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        Log.i("Value of Carry ",index);
        databaseAccess.open();
        List<String> inform = databaseAccess.getRestaurantInfo1(index);
        List<FoodAndPrice> inform1=databaseAccess.getLatAndLon(index);
        String photo=inform.get(2);
       // Bitmap photo=databaseAccess.getRestaurantImage(index);
        databaseAccess.close();
       Log.i("Image",photo);
        for (String item:inform){
        	Log.i("Value of element ",item);
        }
        t1.setText(index);
        t2.setText(inform.get(0));
        t3.setText(inform.get(1));
        t4.setText(dis);
       
        try{
        	InputStream input=getAssets().open(photo);
        	Drawable d=Drawable.createFromStream(input, null);
        	img.setImageDrawable(d);
        	
        }
        catch(IOException ex){
        	Log.e("I/0 error failed when open",photo);
        }
        for (FoodAndPrice f:inform1){
        	endlat=f.getLatitude();
        	endlon=f.getLongitude();
        System.out.println("Restaurant "+endlat);
        System.out.println(endlon);
        }
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
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0==btnMap){
			gps = new GPSTracker(ShowingNearestDetail.this);
			if(haveNetworkConnection()==true){
				if(gps.canGetLocation()) {

                userLat = gps.getLatitude();
               userLon = gps.getLongitude();
               //Toast.makeText(getApplicationContext(), "Your Location is - \nLatitude: " + lati1 + "\nLongitude: " + longi1, Toast.LENGTH_LONG).show();
               if(userLat==0.0 || userLon==0.0){
               	new AlertDialog.Builder(this)
   	 			.setMessage("Your location is not received till now.\nPlease wait and press the button again.")
   	 			.setPositiveButton("OK",new DialogInterface.OnClickListener() {
   	 				
   	 				public void onClick(DialogInterface arg0, int arg1) {
   	 						
   	 				}
   	 		
   	 			}).show();
               }
               else{
            	 Intent intent=new Intent(this,DirectionRoute.class);
           		intent.putExtra("slat",userLat);
           		intent.putExtra("slon",userLon);
           		intent.putExtra("elat",endlat);
           		intent.putExtra("elon",endlon);
           		intent.putExtra("name",t1.getText().toString());
           		startActivity(intent);
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
		else if(arg0==btnMenu){
			 Intent intent=new Intent(this,ShowRestaurantMenu.class);
			 intent.putExtra("resname", t1.getText().toString());
			startActivity(intent);
		 }
	}
}
