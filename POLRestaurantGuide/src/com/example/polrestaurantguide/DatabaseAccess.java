package com.example.polrestaurantguide;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getAllFood(){
   	 List<String> list = new ArrayList<String>();
   	 Cursor cursor = database.rawQuery("SELECT m.name FROM menu_list as m order by m.name", null);
   	 cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list; 
   }
    public List<String> getFood(String category1){
    	 List<String> list = new ArrayList<String>();
    	 Cursor cursor = database.rawQuery("SELECT m.name FROM menu_list as m join category as c where c.name='"+category1+"' and m.cid=c.cid", null);
    	 cursor.moveToFirst();
         while (!cursor.isAfterLast()) {
         	
             list.add(cursor.getString(0));
             cursor.moveToNext();
         }
         cursor.close();
         return list; 
    }
    public List<FoodAndPrice> getFoodAndPrice(){
    	List<FoodAndPrice> list = new ArrayList<FoodAndPrice>();
        Cursor cursor = database.rawQuery("SELECT m.name,a.price,i.name,i.latitude,i.longtitude FROM menu_list as m join all_menu as a on m.mid=a.menu_id join info as i on i.id=a.info_id", null);
        if (cursor.moveToFirst()) {
            do {
                FoodAndPrice f = new FoodAndPrice();
                f.setFood(cursor.getString(0));
                f.setPrice(cursor.getInt(1));
                f.setName(cursor.getString(2));
                f.setLatitude(cursor.getFloat(3));
                f.setLongitude(cursor.getFloat(4));
                list.add(f);
            } while (cursor.moveToNext());
        }
        return list;  
    }
    public List<FoodAndPrice> getFoodAndPrice(String name1){
    	List<FoodAndPrice> list = new ArrayList<FoodAndPrice>();
        Cursor cursor = database.rawQuery("SELECT m.name,a.price FROM menu_list as m join all_menu as a on m.mid=a.menu_id join info as i on i.id=a.info_id where i.name='"+name1+"'", null);
        if (cursor.moveToFirst()) {
            do {
                FoodAndPrice f = new FoodAndPrice();
                f.setFood(cursor.getString(0));
                f.setPrice(cursor.getInt(1));
                list.add(f);
            } while (cursor.moveToNext());
        }
        return list;  
    }
    public List<FoodAndPrice> getLatAndLon(String name1){
    	List<FoodAndPrice> list = new ArrayList<FoodAndPrice>();
    	 Cursor cursor = database.rawQuery("SELECT * FROM info where name='"+name1+"'", null);
    	 if (cursor.moveToFirst()) {
             do {
                 FoodAndPrice f = new FoodAndPrice();
                 f.setLatitude(cursor.getFloat(2));
                 f.setLongitude(cursor.getFloat(3));
                 list.add(f);
             } while (cursor.moveToNext());
         }
         return list;
    }
    
    public List<RestaurantInfo> getRestaurantInformation() {
        List<RestaurantInfo> list = new ArrayList<RestaurantInfo>();
        Cursor cursor = database.rawQuery("SELECT * FROM info", null);
        if (cursor.moveToFirst()) {
            do {
                RestaurantInfo r = new RestaurantInfo();
                r.setName(cursor.getString(1));
                r.setLatitude(cursor.getFloat(2));
                r.setLongitude(cursor.getFloat(3));
                r.setAddress(cursor.getString(4));
                r.setTime(cursor.getString(5));
                r.setPhoto(cursor.getString(6));
                list.add(r);
            } while (cursor.moveToNext());
        }
        return list; 
    }
    public List<String> getRestaurantInfo() {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT * FROM info order by name", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	
            list.add(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return list; 
    }
    public List<Float> getLatLong(){
    	List<Float> list=new ArrayList<Float>();
    	Cursor cursor = database.rawQuery("SELECT * FROM info", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	
            list.add(cursor.getFloat(2));
            list.add(cursor.getFloat(3));
            cursor.moveToNext();
        }
        cursor.close();
    	return list;
    }
    public List<String> getRestaurantInfo1(String name1) {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT * FROM info where name='"+name1+"'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	
            
            list.add(cursor.getString(4));
            list.add(cursor.getString(5));
            list.add(cursor.getString(6));
            
            cursor.moveToNext();
        }
        cursor.close();
        return list; 
    }
    
    public List<String> getRestaurantInfoLatLong(float lat,float lon) {
        List<String> list = new ArrayList<String>();
        Cursor cursor = database.rawQuery("SELECT * FROM info where latitude>='"+lat+"' and longtitude>='"+lon+"'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	list.add(cursor.getString(1));
            list.add(cursor.getString(4));
            list.add(cursor.getString(5));
            list.add(cursor.getString(6));
            //list.add((byte[])(cursor.getBlob(6)));
            cursor.moveToNext();
        }
        cursor.close();
        return list; 
    }
    /*public Bitmap getRestaurantImage(String name1){
    	
    	Cursor cursor = database.rawQuery("SELECT * FROM info where name='"+name1+"'", null);
    	if(cursor!=null)
    		cursor.moveToFirst();
    	
    	byte[] photo=cursor.getBlob(6);
    	return BitmapFactory.decodeByteArray(photo, 0, photo.length);
    	
     }*/
    /*public String getImages(String name1){
    	String img="abc";
    	Cursor cursor = database.rawQuery("SELECT * FROM info where name='"+name1+"'", null);
    	if(cursor.moveToFirst()){
    		do
    		{
    			img=cursor.getString(6);
    		}while(cursor.moveToNext());
    	}
    	cursor.close();
    	return img;
    }*/
    
} 