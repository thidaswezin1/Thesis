package com.example.polrestaurantguide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

@SuppressLint("NewApi") public class About extends ActionBarActivity {
	TextView tv1,tv2,tv3;
	
	@SuppressLint("NewApi") protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        tv1=(TextView)findViewById(R.id.textView2);
        tv2=(TextView)findViewById(R.id.textView4);
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        bar.setDisplayShowHomeEnabled(false);
    }
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.save).setVisible(false);
        return true;
    }
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.myanmar) {
	        	
	    		tv1.setText("ဒီ  App ကေတာ့ ျပင္ဦးလြင္မွာရွိတဲ့ စားေသာက္ဆိုင္ေတြထဲက  အနီးဆံုး စားေသာက္ဆိုင္ကို ရွာေပးမွာပါ။ အသံုးျပဳသူရဲ့ တည္ေနရာ သိႏိုင္ရန္ GPS ဖြင့္ဖို့လိုပါတယ္။  ဒီ app လုပ္ခိုင္းတဲ့အတိုင္း အဆင့္ဆင့္ လုပ္ေဆာင္သြားမည္ဆိုရင္ မိမိအနီးအနားတြင္ရွိေသာဆိုင္မ်ားကို  အလြယ္တကူသိရမွာပါ။ ျပင္ဦးလြင္ရွိဆိုင္မ်ားစြာကိုလည္း All Restaurants ကိုၾကည့္ျခင္းျဖင့္သိရမွာျဖစ္ပါတယ္");
	    		tv2.setText("တစ္စံုတစ္ရာ အကူအညီလိုပါက၊ အႀကံေပးလိုပါက ကၽြႏု္ပ္အား email ပို့ႏိုင္ပါတယ္");
	    		
	    		return true;
	        }
	        if (id == R.id.english) {
	        	
	    		tv1.setText("This app is to find the nearest restaurant in Pyin Oo Lwin. Firstly, you will do GPS on. You cannot find restaurants without internet. You will get detail information of restaurants easily by searching restaurants. You will need to do the instruction of this app progressively. You can view all restaurants that app has.");
	    		tv2.setText("If you have some advice or you need extra help, please send email to me.");
	    		
	    		
	    		return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }

}
