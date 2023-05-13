package com.example.Driverr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Messages extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference messageReference;
    private DatabaseReference retrieveReference;
    private EditText MessageBox;
    private Button sendBtn;
    private TextView namee;
    ArrayList<String> arrayList;
    private ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private String email;
    private String Cuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        namee = (TextView) findViewById(R.id.namee);
        mAuth=FirebaseAuth.getInstance();
        MessageBox=(EditText)findViewById(R.id.messageBox);
        sendBtn=(Button) findViewById(R.id.sendBtn);
        Intent intent = getIntent();

        email=intent.getStringExtra("email");
        Cuid=intent.getStringExtra("Cuid");
        namee.setText(email);
        String uid=mAuth.getCurrentUser().getUid();
        //Toast.makeText(getApplicationContext(),""+name,Toast.LENGTH_SHORT).show();
        messageReference= FirebaseDatabase.getInstance().getReference().
                child("Drivers Available").child("1").child(uid).
                child("messages").child(Cuid).child("messages");

        retrieveReference= FirebaseDatabase.getInstance().getReference().
                child("Drivers Available").child("1").child(uid).child("messages").child(Cuid);

        listView=(ListView) findViewById(R.id.listview);
        arrayList=new ArrayList<String>();
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        updateChatWindow();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MessageBox.length()>0) {
                    String messageDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    DatabaseReference messageRef = messageReference.push();
                    messageRef.child("messagebody").setValue("Driver: "+MessageBox.getText().toString());
                    messageRef.child("messagedate").setValue(messageDate);
                    updateChatWindow();

                }
            }
        });


    }
    public void updateChatWindow() {
        retrieveReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                retrieveMessages ret = snapshot.getValue(retrieveMessages.class);
                if (snapshot.exists()){
                Map<String, Object> td = (HashMap<String, Object>) ret.messages;
                if (td != null) {
                    JSONArray values = new JSONArray(td.values());

                    MessageBox.setText("");
                    listView.setAdapter(null);
                    arrayList.clear();

                    for (int i = values.length()-1; i>=0; i--) {
                        JSONObject jsonObject = values.optJSONObject(i);
                        String message = jsonObject.optString("messagebody");
                        arrayList.add(message);
                    }
                    listView.setAdapter(arrayAdapter);

                }}}

                @Override
                public void onCancelled (@NonNull DatabaseError error){

                }
            });

}}