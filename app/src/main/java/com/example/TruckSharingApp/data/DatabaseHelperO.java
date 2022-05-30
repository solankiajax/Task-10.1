package com.example.TruckSharingApp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.User;
import com.example.TruckSharingApp.util.Util;
import com.example.TruckSharingApp.util.UtilO;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperO extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public DatabaseHelperO(@Nullable Context context) {
        super(context, UtilO.DATABASE_NAME, null, UtilO.DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    try{
        String CREATE_USER_TABLE = "CREATE TABLE " + UtilO.TABLE_NAME + "(" + UtilO.ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + UtilO.USER_ID + " INTEGER , "
                + UtilO.RECEIVER_NAME + " TEXT , " + UtilO.PICKUP_DATE + " TEXT , " + UtilO.PICKUP_TIME + " TEXT , " + UtilO.PICKUP_LOCATION + " TEXT , " + UtilO.GOOD_TYPE + " TEXT , " +
                UtilO.WEIGHT + " TEXT , " + UtilO.LENGTH + " TEXT , " + UtilO.HEIGHT + " TEXT , " + UtilO.WIDTH + " TEXT , " + UtilO.VEHICLE_TYPE + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }
        catch (Exception e){
            Log.d("reached",e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS '" + UtilO.TABLE_NAME + "'";
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        onCreate(sqLiteDatabase);

    }

    public long insertOrder(Order order)
    {
        try{        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(UtilO.USER_ID, order.getUser_id());
            contentValues.put(UtilO.RECEIVER_NAME, order.getReceiver_name() );
            contentValues.put(UtilO.PICKUP_DATE, order.getPickup_date());
            contentValues.put(UtilO.PICKUP_TIME, order.getPickup_time());
            contentValues.put(UtilO.PICKUP_LOCATION, order.getPickup_location());
            contentValues.put(UtilO.GOOD_TYPE, order.getGood_type());
            contentValues.put(UtilO.WEIGHT, order.getWeight());
            contentValues.put(UtilO.LENGTH, order.getLength());
            contentValues.put(UtilO.HEIGHT, order.getHeight());
            contentValues.put(UtilO.WIDTH, order.getWidth());
            contentValues.put(UtilO.VEHICLE_TYPE, order.getVehicle_type());
            long newRowId = db.insert(UtilO.TABLE_NAME, null, contentValues);
            db.close();
            return newRowId;}catch (Exception e){Log.d("reached",e.getMessage()); return 0;}

    }

    public boolean fetchOrder(int order_id, int user_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UtilO.TABLE_NAME, new String[]{UtilO.ORDER_ID}, UtilO.ORDER_ID + "=? and " + UtilO.USER_ID + "=?",
                new String[] {Integer.toString(order_id), Integer.toString(user_id)}, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();

        if (numberOfRows > 0)
            return  true;
        else
            return false;
    }

    public Order fetchOrderObject(Integer order_id, Integer user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        Order order = new Order();
        String selectAll = " SELECT * FROM " + UtilO.TABLE_NAME + " where " + UtilO.ORDER_ID + " = '" + order_id +
                "' and " + UtilO.USER_ID + " = '" + user_id + "'";
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                order.setOrder_id(cursor.getInt(0));
                order.setUser_id(cursor.getInt(1));
                order.setReceiver_name(cursor.getString(2));
                order.setPickup_date(cursor.getString(3));
                order.setPickup_time(cursor.getString(4));
                order.setPickup_location(cursor.getString(5));
                order.setGood_type(cursor.getString(6));
                order.setWeight(cursor.getString(7));
                order.setLength(cursor.getString(8));
                order.setHeight(cursor.getString(9));
                order.setWidth(cursor.getString(10));
                order.setVehicle_type(cursor.getString(11));

            } while (cursor.moveToNext());

        }
        else{
            return new Order();
        }
        return order;
    }

    public List<Order> fetchAllOrders(Integer user_id){
        try{        List<Order> orderList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selectAll = " SELECT * FROM " + UtilO.TABLE_NAME + " where " + UtilO.USER_ID  + " = '" + user_id + "'";
            Cursor cursor = db.rawQuery(selectAll, null);


            if (cursor.moveToFirst()) {
                do {
                    Order order = new Order();
                    order.setOrder_id(cursor.getInt(0));
                    order.setUser_id(cursor.getInt(1));
                    order.setReceiver_name(cursor.getString(2));
                    order.setPickup_date(cursor.getString(3));
                    order.setPickup_time(cursor.getString(4));
                    order.setPickup_location(cursor.getString(5));
                    order.setGood_type(cursor.getString(6));
                    order.setWeight(cursor.getString(7));
                    order.setLength(cursor.getString(8));
                    order.setHeight(cursor.getString(9));
                    order.setWidth(cursor.getString(10));
                    order.setVehicle_type(cursor.getString(11));
                    orderList.add(order);

                } while (cursor.moveToNext());

            }

            return orderList;}catch (Exception e){Log.d("reached",e.getMessage()); List<Order> orderList = new ArrayList<>(); return orderList;}

    }

}
