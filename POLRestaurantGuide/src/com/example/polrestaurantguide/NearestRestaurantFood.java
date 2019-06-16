package com.example.polrestaurantguide;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

@SuppressLint("NewApi") public class NearestRestaurantFood extends Activity implements View.OnClickListener {
	Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8;
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearestrestaurantwithfood);
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        btn1=(Button)findViewById(R.id.button1);
        btn2=(Button)findViewById(R.id.button2);
        btn3=(Button)findViewById(R.id.button3);
        btn4=(Button)findViewById(R.id.button4);
        btn5=(Button)findViewById(R.id.button5);
        btn6=(Button)findViewById(R.id.button6);
        btn7=(Button)findViewById(R.id.button7);
        btn8=(Button)findViewById(R.id.button8);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
	}
	 public void onClick(View view){
		 if(view==btn1){
			
	    	Intent intent=new Intent(this,ShowRestaurantFood.class);
	    	intent.putExtra("food", btn1.getText().toString());
			startActivity(intent);
		 }
		 else if(view==btn2){
			 	
			 	
		    	Intent intent=new Intent(this,ShowRestaurantFood.class);
		    	intent.putExtra("food", btn2.getText().toString());
				startActivity(intent);
			 }
		 else if(view==btn3){
			 	
			 	Intent intent=new Intent(this,ShowRestaurantFood.class);
		    	intent.putExtra("food", btn3.getText().toString());
				startActivity(intent);
			 }
		 else if(view==btn4){
			 	
		    	Intent intent=new Intent(this,ShowRestaurantFood.class);
		    	intent.putExtra("food", btn4.getText().toString());
				startActivity(intent);
			 }
		 else if(view==btn5){
			 	
		    	Intent intent=new Intent(this,ShowRestaurantFood.class);
		    	intent.putExtra("food", btn5.getText().toString());
				startActivity(intent);
			 }
		 else if(view==btn6){
			 	
		    	Intent intent=new Intent(this,ShowRestaurantFood.class);
		    	intent.putExtra("food", btn6.getText().toString());
				startActivity(intent);
			 }
		 else if(view==btn7){
			 	
		    	Intent intent=new Intent(this,ShowRestaurantFood.class);
		    	intent.putExtra("food", btn7.getText().toString());
				startActivity(intent);
			 }
		 else if(view==btn8){
			 	
		    	Intent intent=new Intent(this,ShowRestaurantFood.class);
		    	intent.putExtra("food", btn8.getText().toString());
				startActivity(intent);
			 }
	    }
}
