package com.example.TruckSharingApp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.User;

import java.util.Calendar;

public class NewOrder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView receiverName,date,time,location;
    Button nextBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        receiverName = findViewById(R.id.receiverName);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        nextBtn = findViewById(R.id.nextBtn);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((location.getText().toString().isEmpty()||date.getText().toString().isEmpty()||receiverName.getText().toString().isEmpty()||
                        time.getText().toString().isEmpty()||location.getText().toString().trim().equals("")||date.getText().toString().trim().equals("")||time.getText().toString().trim().equals("")
                        ||receiverName.getText().toString().trim().equals(""))){
                    Toast.makeText(NewOrder.this,"Please enter all the relevant information on display",Toast.LENGTH_SHORT).show();
                }
                else{
                    try{

                        String name = (receiverName.getText().toString());
                        String dateX = (date.getText().toString());
                        String timeX = (time.getText().toString());
                        String locationX = (location.getText().toString());
                        Intent intent = new Intent(NewOrder.this,NewOrderTwo.class);
                        Intent oldIntent = getIntent();
                        intent.putExtra("user_id", oldIntent.getIntExtra("user_id",0));
                        intent.putExtra("name",name);
                        intent.putExtra("date",dateX);
                        intent.putExtra("time",timeX);
                        intent.putExtra("location",locationX);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(NewOrder.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        receiverName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String Date = dayOfMonth + "/" + month + "/" + year;
        date.setText(Date);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}