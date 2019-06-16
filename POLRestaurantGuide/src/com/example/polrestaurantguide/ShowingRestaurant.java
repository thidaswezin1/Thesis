package com.example.polrestaurantguide;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("NewApi") public class ShowingRestaurant extends Activity {
	
	 private ListView listView;
	 SearchView searchView;
	 ArrayAdapter<String> adapter;
	 List<String> name;
	
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showrestaurant);
		android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
		this.listView = (ListView) findViewById(R.id.listView);
		searchView = (SearchView) findViewById(R.id.searchView);  
		
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        
        databaseAccess.open();
        name = databaseAccess.getRestaurantInfo();
        databaseAccess.close();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
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
        
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {  
            @Override  
           /* public boolean onQueryTextSubmit(String query) {  
  
                if(name.contains(query)){  
                    adapter.getFilter().filter(query);  
                }else{  
                    Toast.makeText(getApplicationContext(), "No Match found",Toast.LENGTH_LONG).show();  
                }  
                return false;  
            }  */
  
             
            public boolean onQueryTextChange(String newText) {  
                adapter.getFilter().filter(newText);  
                return false;  
            }

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}  
        });  
        
        }  
 }
	
	
	

	



