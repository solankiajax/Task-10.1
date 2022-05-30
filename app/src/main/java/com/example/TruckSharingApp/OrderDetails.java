package com.example.TruckSharingApp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.TruckSharingApp.data.DatabaseHelper;
import com.example.TruckSharingApp.data.DatabaseHelperO;
import com.example.TruckSharingApp.model.Order;
import com.example.TruckSharingApp.model.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class OrderDetails extends AppCompatActivity {
    ImageView orderDetailImage;
    TextView senderUsername,senderPickUpDate,receiverUsername,
            cardWeight,cardType,cardWidth,cardHeight,cardLength,receiverDropOfDate;
    DatabaseHelper db;
    DatabaseHelperO dbO;
    Button getEstimateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent oldIntent = getIntent();
        int order_id = oldIntent.getIntExtra("order_id",0);
        int user_id = oldIntent.getIntExtra("user_id",0);
        db = new DatabaseHelper(this);
        User user = db.fetchUserObject(user_id);
        dbO = new DatabaseHelperO(this);
        Order order = dbO.fetchOrderObject(order_id,user_id);
        Log.d("reached details",order.getPickup_location());

        orderDetailImage = findViewById(R.id.orderDetailImage);
        senderUsername= findViewById(R.id.senderUsername);
        senderPickUpDate = findViewById(R.id.senderPickUpDate);
        receiverUsername = findViewById(R.id.receiverUsername);
        cardWeight = findViewById(R.id.cardWeight);
        cardType = findViewById(R.id.cardType);
        cardWidth = findViewById(R.id.cardWidth);
        cardHeight = findViewById(R.id.cardHeight);
        cardLength = findViewById(R.id.cardLength);
        getEstimateBtn = findViewById(R.id.getEstimateBtn);
        receiverDropOfDate = findViewById(R.id.receiverDropOfDate);

        orderDetailImage.setImageBitmap(BitmapFactory.decodeByteArray(user.getImg(), 0, user.getImg().length));
        senderUsername.setText(user.getFull_name());
        senderPickUpDate.setText(order.getPickup_date());
        receiverUsername.setText(order.getReceiver_name());
        cardWidth.setText("Width:\n" + order.getWidth() + " m");
        cardWeight.setText("Weight:\n"+order.getWeight()+ " kg");
        cardType.setText("Type:\n" + order.getGood_type());
        cardLength.setText("Length:\n"+order.getLength()+ " m");
        cardHeight.setText("Height:\n" + order.getHeight() + " m");

        String dt = order.getPickup_date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c =Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE,4);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        String output = sdf1.format(c.getTime());

        receiverDropOfDate.setText(output);


        getEstimateBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent oldIntent = getIntent();
                int user_id = oldIntent.getIntExtra("user_id",0);
                int order_id = oldIntent.getIntExtra("order_id",0);
                Intent intent = new Intent(OrderDetails.this,MapDisplay.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("order_id", order_id);
                startActivity(intent);
            }
        });
    }
}