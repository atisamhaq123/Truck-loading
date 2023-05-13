package com.example.Driverr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddDriverCategory extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView category;
    private Button go;

    private ImageView v1;
    private ImageView v2;
    private ImageView v3;
    private ImageView v4;
    private ImageView v5;
    private ImageView v6;
    private ImageView v7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver_category);
        category=(TextView)findViewById(R.id.category);
        go=(Button)findViewById(R.id.GoPage);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent1=new Intent(AddDriverCategory.this,DriverHome.class);
            startActivity(intent1);
            finish();
        }
        v1=(ImageView) findViewById(R.id.v1);
        v2=(ImageView) findViewById(R.id.v2);
        v3=(ImageView) findViewById(R.id.v3);
        v4=(ImageView) findViewById(R.id.v4);
        v5=(ImageView) findViewById(R.id.v5);
        v6=(ImageView) findViewById(R.id.v6);
        v7=(ImageView) findViewById(R.id.v7);

        v1.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {category.setText("1"); }});
        v2.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {category.setText("2"); }});
        v3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {category.setText("3"); }});
        v4.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {category.setText("4"); }});
        v5.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {category.setText("5"); }});
        v6.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {category.setText("6"); }});
        v7.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) {category.setText("7"); }});

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cat=category.getText().toString();
                Intent intent21=new Intent(AddDriverCategory.this, RegDriver.class);
                intent21.putExtra("vehicle",cat );
                startActivity(intent21);
            }
        });

    }
}