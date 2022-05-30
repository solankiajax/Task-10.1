package com.example.TruckSharingApp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.Trucker;
import com.example.TruckSharingApp.model.User;
import com.example.TruckSharingApp.util.Util;
import com.example.TruckSharingApp.util.UtilO;
import com.example.TruckSharingApp.util.UtilT;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperT extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public DatabaseHelperT(@Nullable Context context) {
        super(context, UtilT.DATABASE_NAME, null, UtilT.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            String CREATE_USER_TABLE = "CREATE TABLE " + UtilT.TABLE_NAME + "(" + UtilT.TRUCKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + UtilT.NAME + " TEXT, " +
                    UtilT.LOCATION + " TEXT , " + UtilT.IMAGE + " Blob)";
            sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        }
        catch (Exception e){
            Log.d("reached",e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS '" + UtilT.TABLE_NAME + "'";
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        onCreate(sqLiteDatabase);

    }

    public long insertTruck(Trucker truck)
    {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UtilT.NAME,truck.getName());
            contentValues.put(UtilT.LOCATION,truck.getLocation());
            contentValues.put(UtilT.IMAGE,truck.getImg());
            long newRowId = db.insert(UtilT.TABLE_NAME, null, contentValues);
            db.close();
            return newRowId;}catch (Exception e){Log.d("reached",e.getMessage()); return 0;}

    }

    public boolean fetchTruck(int trucker_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM " + UtilT.TABLE_NAME + " where " + UtilT.TRUCKER_ID + " = '" + trucker_id + "'";
        Cursor cursor = db.rawQuery(query,null);
        int numberOfRows = cursor.getCount();
        db.close();

        if (numberOfRows > 0)
            return  true;
        else
            return false;
    }

    public Trucker fetchTruckObject(int trucker_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Trucker truck = new Trucker();
        String selectAll = " SELECT * FROM " + UtilT.TABLE_NAME + " where " + UtilT.TRUCKER_ID + " = '" + trucker_id + "'";
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                truck.setTrucker_id(cursor.getInt(0));
                truck.setName(cursor.getString(1));
                truck.setLocation(cursor.getString(2));
                truck.setImg(cursor.getBlob(3));
            } while (cursor.moveToNext());

        }
        else{
            return new Trucker();
        }
        return truck;
    }

    public List<Trucker> fetchAllTruck(){
        try{        List<Trucker> truckList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selectAll = " SELECT * FROM " + UtilT.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectAll, null);


            if (cursor.moveToFirst()) {
                do {
                    Trucker truck = new Trucker();
                    truck.setTrucker_id(cursor.getInt(0));
                    truck.setName(cursor.getString(1));
                    truck.setLocation(cursor.getString(2));
                    truck.setImg(cursor.getBlob(3));
                    truckList.add(truck);

                } while (cursor.moveToNext());

            }

            return truckList;}catch (Exception e){
            Log.d("reached",e.getMessage()); List<Trucker>  truckList = new ArrayList<>(); return truckList;
        }
    }

    /*public long populate(Bitmap bitmap){
        try{

            long x = insertTruck(new Trucker(img,"Alex","north melbourne"));
            Log.d("reached",Long.toString(x));
            long x1 = insertTruck(new Trucker(img,"Alexander","south melbourne"));
            Log.d("reached",Long.toString(x1));
            long x2 = insertTruck(new Trucker(img,"Jessi","NSW"));
            Log.d("reached",Long.toString(x2));
            long x3 = insertTruck(new Trucker(img,"Gorge","western Australia"));
            Log.d("reached",Long.toString(x3));
            long x4 = insertTruck(new Trucker(img,"Ramesh","India"));
            Log.d("reached",Long.toString(x4));
            return x+x1+x2+x3+x4;
        }
        catch (Exception e){
            Log.d("reached",e.getMessage());
            return 0;
        }
    }*/

}
