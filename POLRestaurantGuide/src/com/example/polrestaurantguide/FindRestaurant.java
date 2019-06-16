package com.example.polrestaurantguide;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("NewApi") public class FindRestaurant extends Activity implements View.OnClickListener{ 
	Button btnSearch;
	ListView listView;
	Spinner spinfood;
	String userFood,food;
	List<FoodAndPrice> info;
	//List<String> resName=new ArrayList<String>();
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findrestaurant);
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        btnSearch=(Button)findViewById(R.id.button1);
        btnSearch.setOnClickListener(this);
        		
        this.listView = (ListView) findViewById(R.id.listView);
        spinfood=(Spinner)findViewById(R.id.spinner1);
        
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> foodname = databaseAccess.getAllFood();
        
        databaseAccess.close();
        
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
	@Override
	public void onClick(View arg0) {
		List<String> resName=new ArrayList<String>();
		 DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
	        databaseAccess.open();
	        info = databaseAccess.getFoodAndPrice();
	        databaseAccess.close();
		for(FoodAndPrice i:info){
        	//System.out.println("Food and price from DB "+i.getFood()+""+i.getPrice());
        	food=i.getFood();
        	
        	if(userFood.equals(food)){
        		resName.add(i.getName());
        	}
        }
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, resName);
        this.listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent,View view,int position,long id){
        		String nameOfRestaurant=(String)listView.getItemAtPosition(position);
        		Log.i("Value of restaurant name ",nameOfRestaurant);
        		
        		Intent intent1=new Intent(getApplicationContext(),ShowingRestaurantInfo.class);
        		intent1.putExtra("n", nameOfRestaurant);
        		startActivity(intent1);
        	}
        } );
       
		
	}
}
