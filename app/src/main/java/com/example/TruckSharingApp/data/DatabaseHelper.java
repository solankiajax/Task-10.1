package com.example.TruckSharingApp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.TruckSharingApp.MainActivity;
import com.example.TruckSharingApp.model.User;
import com.example.TruckSharingApp.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "tag";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
    
    

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

         String CREATE_USER_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "(" + Util.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + Util.USERNAME + " TEXT , " + Util.PASSWORD + " TEXT , " + Util.FULL_NAME + " TEXT , " + Util.PHONE_NO + " TEXT , " + Util.IMAGE + " blob)";
         sqLiteDatabase.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String DROP_USER_TABLE = "DROP TABLE IF EXISTS '" + Util.TABLE_NAME + "'";
        sqLiteDatabase.execSQL(DROP_USER_TABLE);

        onCreate(sqLiteDatabase);

    }

    public long insertUser (User user)
    {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Util.USERNAME, user.getUsername());
            contentValues.put(Util.PASSWORD, user.getPassword());
            contentValues.put(Util.FULL_NAME,user.getFull_name());
            contentValues.put(Util.PHONE_NO,user.getPhone());
            contentValues.put(Util.IMAGE,user.getImg());
            long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
            db.close();
            return newRowId;
        }
        catch (Exception e){
            throw e;
        }

    }

    public boolean fetchUser(String username, String password)
    {
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.USER_ID}, Util.USERNAME + "=? and " + Util.PASSWORD + "=?",
                    new String[] {username, password}, null, null, null);
            int numberOfRows = cursor.getCount();
            db.close();

            if (numberOfRows > 0)
                return  true;
            else
                return false;
        }
        catch (Exception e){
            Log.d("reached",e.getMessage());
            return false;
        }

    }

    public User fetchUserObject(String username,String password){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            User user = new User();
            String selectAll = " SELECT * FROM " + Util.TABLE_NAME + " where " + Util.USERNAME + " = '" + username +
                    "' and " + Util.PASSWORD + " = '" + password + "'";
            Cursor cursor = db.rawQuery(selectAll, null);

            if (cursor.moveToFirst()) {
                do {
                    user.setUser_id(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    user.setFull_name(cursor.getString(3));
                    user.setPhone(cursor.getString(4));
                    user.setImg(cursor.getBlob(5));

                } while (cursor.moveToNext());
            }
            else{
                return new User();
            }
            return user;
        }
        catch (Exception e){

            Log.d("reached",e.getMessage());
            return new User();
        }
    }

    public User fetchUserObject(Integer user_id){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            User user = new User();
            String selectAll = " SELECT * FROM " + Util.TABLE_NAME + " where " + Util.USER_ID + " = '" + user_id + "'";
            Cursor cursor = db.rawQuery(selectAll, null);

            if (cursor.moveToFirst()) {
                do {
                    user.setUser_id(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    user.setFull_name(cursor.getString(3));
                    user.setPhone(cursor.getString(4));
                    user.setImg(cursor.getBlob(5));

                } while (cursor.moveToNext());
            }
            else{
                return new User();
            }
            return user;
        }
        catch (Exception e){

            Log.d("reached",e.getMessage());
            return new User();
        }
    }


    public List<User> fetchAllUsers (){
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = " SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUser_id(cursor.getInt(0));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setFull_name(cursor.getString(3));
                user.setPhone(cursor.getString(4));
                user.setImg(cursor.getBlob(5));
                userList.add(user);

            } while (cursor.moveToNext());

        }

        return userList;
    }

    public int updatePassword(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Util.PASSWORD, user.getPassword());

        return db.update(Util.TABLE_NAME, contentValues, Util.USERNAME + "=?", new String[]{String.valueOf(user.getUsername())});

    }
}
