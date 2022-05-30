package com.example.TruckSharingApp;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.example.TruckSharingApp.data.DatabaseHelperO;
import com.example.TruckSharingApp.model.Order;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.base.CharMatcher;
import com.google.firebase.firestore.GeoPoint;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapDisplay extends FragmentActivity implements OnMapReadyCallback,
       RoutingListener {

    //google map object
    private GoogleMap mMap;

    //current and destination location objects
    public LatLng start=null;
    public LatLng end=null;

    //to get location permissions.

    //polyline object
    private List<Polyline> polylines=null;

    // fused
    private FusedLocationProviderClient fusedLocationClient;

    // text view
    TextView pickUpLocationTV,dropOffLocationTV,approxFare,estTime;

    // button
    Button bookNowBtn,callDriverBtn;

    //public static final int PAYPAL_REQUEST_CODE = 7171;
    public static PayPalConfiguration payPalConfiguration  = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AXk4PT97Qa2JZhJzF8GY7ZGADUCoora0fMcXyZibk7r6gORgM3EHht-S7GdoqhuAS07GnyAvm4cNJfAy");

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_display);
        pickUpLocationTV = findViewById(R.id.pickUpLocationTV);
        dropOffLocationTV = findViewById(R.id.dropOffLocationTV);
        bookNowBtn = findViewById(R.id.bookNowBtn);
        callDriverBtn = findViewById(R.id.callDriverBtn);
        approxFare = findViewById(R.id.approxFare);
        estTime = findViewById(R.id.estTime);

        // fused initialized
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        //init google map fragment to show map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        start = new LatLng(28.6741,77.0931);

        try {
            callDriverBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(MapDisplay.this,
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:8130405714"));
                        startActivity(intent);
                    }
                    else{
                        ActivityCompat.requestPermissions(MapDisplay.this, new String[]{Manifest.permission.CALL_PHONE}, 0);
                    }
                }
            });
        }
        catch (Exception e){Log.d("reached",e.getMessage());}

        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            int code = result.getResultCode();
                            Intent data = result.getData();

                            if(code == RESULT_OK){
                                Toast.makeText(MapDisplay.this,"Payment Successful",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    private void processPayment() {
        try{String amount = approxFare.getText().toString();
            String theDigits = CharMatcher.anyOf("0123456789.").retainFrom(amount);
            Double doble = Double.parseDouble(theDigits);
            doble = doble/55;
            BigDecimal decimal = new BigDecimal(doble);

            PayPalPayment payPalPayment = new PayPalPayment(decimal,
                    "AUD","Your delivery fare",PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,payPalConfiguration);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
            activityResultLauncher.launch(intent);
        }
        catch (Exception e){
            Log.d("reached",e.getMessage());
        }

    }

    //to get user location
    @SuppressLint("MissingPermission")
    private void getMyLocation() throws IOException {

        DatabaseHelperO dbO = new DatabaseHelperO(this);
        Intent oldIntent = getIntent();
        int user_id = oldIntent.getIntExtra("user_id",0);
        int order_id = oldIntent.getIntExtra("order_id",0);
        Order order = dbO.fetchOrderObject(order_id,user_id);
        String pickUpaddress = order.getPickup_location();


        end = getLocationFromAddress(pickUpaddress);
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        addresses = geocoder.getFromLocation(end.latitude, end.longitude, 1);
        dropOffLocationTV.setText("Drop off Location: "+addresses.get(0).getLocality());
        addresses = geocoder.getFromLocation(start.latitude, start.longitude, 1);
        pickUpLocationTV.setText("Pick Up Location: "+addresses.get(0).getLocality());

        LatLng ltlng=new LatLng(start.latitude,start.longitude);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                ltlng, 8f);
        mMap.animateCamera(cameraUpdate);
        getDurationForRoute();
        if(start!=null&&end!=null){
            Findroutes(start,end);
        }else{
            Toast.makeText(MapDisplay.this,"please turn on location services x",Toast.LENGTH_SHORT).show();
        }
    }

    public LatLng getCurrentLocation2(String address){
        try {
            Geocoder coder = new Geocoder(this);
            List<Address> addresses;
            GeoPoint p1 = null;

            try {
                addresses = coder.getFromLocationName(address, 5);
                if (address == null) {
                    return null;
                }
                Address location = addresses.get(0);

                return new LatLng(location.getLatitude(), location.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }catch (Exception e){
            Log.d("reached gcl2: ",e.getMessage());
            return null;
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {
        try {
            Geocoder coder = new Geocoder(this);
            List<Address> address;
            GeoPoint p1 = null;

            try {
                address = coder.getFromLocationName(strAddress, 5);
                if (address == null) {
                    return null;
                }
                Address location = address.get(0);

                return new LatLng(location.getLatitude(), location.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }catch (Exception e){
            Log.d("reached glfa: ",e.getMessage());
            return null;
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try {
            getMyLocation();
        }catch (IOException e){
            Log.d("reached",e.getMessage());
        }

    }

    // function to find Routes.
    public void Findroutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            Toast.makeText(MapDisplay.this,"Unable to get location", Toast.LENGTH_LONG).show();
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyAWxTjWICSZbflbnyfR6uhvlUfNKwy17n4")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }



    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
        Log.d("reached",e.toString());
//       Findroutes(start,end);
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(MapDisplay.this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                int colorP = ContextCompat.getColor(this,R.color.design_default_color_primary);
                polyOptions.color(colorP);
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start,end);
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start,end);

    }

    public void getDurationForRoute(){
        String serverKey = getResources().getString(R.string.maps_api_key); // Api Key For Google Direction API \\
        final LatLng origin = new LatLng(28.6741,77.0931);
        final LatLng destination = end;
        GoogleDirection.withServerKey("AIzaSyAWxTjWICSZbflbnyfR6uhvlUfNKwy17n4")
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        String status = direction.getStatus();
                        if (status.equals(RequestResult.OK)) {
                            com.akexorcist.googledirection.model.Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            Info distanceInfo = leg.getDistance();
                            Info durationInfo = leg.getDuration();
                            String distance = distanceInfo.getText();
                            String duration = durationInfo.getText();
                            estTime.setText("Approx. Travel Time: " + duration);
                            String theDigits = CharMatcher.anyOf("0123456789.").retainFrom(distance);
                            Double baseCharge= 8.00;
                            Double price = baseCharge*Double.parseDouble(theDigits);
                            approxFare.setText("Approx Fare: Rs "+price.toString());

                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Log.d("reached","failed to fetch time");
                    }
                });
    }
}
