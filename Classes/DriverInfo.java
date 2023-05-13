package com.example.Driverr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class DriverInfo extends AppCompatActivity {
    private DatabaseReference driverInfoReference;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView ghs;
    private TextView license;
    private TextView price;
    ///
    private String pricee;

    private String vehicle;
    private String driverKey;
    private ImageView callButton;
    private ImageView chatButton;
    private String vehiclee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_info);

        callButton = (ImageView) findViewById(R.id.callButton);
        chatButton = (ImageView) findViewById(R.id.chatBtn);
        Intent intent = getIntent();
        pricee = intent.getStringExtra("price");
        vehicle = intent.getStringExtra("vehicle"); ///v2,v1,v3....etc
        vehiclee = intent.getStringExtra("vehiclee");
        driverKey=intent.getStringExtra("driverKey");
        //Toast.makeText(getApplicationContext(),vehiclee+""+driverKey, Toast.LENGTH_SHORT).show();
        if (vehicle.equals("v1")) {
            vehicle = "1";
        }
        if (vehicle.equals("v2")) {
            vehicle = "2";
        }
        if (vehicle.equals("v3")) {
            vehicle = "3";
        }
        if (vehicle.equals("v4")) {
            vehicle = "4";
        }
        if (vehicle.equals("v5")) {
            vehicle = "5";
        }
        if (vehicle.equals("v6")) {
            vehicle = "6";
        }
        if (vehicle.equals("v7")) {
            vehicle = "7";
        }
        driverKey = intent.getStringExtra("driverKey");

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        ghs = (TextView) findViewById(R.id.ghs);
        license = (TextView) findViewById(R.id.license);
        price = (TextView) findViewById(R.id.price);

        //database
        driverInfoReference = FirebaseDatabase.getInstance().getReference().child("Drivers Available").child(vehicle).child(driverKey);
        data();
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(DriverInfo.this,Messages.class);
                intent2.putExtra("vehiclee", vehiclee);
                intent2.putExtra("driverKey",driverKey);
                intent2.putExtra("drivername",name.getText());
                startActivity(intent2);
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber = phone.getText().toString();
                Intent intent1 = new Intent(Intent.ACTION_CALL);
                intent1.setData(Uri.parse("tel:" + phonenumber));
                startActivity(intent1);
            }
        });

    }

    public void data() {
        driverInfoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                retrieveDriveInfo ret = snapshot.getValue(retrieveDriveInfo.class);
                //Toast.makeText(getApplicationContext(),ret.email+"", Toast.LENGTH_SHORT).show();
                name.setText(ret.name);
                price.setText(pricee + " Rs");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}