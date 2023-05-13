package com.example.admin.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class adminHome extends AppCompatActivity {
    private Button addBTN;
    private Button GetBTN;
    private Button delBTN;
    private Button logout;
    private Boolean logoutFlag=false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        addBTN=(Button) findViewById(R.id.adBTN);
        GetBTN=(Button)findViewById(R.id.getBTN);
        delBTN=(Button) findViewById(R.id.delBTN);
        mAuth= FirebaseAuth.getInstance();
        logout=(Button)findViewById(R.id.LOGOUT);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutFlag=true;
                mAuth.signOut();
                Logout();
            }
        });
        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent21=new Intent(adminHome.this,AddDriverCategory.class);
                startActivity(intent21);
            }
        });
        delBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0=new Intent(adminHome.this, DeleteDriver.class);
                startActivity(intent0);
            }
        });
        GetBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent31=new Intent (adminHome.this,GetDriverList.class);
                startActivity(intent31);
            }
        });
    }
    private void Logout(){
        Intent login=new Intent(adminHome.this,Login.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }
}