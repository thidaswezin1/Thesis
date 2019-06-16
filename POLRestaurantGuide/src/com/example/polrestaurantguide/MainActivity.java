package com.example.polrestaurantguide;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


@SuppressLint("NewApi") public class MainActivity extends ActionBarActivity implements View.OnClickListener {

   
    Button btnStart;
    @SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart=(Button)findViewById(R.id.start);
        btnStart.setOnClickListener(this);
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        bar.setTitle(" ");
        bar.setDisplayShowHomeEnabled(false);
    }
    public void onClick(View view){
    	Intent intent=new Intent(this,Find.class);
		startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.myanmar).setVisible(false);
        menu.findItem(R.id.english).setVisible(false);
        menu.findItem(R.id.save).setVisible(false);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
        	Intent intent=new Intent(this,About.class);
    		startActivity(intent);
    		return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
