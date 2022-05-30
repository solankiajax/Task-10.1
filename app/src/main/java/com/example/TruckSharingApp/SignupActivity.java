package com.example.TruckSharingApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.TruckSharingApp.data.DatabaseHelper;
import com.example.TruckSharingApp.model.User;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;

public class SignupActivity extends AppCompatActivity {
    DatabaseHelper db;
    ImageView image;
    public static final String[] permissionArray = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        EditText sUsernameEditText = findViewById(R.id.sUsernameEditText);
        EditText sPasswordEditText = findViewById(R.id.sPasswordEditText);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        EditText fullNameEditText = findViewById(R.id.fullNameEditText);
        EditText phoneNoEditText = findViewById(R.id.phoneNoEditText);
        Button saveButton = findViewById(R.id.saveButton);
        ImageView addImage = findViewById(R.id.addImage);
        image = findViewById(R.id.image);
        db = new DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = sUsernameEditText.getText().toString();
                String password = sPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String phone_no = phoneNoEditText.getText().toString();
                String full_name = fullNameEditText.getText().toString();

                Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

                try {
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArray);
                    byte[] img = byteArray.toByteArray();
                    if (password.equals(confirmPassword)&&!(username.isEmpty()))
                    {
                        long result = db.insertUser(new User(username, password,phone_no,full_name,img));
                        if (result > 0)
                        {
                            Toast.makeText(SignupActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(SignupActivity.this, "Registration error!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        if(password.equals(confirmPassword)&&!password.isEmpty())
                            Toast.makeText(SignupActivity.this, "Please enter values in all relevant fields", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(SignupActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(mContext,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions((Activity) mContext,permissionArray,1);
                }
                if(ActivityCompat.checkSelfPermission(mContext,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    callGallery();
                }
            }
        });

        sUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        sPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        fullNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        phoneNoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }

    public void callGallery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Select Photo From Gallery");

        String[] items = {"Gallery"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        CropImage.activity()
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(SignupActivity.this);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(image);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

                try {
                    throw error;
                } catch (Exception e) {
                    Log.d("reached", e.getMessage());
                }
            }
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}