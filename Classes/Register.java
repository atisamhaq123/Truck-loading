package com.example.admin.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private Button RegBtn;

    //
    private ProgressDialog loadingBar;
    //
    private EditText emailBtn;
    private EditText passBtn;
    private EditText phonBtn;

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference Admindatabasereference;
    private DatabaseReference IDEmail;
    private String AdminID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Firebase
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent=new Intent(Register.this,Vehicles.class);
            startActivity(intent);
            finish();
        }

        //
        loadingBar = new ProgressDialog(this);
        RegBtn = (Button) findViewById(R.id.RegBtn);
        //

        //Login-SignUp
        emailBtn = (EditText) findViewById(R.id.dEmail);
        passBtn = (EditText) findViewById(R.id.dPass);
        phonBtn=(EditText)findViewById(R.id.dPhone);

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailBtn.getText().toString();
                String password=passBtn.getText().toString();
                String phonee=phonBtn.getText().toString();
                Register(email,password,phonee);
            }
        });

    }
    private void Register(String email,String password,String phonee){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(Register.this,"Please write email",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(Register.this,"Please write password",Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(phonee)){
            Toast.makeText(Register.this,"Please write phone number",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Admin Registration");
            loadingBar.setMessage("Please wait while we complete registration");
            loadingBar.setIndeterminate(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        firebaseUser=mAuth.getCurrentUser();
                        if(firebaseUser != null) {
                            AdminID = firebaseUser.getUid(); //Do what you need to do with the id
                            IDEmail=FirebaseDatabase.getInstance().getReference().child("IDEmail").child(AdminID);
                            IDEmail.setValue(email);
                            Admindatabasereference= FirebaseDatabase.getInstance().getReference().child("All_Admins").child(AdminID);
                            Admindatabasereference.child("email").setValue(email);
                            Admindatabasereference.child("phone").setValue(phonee);
                        }

                        Toast.makeText(Register.this,"Admin Registered",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        //

                        Intent intent=new Intent(Register.this,adminHome.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(Register.this,"Registration unsuccessful",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                    }
                }
            });
        }

    }

}


