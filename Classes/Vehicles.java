package com.example.Driverr;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.firebase.auth.FirebaseAuth;

public class Vehicles extends AppCompatActivity {
    private ImageView v1;
    private ImageView v2;
    private ImageView v3;
    private ImageView v4;
    private ImageView v5;
    private ImageView v6;
    private ImageView v7;

    private EditText destination;
    private EditText startingg;

    private Button logout;
    private Boolean logoutFlag=false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles);

        //Permissions
        enableLocationSettings(); //for gps location
        //Location request from setting (call/location)
        ActivityCompat.requestPermissions(Vehicles.this,
                new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                1);

        destination=(EditText)findViewById(R.id.destination);
        startingg=(EditText)findViewById(R.id.starting);

        mAuth=FirebaseAuth.getInstance();
        logout=(Button)findViewById(R.id.logoutBTN);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutFlag=true;
                mAuth.signOut();
                Logout();
            }
        });
        v1=(ImageView) findViewById(R.id.v1);
        v2=(ImageView) findViewById(R.id.v2);
        v3=(ImageView) findViewById(R.id.v3);
        v4=(ImageView) findViewById(R.id.v4);
        v5=(ImageView) findViewById(R.id.v5);
        v6=(ImageView) findViewById(R.id.v6);
        v7=(ImageView) findViewById(R.id.v7);

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsIntent = new Intent(Vehicles.this,MapsActivity.class);
                MapsIntent.putExtra("vehicle", "v1");
                String destinationField=destination.getText().toString();
                String starting=startingg.getText().toString();
                if (TextUtils.isEmpty(destinationField)){
                    Toast.makeText(getApplicationContext(),"Please enter destination",Toast.LENGTH_SHORT).show();
                }
                else if((TextUtils.isEmpty(starting))){
                    Toast.makeText(getApplicationContext(),"Please enter starting location",Toast.LENGTH_SHORT).show();
                }
                else{
                    MapsIntent.putExtra("destination", destinationField);
                    MapsIntent.putExtra("starting", starting);
                    startActivity(MapsIntent);}
            }
        });
        //2
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsIntent = new Intent(Vehicles.this,MapsActivity.class);
                MapsIntent.putExtra("vehicle", "v2");
                String destinationField=destination.getText().toString();
                String starting=startingg.getText().toString();
                if (TextUtils.isEmpty(destinationField)){
                    Toast.makeText(getApplicationContext(),"Please enter destination",Toast.LENGTH_SHORT).show();
                }
                else if((TextUtils.isEmpty(starting))){
                    Toast.makeText(getApplicationContext(),"Please enter starting location",Toast.LENGTH_SHORT).show();
                }
                else{
                    MapsIntent.putExtra("destination", destinationField);
                    MapsIntent.putExtra("starting", starting);
                    startActivity(MapsIntent);
                }
            }
        });
        //3
        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsIntent = new Intent(Vehicles.this,MapsActivity.class);
                MapsIntent.putExtra("vehicle", "v3");
                String destinationField=destination.getText().toString();
                String starting=startingg.getText().toString();
                if (TextUtils.isEmpty(destinationField)){
                    Toast.makeText(getApplicationContext(),"Please enter destination",Toast.LENGTH_SHORT).show();
                }
                else if((TextUtils.isEmpty(starting))){
                    Toast.makeText(getApplicationContext(),"Please enter starting location",Toast.LENGTH_SHORT).show();
                }
                else{
                    MapsIntent.putExtra("destination", destinationField);
                    MapsIntent.putExtra("starting", starting);
                    startActivity(MapsIntent);}
            }
        });
        //4
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsIntent = new Intent(Vehicles.this,MapsActivity.class);
                MapsIntent.putExtra("vehicle", "v4");
                String destinationField=destination.getText().toString();
                String starting=startingg.getText().toString();
                if (TextUtils.isEmpty(destinationField)){
                    Toast.makeText(getApplicationContext(),"Please enter destination",Toast.LENGTH_SHORT).show();
                }
                else if((TextUtils.isEmpty(starting))){
                    Toast.makeText(getApplicationContext(),"Please enter starting location",Toast.LENGTH_SHORT).show();
                }
                else{
                    MapsIntent.putExtra("destination", destinationField);
                    MapsIntent.putExtra("starting", starting);
                    startActivity(MapsIntent);}
            }
        });
        //5
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsIntent = new Intent(Vehicles.this,MapsActivity.class);
                MapsIntent.putExtra("vehicle", "v5");
                String destinationField=destination.getText().toString();
                String starting=startingg.getText().toString();
                if (TextUtils.isEmpty(destinationField)){
                    Toast.makeText(getApplicationContext(),"Please enter destination",Toast.LENGTH_SHORT).show();
                }
                else if((TextUtils.isEmpty(starting))){
                    Toast.makeText(getApplicationContext(),"Please enter starting location",Toast.LENGTH_SHORT).show();
                }
                else{
                    MapsIntent.putExtra("destination", destinationField);
                    MapsIntent.putExtra("starting", starting);
                    startActivity(MapsIntent);}
            }
        });
        //6
        v6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsIntent = new Intent(Vehicles.this,MapsActivity.class);
                MapsIntent.putExtra("vehicle", "v6");
                String destinationField=destination.getText().toString();
                String starting=startingg.getText().toString();
                if (TextUtils.isEmpty(destinationField)){
                    Toast.makeText(getApplicationContext(),"Please enter destination",Toast.LENGTH_SHORT).show();
                }
                else if((TextUtils.isEmpty(starting))){
                    Toast.makeText(getApplicationContext(),"Please enter starting location",Toast.LENGTH_SHORT).show();
                }
                else{
                    MapsIntent.putExtra("destination", destinationField);
                    MapsIntent.putExtra("starting", starting);
                    startActivity(MapsIntent);}
            }
        });
        //7
        v7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MapsIntent = new Intent(Vehicles.this,MapsActivity.class);
                MapsIntent.putExtra("vehicle", "v7");
                String destinationField=destination.getText().toString();
                String starting=startingg.getText().toString();
                if (TextUtils.isEmpty(destinationField)){
                    Toast.makeText(getApplicationContext(),"Please enter destination",Toast.LENGTH_SHORT).show();
                }
                else if((TextUtils.isEmpty(starting))){
                    Toast.makeText(getApplicationContext(),"Please enter starting location",Toast.LENGTH_SHORT).show();
                }
                else{
                    MapsIntent.putExtra("destination", destinationField);
                    MapsIntent.putExtra("starting", starting);
                startActivity(MapsIntent);}
            }
        });
    }
    private void Logout(){
        Intent splash=new Intent(Vehicles.this,SplashActivity.class);
        splash.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(splash);
        finish();
    }
    protected void enableLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(this, (LocationSettingsResponse response) -> {
                    // startUpdatingLocation(...);
                })
                .addOnFailureListener(this, ex -> {
                    if (ex instanceof ResolvableApiException) {
                        // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) ex;
                            resolvable.startResolutionForResult(Vehicles.this, 1);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                });
    }
}