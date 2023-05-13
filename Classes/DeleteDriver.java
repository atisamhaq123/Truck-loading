package com.example.admin.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteDriver extends AppCompatActivity {
    private Button deleteButton;
    private TextView category;
    private EditText emailField;

    private ImageView v1;
    private ImageView v2;
    private ImageView v3;
    private ImageView v4;
    private ImageView v5;
    private ImageView v6;
    private ImageView v7;

    private DatabaseReference delREF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_driver);
        delREF=FirebaseDatabase.getInstance().getReference().child("delet");

        category=(TextView)findViewById(R.id.category);
        deleteButton=(Button)findViewById(R.id.GoPage);
        emailField=(EditText)findViewById(R.id.emailField);

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

        OnStartDelValue();
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    String cat=category.getText().toString();
                    String email=emailField.getText().toString();
                    Query applesQuery = ref.child("Drivers Available").child(cat).orderByChild("email").equalTo(email);
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) { appleSnapshot.getRef().removeValue();
                            delREF.setValue("1");}
                           // Toast.makeText(getApplicationContext(),"driver deleted",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}});

           showToast(); }});
        }
        private void OnStartDelValue(){
                    delREF.setValue("0");}

        private void showToast(){
            delREF.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String del=(String) dataSnapshot.getValue();
                    if (del.equals("1")){Toast.makeText(getApplicationContext(),"Driver Deleted",Toast.LENGTH_SHORT).show();
                        delREF.setValue("0");}
                    else{Toast.makeText(getApplicationContext(),"Error Deletion",Toast.LENGTH_SHORT).show();}
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {}});
        }
        }