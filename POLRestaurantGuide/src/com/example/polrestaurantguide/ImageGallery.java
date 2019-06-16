package com.example.polrestaurantguide;
import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi") @SuppressWarnings("deprecation")
public class ImageGallery extends ActionBarActivity {
	TextView tvname,tvinfo;
	ImageView imageView;
	String[] info={"-This pagoda is one of the most famous pagodas in Myanmar.",
			"-Peik Chin Myaung Cave (also known as Maha Nandamu Cave) is located on the Lashio road near Wetwun village, 12 miles east of Pyin Oo Lwin town. It is a limestone cave estimated between 230 million and 310 million years old.",
			"-It is a very pleasant picnic spot in Pyin Oo Lwin where many Myanmar families come for a picnic. Pwe Kauk or B.E waterfalls is also known as Hampshire Falls in British times. It is located on the way to Lashio and not too far from Pyin Oo Lwin town.",
			"-Built by Yunnan immigrants, this Chinese temple is one of the places that are worth visiting in Maymyo (Pyinoolwin). Built in a typical Chinese architecture and arts, this temple is also home to an orphanage and nursing home. Shoe-friendly ground includes a Chinese style six-storey tower.",
			"-It is located in Pyin Sar Village, Mandalay-Lashio Road, Pyin Oo Lwin.","-It consists of scale models of well known landmarks from each State in Myanmar, including its most famous pagodas, spread over 50 acres of parkland. There is a mini theme park and a huge garden.",
			"-It is the great market in Pyin Oo Lwin. It is Located in Mandalay-Lashio Road",
			"-This Bridge is the highest bridge in Myanmar, about 150 feet above the ground.",
			"-It is located in One Mile North of Pywe Gauk WaterFall, Pyin Oo Lwin.",
			"-The National Kandawgyi Gardens (formerly National Botanical Gardens) is located in the Alpine town about 1.5 km south of Pyin Oo Lwin. It is a popular picnic spot for the locals and one of the main attractions for tourist.",
			"-It is the great grape farm in Pyin Oo Lwin. It is one popular place for visitors.",
			"-The journey to Dee Doke from Pyin Oo Lwin is around 60km. The journey to Dee Doke from Mandalay is around 50km.",
			"-It is located in Ani Sakhan which is the half way point between Mandalay and Pyin Oo Lwin. It is also known as Anisakan Falls. The height of the waterfall is nearly 122m and the depth is about 91m.",
			"-It is located in 5 miles away from the national park of Pyin Oo Lwin.",
			"-The coffee farm is located near Pyin Oo Lwin, about 10 mins drive from downtown.",
			"-COZY WORLD recreational park was opened near Anisakan Airport in PyinOoLwin on 24 March,2018. The park includes a waterslide, rope bridges, fields of strawberries, dragon fruits, grapes and other flowers.",
			};
	String[] name={"MaharAntHtooKanThar Pagoda","Peik Chin Myaung Cave","BE Waterfall","Chinese Temple","University of Technology(Yatanarpon Cyber City)","National Landmarks","Ruby Mart","Goak Hteik(ဂုတ္ထိပ္) Bridge","Htoo Orange Farm","Kandawgyi Garden","Shwe Pyin Oo Lwin Farm","Dee Doak(ဒီးဒုတ္)Blue Waterfall","DatTawGyink(ဓာတ္ေတာ္ခ်ိဳင့္ )Waterfall","YayPyanTaung(ေရပ်ံေတာင္) Waterfall","The Croft Coffee&Organic Farm","Cozy World"};
	Integer[] imageIDs = {
	R.drawable.p1,
	R.drawable.p2,
	R.drawable.p3,
	R.drawable.p4,
	R.drawable.p5,
	R.drawable.p6,
	R.drawable.p7,
	R.drawable.p8,
	R.drawable.p9,
	R.drawable.p10,
	R.drawable.p11,
	R.drawable.p12,
	R.drawable.p13,
	R.drawable.p14,
	R.drawable.p15,
	R.drawable.p16
	};
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery1);
		tvname=(TextView)findViewById(R.id.textView1);
		tvinfo=(TextView)findViewById(R.id.textView2);
		android.app.ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1565C0")));
        bar.setDisplayShowHomeEnabled(false);
		Gallery gallery = (Gallery) findViewById(R.id.gallery1);
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v, int position,long id)
		{
					tvname.setText(name[position]);
					tvinfo.setText(info[position]);
					// display the images selected
					imageView = (ImageView) findViewById(R.id.image1);
					imageView.setImageResource(imageIDs[position]);
			}
		});
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.myanmar).setVisible(false);
        menu.findItem(R.id.english).setVisible(false);
        return true;
    }
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.save) {
	        	BitmapDrawable drawable=(BitmapDrawable)imageView.getDrawable();
				Bitmap bitmap=drawable.getBitmap();
				String root=Environment.getExternalStorageDirectory().toString();
				File myDir=new File(root+"/POLRestaurantGuide");
				myDir.mkdirs();
				//Random generator=new Random();
				//int n=10000;
				//n=generator.nextInt(n);
				String fname=tvname.getText().toString()+".jpg";
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
	    		
	    		return true;
	        }
	        return super.onOptionsItemSelected(item);
	 }

	public class ImageAdapter extends BaseAdapter {
		private Context context;
		private int itemBackground;
		public ImageAdapter(Context c)
		{
			context = c;
			// sets a grey background; wraps around the images
			TypedArray a =obtainStyledAttributes(R.styleable.MyGallery);
			itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
			a.recycle();
		}
		// returns the number of images
		public int getCount() {
			return imageIDs.length;
		}
		// returns the ID of an item
		public Object getItem(int position) {
			return position;
		}
		// returns the ID of an item
		public long getItemId(int position) {
			return position;
		}
		// returns an ImageView view
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView = new ImageView(context);
			imageView.setImageResource(imageIDs[position]);
			imageView.setLayoutParams(new Gallery.LayoutParams(190, 190));
			imageView.setBackgroundResource(itemBackground);
			return imageView;
		}
	}
}
