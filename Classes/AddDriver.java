package com.example.admin.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDriver extends AppCompatActivity {



    //
    private ProgressDialog loadingBar;
    //
    private TextView name;
    private EditText emailBtn;
    private EditText passBtn;
    private EditText phonBtn;
    private Button RegBtn;
    private TextView GHS;
    private TextView license;

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference AddDriverdatabasereference;
    private String AddDriverID;
    private ImageView TruckIcon;
    String vehicle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);
        Intent intent = getIntent();
        vehicle = intent.getStringExtra("vehicle");

        TruckIcon=(ImageView)findViewById(R.id.TruckIcon);

        if (vehicle.equals("1")){TruckIcon.setBackgroundResource(R.drawable.htruck);}
        else if (vehicle.equals("2")){TruckIcon.setBackgroundResource(R.drawable.lorry);}
        else if (vehicle.equals("3")){TruckIcon.setBackgroundResource(R.drawable.recovery1);}
        else if (vehicle.equals("4")){TruckIcon.setBackgroundResource(R.drawable.truck);}
        else if (vehicle.equals("5")){TruckIcon.setBackgroundResource(R.drawable.transport1);}
        else if(vehicle.equals("6")){TruckIcon.setBackgroundResource(R.drawable.watertank);}
        else if (vehicle.equals("7")){TruckIcon.setBackgroundResource(R.drawable.tractor);}
        //Firebase
        mAuth=FirebaseAuth.getInstance();
        RegBtn = (Button) findViewById(R.id.RegBtn);
        //

        //Login-SignUp
        GHS=(TextView)findViewById(R.id.dGHS);
        name=(TextView)findViewById(R.id.dName);
        emailBtn = (EditText) findViewById(R.id.dEmail);
        passBtn = (EditText) findViewById(R.id.dPass);
        phonBtn=(EditText)findViewById(R.id.dPhone);
        license=(TextView)findViewById(R.id.dLicense);

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name=name.getText().toString();
                String ghs=GHS.getText().toString();
                String email=emailBtn.getText().toString();
                String password=passBtn.getText().toString();
                String phonee=phonBtn.getText().toString();
                String License=license.getText().toString();
                Register(email,password,phonee,Name,ghs,License);
            }
        });

    }
    private void Register(String email,String password,String phonee,String Name,String ghs,String License){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(AddDriver.this,"Please write email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(AddDriver.this,"Please write password",Toast.LENGTH_SHORT).show();
        }

       else if (TextUtils.isEmpty(phonee)){
            Toast.makeText(AddDriver.this,"Please write phone number",Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(Name)){
            Toast.makeText(AddDriver.this,"Please write Name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ghs)){
            Toast.makeText(AddDriver.this,"Please write GHS number",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(License)){
            Toast.makeText(AddDriver.this,"Please write License number",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar = new ProgressDialog(this);
            loadingBar.setTitle("Driver Registration");
            loadingBar.setMessage("Please wait while we Add Driver");
            loadingBar.setIndeterminate(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        firebaseUser=mAuth.getCurrentUser();
                        if(firebaseUser != null) {
                            AddDriverID = firebaseUser.getUid(); //Do what you need to do with the id
                            AddDriverdatabasereference= FirebaseDatabase.getInstance().getReference().child("Drivers Available").child(vehicle);
                            GeoFire geoFire = new GeoFire(AddDriverdatabasereference);
                            geoFire.setLocation(AddDriverID,new GeoLocation(33.71854487349762, 73.0716260241197));
                            AddDriverdatabasereference.child(AddDriverID).child("email").setValue(email);
                            AddDriverdatabasereference.child(AddDriverID).child("ghs").setValue(ghs);
                            AddDriverdatabasereference.child(AddDriverID).child("license").setValue(License);
                            AddDriverdatabasereference.child(AddDriverID).child("name").setValue(Name);
                            AddDriverdatabasereference.child(AddDriverID).child("phone").setValue(phonee);

                        }

                        Toast.makeText(AddDriver.this,"Driver Added",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        //

                        Intent intent=new Intent(AddDriver.this,adminHome.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(AddDriver.this,"Registration unsuccessful",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }
                }
            });
        }

    }

}


