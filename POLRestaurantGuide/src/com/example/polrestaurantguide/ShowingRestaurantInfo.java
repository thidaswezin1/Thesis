package com.example.polrestaurantguide;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") public class ShowingRestaurantInfo extends Activity implements View.OnClickListener {
	ImageView img;
	TextView t1,t2,t3;
	Button btnmenu,btnsave,btnlocation;
	String photo;
	double endlat,endlon;
	
	BitmapDrawable drawable;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showrestaurantinfo);
		android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
		img=(ImageView)findViewById(R.id.imageView1);
		t1=(TextView)findViewById(R.id.textView4);
		t2=(TextView)findViewById(R.id.textView5);
		t3=(TextView)findViewById(R.id.textView6);
		btnmenu=(Button)findViewById(R.id.button1);
		btnmenu.setOnClickListener(this);
		btnsave=(Button)findViewById(R.id.button2);
		btnsave.setOnClickListener(this);
		btnlocation=(Button)findViewById(R.id.button3);
		btnlocation.setOnClickListener(this);
		String index=getIntent().getStringExtra("n");
		DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        Log.i("Value of Carry ",index);
        databaseAccess.open();
        List<String> inform = databaseAccess.getRestaurantInfo1(index);
        List<FoodAndPrice> inform1=databaseAccess.getLatAndLon(index);
         photo=inform.get(2);
       // Bitmap photo=databaseAccess.getRestaurantImage(index);
        databaseAccess.close();
       Log.i("Image",photo);
        for (String item:inform){
        	Log.i("Value of element ",item);
        }
        t1.setText(index);
        t2.setText(inform.get(0));
        t3.setText(inform.get(1));
       
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
       
        }
	}
        private boolean haveNetworkConnection(){
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
		if(v==btnmenu){
			Intent intent=new Intent(this,ShowRestaurantMenu.class);
			intent.putExtra("resname", t1.getText().toString());
			startActivity(intent);
		}
		else if(v==btnlocation){
			if(haveNetworkConnection()==true){
				Intent intent=new Intent(this,ConnectMap.class);
				intent.putExtra("resname", t1.getText().toString());
				intent.putExtra("elat", endlat);
				intent.putExtra("elon", endlon);
				startActivity(intent);
			}
			else{
				 new AlertDialog.Builder(this)
	 			.setMessage("Offline!!!\nDo you want to go to settings menu?")
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
		else if(v==btnsave){
			//Toast.makeText(getApplicationContext(),"Image is saved successfully!",Toast.LENGTH_LONG).show();
			drawable=(BitmapDrawable)img.getDrawable();
			Bitmap bitmap=drawable.getBitmap();
			String root=Environment.getExternalStorageDirectory().toString();
			File myDir=new File(root+"/POLRestaurantGuide");
			myDir.mkdirs();
			//Random generator=new Random();
			//int n=10000;
			//n=generator.nextInt(n);
			String fname=t1.getText().toString()+".jpg";
			File file=new File(myDir,fname);
			if(file.exists()){
				//file.delete();
				Toast.makeText(getApplicationContext(),"Image is already saved.",Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(getApplicationContext(),"Image is saved successfully!",Toast.LENGTH_LONG).show();
			
			try{
				FileOutputStream out=new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.JPEG,90,out);
				out.flush();
				out.close();
				
			}catch (Exception e) {
				
				e.printStackTrace();
			}
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,Uri.fromFile(file)));
			
			}
		}
	}

	
	
}
