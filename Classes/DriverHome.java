package com.example.Driverr;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DriverHome extends AppCompatActivity {
    private boolean  logoutFlag=false;
    private Button logout;
    private Button mapBTN;
    private FirebaseAuth mAuth;
    private DatabaseReference data;
    private DatabaseReference findEmail;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private DatabaseReference idemail;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        mapBTN=(Button)findViewById(R.id.mapBtn);
        ArrayList keyS=new ArrayList();
        ArrayList lonLat=new ArrayList();



        ArrayList<String> emails=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,emails);
        idemail=FirebaseDatabase.getInstance().getReference().child("IDEmail");
        //
        mAuth= FirebaseAuth.getInstance();
        String uid=mAuth.getCurrentUser().getUid();
        data= FirebaseDatabase.getInstance().getReference().child("Rides").child(uid);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idemail.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        HashMap cc=(HashMap)snapshot.getValue();
                        Set set = cc.entrySet();
                        Iterator i = set.iterator();
                        while (i.hasNext()) {
                            Map.Entry me = (Map.Entry) i.next();
                            if (me.getValue().equals(selectedItem)){
                                String Cuid=me.getKey().toString();
                                String email=selectedItem;
                                Intent new1=new Intent(getApplicationContext(),Messages.class);
                                new1.putExtra("email",email);
                                new1.putExtra("Cuid",Cuid);
                                startActivity(new1);
                            };
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        findEmail= FirebaseDatabase.getInstance().getReference().child("IDEmail");
        mapBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten1=new Intent(getApplicationContext(),MapsActivity.class);
                inten1.putExtra("LocationList",lonLat);
                inten1.putExtra("KeysList",keyS);
                startActivity(inten1);
            }
        });
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    HashMap cc = (HashMap) snapshot.getValue();
                    Set set = cc.entrySet();
                    Iterator i = set.iterator();
                    ArrayList ran = new ArrayList();

                    ArrayList dk = new ArrayList();

                    while (i.hasNext()) {
                        Map.Entry me = (Map.Entry) i.next();
                        keyS.add(me.getKey());
                    }
                    List<String> Value = new ArrayList<String>(cc.values());
                    for (int lpz = 0; lpz < cc.size(); lpz++) {
                        ran.add(Value.get(lpz));
                    }
                    for (int lpzz = 0; lpzz < ran.size(); lpzz++) {
                        HashMap<String, String> fi = (HashMap<String, String>) ran.get(lpzz);
                        Set set1 = fi.entrySet();
                        Iterator zk = set1.iterator();
                        while (zk.hasNext()) {
                            Map.Entry me1 = (Map.Entry) zk.next();  //key = z,I
                            dk.add(me1.getValue());
                        }
                        lonLat.clear();  //[jjjj,[31.82,72.536],kkkkkkk,[8.9,87]]
                        for (int tf = 0; tf < dk.size(); tf++) {
                            if (tf % 2 != 0) {
                                lonLat.add(dk.get(tf));
                            }
                        }
                        emails.clear();
                        for (int tr = 0; tr < keyS.size(); tr++) {
                            String iddd = keyS.get(tr).toString();
                            findEmail.child(iddd).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String drr = (String) snapshot.getValue();
                                    emails.add(drr);
                                    update(emails);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }
                }}

                @Override
                public void onCancelled (@NonNull DatabaseError error){
                }
             });



        logout = (Button) findViewById(R.id.LOGOUT);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutFlag = true;
                mAuth.signOut();
                Logout();
            }
        });

    }
    private void update(ArrayList email){
        HashSet hs = new HashSet();
        hs.addAll(email);
        email.clear();
        email.addAll(hs);
        listView.setAdapter(arrayAdapter);
    }


    private void Logout(){
        Intent login=new Intent(DriverHome.this,Login.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }
}