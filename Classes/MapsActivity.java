package com.example.Driverr;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.Driverr.databinding.ActivityMapsBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {
    //location
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location lastlocation;




    private ActivityMapsBinding binding;

    private Button CallTruck;
    private Button confirm;
    private Boolean callTruckButtonClicked=false;


    //
    private FirebaseAuth mAuth;
    private DatabaseReference customerdatabaseReference;


    private String customerID;
    private LatLng customerPickupLocation;


    private DatabaseReference driverUpdatLoc;
    private DatabaseReference vehiccle;
    private DatabaseReference MyCustomers;
    //

    private String[] reqPermission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private final int REQUEST_PERMISSION = 1;

    SupportMapFragment mapFrag;
    ArrayList locationList;
    ArrayList keys;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent1=getIntent();
        //got locations and their keys_id
        keys=intent1.getStringArrayListExtra("KeysList");
        locationList=intent1.getStringArrayListExtra("LocationList");


        ActivityCompat.requestPermissions(MapsActivity.this,
                new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        enableLocationSettings();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new Timer().schedule(new TimerTask(){@Override public void run() { runOnUiThread(new Runnable(){

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override public void run() { setLocation(); };}); }}, 5000,1000);

        vehiccle= FirebaseDatabase.getInstance().getReference().child("Mapping");
        driverUpdatLoc= FirebaseDatabase.getInstance().getReference().child("Drivers Available");
        mAuth=FirebaseAuth.getInstance();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        MyCustomers=FirebaseDatabase.getInstance().getReference().child("Customers Requests").child("LuEvpF4WttPucOc9aBWoMJBrydu1").child("l");


        //

        customerID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        customerdatabaseReference=FirebaseDatabase.getInstance().getReference().child("Customers Requests");
        //deciding vehicle driver here

        //

        CallTruck=(Button)findViewById(R.id.calltruck);
        confirm=(Button)findViewById(R.id.confirm);
        confirm.setVisibility(View.INVISIBLE);

        getCustomers();
        }

    private void getCustomers(){
        for (int hg=0;hg<locationList.size();hg++){
            ArrayList zz=(ArrayList) locationList.get(hg);
            double latt=(double)zz.get(0);
            double langg=(double)zz.get(1);
            LatLng customerPickupLocationk = new LatLng(latt, langg);
            try {
                mMap.addMarker(new MarkerOptions().position(customerPickupLocationk).title("PickUP point").icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));
            }catch (Exception ex){}finally { }
    }}
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if ((ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION))
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(reqPermission, REQUEST_PERMISSION);
        }
        else{
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);}


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    protected synchronized void buildGoogleApiClient(){
        if ((ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION))
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(reqPermission, REQUEST_PERMISSION);
        }

        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        }

        @Override
        protected void onStop(){
        super.onStop();
        }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLocationChanged(@NonNull Location location) {
        getCustomers();
        if ((ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION))
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(reqPermission, REQUEST_PERMISSION);
        }
        else{

        lastlocation=location;

            String uid=mAuth.getCurrentUser().getUid()+"";
            vehiccle.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    enableLocationSettings();
                    if (snapshot.exists()){
                    String veh=(String) snapshot.getValue().toString();
                    try{
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        float longituded=(float) latLng.longitude;
                        float latti=(float) latLng.latitude;
                        driverUpdatLoc.child(veh).child(uid).child("l").child("0").setValue(latti);
                        driverUpdatLoc.child(veh).child(uid).child("l").child("1").setValue(longituded);
                        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                       // mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    }
                    catch (Exception ex){} finally { }}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }}


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override public void onConnected(@Nullable Bundle bundle) {

        setLocation();
    }

    protected void enableLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(this, (LocationSettingsResponse response) -> {
                })
                .addOnFailureListener(this, ex -> {

                    if (ex instanceof ResolvableApiException) {
                        // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),  and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) ex;
                            resolvable.startResolutionForResult(MapsActivity.this, 1);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setLocation() {
        if ((ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION))
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(reqPermission, REQUEST_PERMISSION);
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);

        if ((ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION))
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(reqPermission, REQUEST_PERMISSION);
        }
        else{
            try{
            lastlocation=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            onLocationChanged(lastlocation);}catch (Exception ex){}finally {

            }
            //Toast.makeText(MapsActivity.this,"Connected",Toast.LENGTH_SHORT).show();

        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_PERMISSION:
                try{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&grantResults[2] == PackageManager.PERMISSION_GRANTED)
                    setLocation();}
                catch (Exception ex){}finally { }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}