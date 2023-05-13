package com.example.Driverr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private Button LogBtn;
    private Button RegBtn;
    private TextView forgot;


    //
    private ProgressDialog loadingBar;
    //
    private EditText emailBtn;
    private EditText passBtn;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //Firebase
        mAuth=FirebaseAuth.getInstance();
        //Check user login or not
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent=new Intent(Login.this,DriverHome.class);
            startActivity(intent);
            finish();
        }

        //
        loadingBar = new ProgressDialog(this);
        LogBtn = (Button) findViewById(R.id.logBtn);
        RegBtn = (Button) findViewById(R.id.regBtn);
        forgot= (TextView) findViewById(R.id.forgetButton);

        //




        //Login-SignUp
        emailBtn = (EditText) findViewById(R.id.emailBut);
        passBtn = (EditText) findViewById(R.id.passbut);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecoverPasswordDialog();
            }
        });
        LogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailBtn.getText().toString();
                String password=passBtn.getText().toString();
                LogIn(email,password);
            }
        });
        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddDriverCategory.class);
                startActivity(intent);
            }
        });
    }
    private void showRecoverPasswordDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText emailet= new EditText(this);

        // write the email using which you registered
        emailet.setText("Write Email");
        emailet.setMinEms(16);
        emailet.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        linearLayout.addView(emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);

        // Click on Recover and a email will be sent to your registered email id
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=emailet.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void beginRecovery(String email){
        loadingBar.setMessage("Sending Email....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        // calling sendPasswordResetEmail
        // open your email and write the new
        // password and then you can login
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                loadingBar.dismiss();
                if(task.isSuccessful())
                {
                    // if isSuccessful then done message will be shown
                    // and you can change the password
                    Toast.makeText(Login.this,"Email sent",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Login.this,"Error Occurred",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadingBar.dismiss();
                Toast.makeText(Login.this,"Error Failed",Toast.LENGTH_LONG).show();
            }
        });
    }
        private void LogIn(String email,String password){
            if (TextUtils.isEmpty(email)){
                Toast.makeText(Login.this,"Please write email",Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(Login.this,"Please write password",Toast.LENGTH_SHORT).show();
            }
            else{
                loadingBar.setTitle("Driver Login");
                loadingBar.setMessage("Please wait while we check your credentials");
                loadingBar.setIndeterminate(true);
                loadingBar.show();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(Login.this, DriverHome.class);
                            startActivity(intent);
                            //

                        }
                        else{
                            Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();


                        }
                    }
                });
            }

        }


    }


