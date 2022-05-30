package com.example.TruckSharingApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.TruckSharingApp.data.DatabaseHelper;
import com.example.TruckSharingApp.data.DatabaseHelperO;
import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.User;

import java.util.List;

public class MyOrders extends AppCompatActivity implements ClickInterfaceRV{
    DatabaseHelperO dbO;
    DatabaseHelper db;
    ImageButton plusButtonO;
    Spinner spinnerO;
    orderAdapter adapter;
    RecyclerView orderList;
    List<Order> orders;

    @Override
    protected void onStart() {
        super.onStart();
        try{
            spinnerO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0){
                        Intent oldIntent = getIntent();
                        Intent intent = new Intent(MyOrders.this,HomeScreen.class);
                        intent.putExtra("user_id", oldIntent.getIntExtra("user_id",0));
                        startActivity(intent);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
        }
        catch (Exception e){
            Log.d("reached",e.getMessage());
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        try{
            dbO = new DatabaseHelperO(this);
            db = new DatabaseHelper(this);
            spinnerO = (Spinner) findViewById(R.id.spinnerO);
            plusButtonO = findViewById(R.id.plusButtonO);

            Intent oldIntent = getIntent();
            Integer user_id = oldIntent.getIntExtra("user_id",0);
            orders = dbO.fetchAllOrders(user_id);
            User user = db.fetchUserObject(user_id);

            if(orders != null){
                orderList = findViewById(R.id.orderList);
                adapter = new orderAdapter(orders,user, MyOrders.this,this);
                orderList.setAdapter(adapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyOrders.this, LinearLayoutManager.VERTICAL, false);
                orderList.setLayoutManager(layoutManager);
            }
            else{

            }

            ArrayAdapter<String> myAdapter = new
                    ArrayAdapter<String> (MyOrders.this,
                    android.R.layout.simple_list_item_1,
                    getResources().getStringArray(R.array.options));
            myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerO.setAdapter(myAdapter);
            spinnerO.setSelection(0, false);
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }



        try{
            plusButtonO.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent oldIntent = getIntent();
                    Intent intent = new Intent(MyOrders.this,NewOrder.class);
                    intent.putExtra("user_id", oldIntent.getIntExtra("user_id",0));
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(MyOrders.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MyOrders.this,OrderDetails.class);
        intent.putExtra("order_id",orders.get(position).getOrder_id());
        intent.putExtra("user_id",orders.get(position).getUser_id());
        startActivity(intent);
    }
}