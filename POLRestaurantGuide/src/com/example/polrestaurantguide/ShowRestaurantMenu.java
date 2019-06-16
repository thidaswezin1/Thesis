package com.example.polrestaurantguide;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

@SuppressLint("NewApi") public class ShowRestaurantMenu extends Activity {
	TextView t1;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showrestaurantmenu);
		t1=(TextView)findViewById(R.id.menu);
		android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
		
		String index=getIntent().getStringExtra("resname");
		DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        Log.i("Value of Carry ",index);
        databaseAccess.open();
        List<FoodAndPrice> inform = databaseAccess.getFoodAndPrice(index);
        databaseAccess.close();
        for (FoodAndPrice f:inform){
        	String s=String.format("%s%10d%n%n",f.getFood(),f.getPrice());
        	//String s=String.format("%-5d",f.getPrice());
        	t1.append(s);
        	
        }
	}
}
