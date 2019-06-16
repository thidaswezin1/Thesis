package com.example.polrestaurantguide;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

@SuppressLint("NewApi") public class Find extends Activity implements View.OnClickListener{
	Button btnGallery,btnAll,btnNear,btnNearFood,btnFind;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find);
        btnGallery=(Button)findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(this);
        btnAll=(Button)findViewById(R.id.Button02);
        btnAll.setOnClickListener(this);
        btnNear=(Button)findViewById(R.id.button1);
        btnNear.setOnClickListener(this);
        btnNearFood=(Button)findViewById(R.id.Button01);
        btnNearFood.setOnClickListener(this);
        btnFind=(Button)findViewById(R.id.button2);
        btnFind.setOnClickListener(this);
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
    }
    public void onClick(View view){
    	if(view==btnGallery){
    		btnGallery.setTextColor(Color.parseColor("#ffffff"));
    		Intent intent=new Intent(this,ImageGallery.class);
    		startActivity(intent);
    	}
    	else if(view==btnAll){
    		btnAll.setTextColor(Color.parseColor("#ffffff"));
    		Intent intent=new Intent(this,ShowingRestaurant.class);
    		startActivity(intent);
    	}
    	else if(view==btnNear){
    		btnNear.setTextColor(Color.parseColor("#ffffff"));
    		Intent intent=new Intent(this,NearestRestaurant.class);
    		startActivity(intent);
    	}
    	else if(view==btnNearFood){
    		btnNearFood.setTextColor(Color.parseColor("#ffffff"));
    		Intent intent=new Intent(this,NearestRestaurantFood.class);
    		startActivity(intent);
    	}
    	else if(view==btnFind){
    		btnFind.setTextColor(Color.parseColor("#1565C0"));
    		Intent intent=new Intent(this,FindRestaurant.class);
    		startActivity(intent);
    	}
    }
}
