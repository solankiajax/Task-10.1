package com.example.TruckSharingApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.TruckSharingApp.data.DatabaseHelperO;
import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.User;

public class NewOrderTwo extends AppCompatActivity {
    EditText otherGoodTypesText,otherVehicleType,width,weight,length,height;
    RadioButton radioButton,radioButton2;
    RadioGroup radioGroup,radioGroup2;
    Button createOrderBtn;
    String good_type,vehicle_type;
    DatabaseHelperO dbO;
    int radioIDG = -1;
    int radioIDV = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_two);
        otherGoodTypesText = findViewById(R.id.otherGoodTypesText);
        otherVehicleType = findViewById(R.id.otherVehicleType);
        width = findViewById(R.id.width);
        weight = findViewById(R.id.weight);
        length = findViewById(R.id.length);
        height = findViewById(R.id.height);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup2 = findViewById(R.id.radioGroup2);
        createOrderBtn = findViewById(R.id.createOrderBtn);
        Intent object = getIntent();
        Order order = new Order();
        dbO= new DatabaseHelperO(this);

        otherGoodTypesText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        otherVehicleType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        width.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        weight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        length.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        height.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });



        otherGoodTypesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherGoodTypesText.setFocusableInTouchMode(true);
                if(radioGroup.getCheckedRadioButtonId()!=-1){
                    radioGroup.clearCheck();
                    good_type="";
                    Log.d("reached",good_type);
                }
            }
        });

        otherVehicleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherVehicleType.setFocusableInTouchMode(true);
                if(radioGroup2.getCheckedRadioButtonId()!=-1){
                    radioGroup2.clearCheck();
                    vehicle_type="";
                    Log.d("reached",vehicle_type);
                }
            }
        });


        createOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int type= 0 ;
                    String weightX = weight.getText().toString();
                    String lengthX = length.getText().toString();
                    String heightX = height.getText().toString();
                    String widthX = weight.getText().toString();

                    if(!otherGoodTypesText.getText().toString().isEmpty()&&!otherGoodTypesText.getText().toString().trim().equals("")){
                        good_type = otherGoodTypesText.getText().toString();
                        type = type + 1;
                    }
                    if(!otherVehicleType.getText().toString().isEmpty()&&!otherVehicleType.getText().toString().trim().equals("")){
                        vehicle_type = otherVehicleType.getText().toString();
                        type = type +1;
                    }
                    if(radioGroup.getCheckedRadioButtonId()==-1&&otherGoodTypesText.getText().toString().isEmpty()&&otherGoodTypesText.getText().toString().trim().equals("")){
                        throw new Exception();
                    }

                    if(radioGroup2.getCheckedRadioButtonId()==-1&&otherVehicleType.getText().toString().isEmpty()&&otherVehicleType.getText().toString().trim().equals("")){
                        throw new Exception();
                    }
                    if(weight.getText().toString().isEmpty()||weight.getText().toString().trim().equals("")||length.getText().toString().isEmpty()||length.getText().toString().trim().equals("")||
                    height.getText().toString().isEmpty()||height.getText().toString().trim().equals("")||weight.getText().toString().isEmpty()||weight.getText().toString().trim().equals("")) {
                        Toast.makeText(NewOrderTwo.this, "please enter all the relevant info on display", Toast.LENGTH_SHORT).show();
                    }
                    if (type>0)
                    {
                        try{
                            Intent oldIntent = getIntent();
                            Integer x = oldIntent.getIntExtra("user_id",0);
                            String name = oldIntent.getStringExtra("name");
                            String time = oldIntent.getStringExtra("time");
                            String date = oldIntent.getStringExtra("date");
                            String location = oldIntent.getStringExtra("location");
                            order.setReceiver_name(name);
                            order.setPickup_time(time);
                            order.setPickup_date(date);
                            order.setPickup_location(location);
                            order.setUser_id(x);
                            order.setWeight(weightX);
                            order.setWidth(widthX);
                            order.setLength(lengthX);
                            order.setHeight(heightX);
                            order.setVehicle_type(vehicle_type);
                            order.setGood_type(good_type);
                            long result = dbO.insertOrder(order);
                            if(result>0){

                                Intent intent = new Intent(NewOrderTwo.this,MyOrders.class);
                                intent.putExtra("user_id", oldIntent.getIntExtra("user_id",0));
                                startActivity(intent);
                                Toast.makeText(NewOrderTwo.this, "inserted successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(NewOrderTwo.this, "couldn't insert", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(NewOrderTwo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(NewOrderTwo.this,"please enter all the relevant info on display",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(NewOrderTwo.this,"please enter all the relevant info on display",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void checkBtn(View v){
        otherGoodTypesText.setFocusableInTouchMode(false);
        try {
            int radio = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radio);
            if(otherGoodTypesText.getText().toString().isEmpty()||otherGoodTypesText.getText().toString().trim().equals("")){
                good_type = radioButton.getText().toString();
                radioIDG=radio;
                Log.d("reached",good_type);
            }
            else if(!otherGoodTypesText.getText().toString().isEmpty()||!otherGoodTypesText.getText().toString().trim().equals("")){
                otherGoodTypesText.setText("");
                good_type = radioButton.getText().toString();
                radioIDG = radio;
                Log.d("reached",good_type);
            }else if (radio == radioIDG){
                radioGroup.clearCheck();
                good_type = "";
                Log.d("reached",good_type);
            }else if(radio != radioIDG && radio != -1){
                radioGroup.clearCheck();
                radioButton.setChecked(true);
                good_type = radioButton.getText().toString();
                Log.d("reached",good_type);
            }
        }catch (Exception e){
            Log.d("reached",e.getMessage());
        }

    }

    public void checkBtn2(View v){
        otherVehicleType.setFocusableInTouchMode(false);
        try {
            int radio = radioGroup2.getCheckedRadioButtonId();
            radioButton2 = findViewById(radio);

            if(otherVehicleType.getText().toString().isEmpty()||otherVehicleType.getText().toString().trim().equals("")){
                vehicle_type = radioButton2.getText().toString();
                radioIDV=radio;
                Log.d("reached",vehicle_type);
            }

            else if(!otherVehicleType.getText().toString().isEmpty()||!otherVehicleType.getText().toString().trim().equals("")){
                otherVehicleType.setText("");
                vehicle_type = radioButton2.getText().toString();
                radioIDV = radio;
                Log.d("reached",vehicle_type);
            }

            else if (radio == radioIDV){
                radioGroup2.clearCheck();
                vehicle_type = "";
                Log.d("reached",vehicle_type);
            }

            else if(radio != radioIDV && radio != -1){
                radioGroup2.clearCheck();
                radioButton2.setChecked(true);
                vehicle_type = radioButton.getText().toString();
                Log.d("reached",vehicle_type);
            }
        }

        catch (Exception e){
            Log.d("reached",e.getMessage());
        }
    }
}