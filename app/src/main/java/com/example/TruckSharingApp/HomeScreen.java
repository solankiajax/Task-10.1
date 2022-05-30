package com.example.TruckSharingApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.TruckSharingApp.data.DatabaseHelper;
import com.example.TruckSharingApp.data.DatabaseHelperT;
import com.example.TruckSharingApp.model.Trucker;
import com.example.TruckSharingApp.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity {

    Spinner spinner;
    String selectedItem;
    ImageButton plusButton;
    RecyclerView truckList;
    List<Trucker> trucks;
    truckAdapter adapter;
    String[] name = {"Alex","Alexander","Jessi","George","Ramesh"};
    String[] location = {"north melbourne","south melbourne","NSW","western Australia","India"};
    String[] id = {"1","2","3","4","5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        spinner = (Spinner) findViewById(R.id.spinner);
        plusButton = findViewById(R.id.plusButton);


        try{
            truckList = findViewById(R.id.truckList);
            trucks = new ArrayList<>();
            for(int i=0;i<5;i++){
                trucks.add(new Trucker(name[i],location[i],id[i]));
            }

            if(trucks.size() > 0){
                adapter = new truckAdapter(trucks,HomeScreen.this);
                truckList.setAdapter(adapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeScreen.this, LinearLayoutManager.VERTICAL, false);
                truckList.setLayoutManager(layoutManager);
            }
            else{

            }

        }
        catch (Exception e){
            Log.d("reached",e.getMessage());
        }


        ArrayAdapter<String> myAdapter = new
        ArrayAdapter<String> (HomeScreen.this,
                android.R.layout.simple_list_item_1,
                    getResources().getStringArray(R.array.options));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        int initialSelectedPosition=spinner.getSelectedItemPosition();
        spinner.setSelection(initialSelectedPosition, false);
        try{
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = parent.getItemAtPosition(position).toString();
                    if(selectedItem.equals("My Orders")){
                        try{
                            Intent oldIntent = getIntent();
                            Intent intent = new Intent(HomeScreen.this,MyOrders.class);
                            intent.putExtra("user_id", oldIntent.getIntExtra("user_id",0));
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(HomeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
        }catch (Exception e){
            Log.d("reached",e.getMessage());
        }

        try{
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent oldIntent = getIntent();
                    Intent intent = new Intent(HomeScreen.this,NewOrder.class);
                    intent.putExtra("user_id", oldIntent.getIntExtra("user_id",0));
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(HomeScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

}