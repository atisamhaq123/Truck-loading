package com.example.admin.admin;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class GetDriverList extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference driversReference;
    private DatabaseReference AdminReference;
    ArrayAdapter<String> arrayAdapter;

    HashMap <String,Integer> pp;
    ArrayList<String> data_list_withRepetition;
    ArrayList<String> names_email_List;
    ArrayList<String> IDList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_driver_list);
        listView=(ListView) findViewById(R.id.listview);
        names_email_List=new ArrayList<String>();
        data_list_withRepetition=new ArrayList<String>();
        IDList=new ArrayList<String>();
        pp = new HashMap<String,Integer>();
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names_email_List);
        driversReference= FirebaseDatabase.getInstance().getReference().
                child("Drivers Available");
        AdminReference= FirebaseDatabase.getInstance().getReference().
                child("Admin");
        AdminReference.child("list").setValue("0");
        data();
        getPp();
       
    }
 
    public void data() {
        driversReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String veh = ds.getKey().toString();
                        data1(veh);
                }
            }}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void getPp() {
        //pp contains 1,2,3,....8
        //arrList contains all ids present in 1,2,3,..
       // Toast.makeText(getApplicationContext(), arrayList+ "", Toast.LENGTH_SHORT).show();
        AdminReference.child("list").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    IDList.clear();
                   if (!snapshot.getValue().equals("0")) {
                       HashMap<String, String> cc = (HashMap<String, String>) snapshot.getValue();
                       if (cc.size()>1 && cc.size()<9){
                       for (Map.Entry<String, String> set :
                               cc.entrySet()) {
                           IDList.add(set.getValue());
                       }}}}
                       ////dddd
                           data_list_withRepetition.clear();
                           for(int i = 0; i < IDList.size(); i++){
                               String[] arrOfStr=IDList.get(i).split("@",2);

                               Query query = driversReference.child(arrOfStr[1]).child(arrOfStr[0]);
                               query.addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                                       if (snapshot.exists()) {
                                           String name=snapshot.child("name").getValue(String.class);
                                           String email=snapshot.child("email").getValue(String.class);
                                           String driver=new String();
                                           if (arrOfStr[1].equals("1")){driver="HalfFilled";}
                                           else if (arrOfStr[1].equals("2")){driver="lorry";}
                                           else if (arrOfStr[1].equals("3")){driver="Wheeler";}
                                           else if (arrOfStr[1].equals("4")){driver="junk ";}
                                           else if (arrOfStr[1].equals("5")){driver="cargo ";}
                                           else if (arrOfStr[1].equals("6")){driver="tank";}
                                           else if (arrOfStr[1].equals("7")){driver="tractor";}
                                           data_list_withRepetition.add(name+"  ___  "+email+"_____:"+driver);}
                                          // Toast.makeText(getApplicationContext(), namesList + "", Toast.LENGTH_SHORT).show();
                                       //
                                       HashSet<String> hashSetNumbers= new HashSet<String>(data_list_withRepetition);
                                       names_email_List.clear();
                                       for (String val : hashSetNumbers){
                                           names_email_List.add(val.toString());}
                                       listView.setAdapter(arrayAdapter);
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError error) { };
                               });}

                           ///ddd



                   }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }});

        //
    }

    public void data1(String veh) {
        driversReference.child(veh).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        String cc=ds.getKey();
                        DatabaseReference addRef=AdminReference.child("list").push();
                        addRef.setValue(cc+"@"+veh); }}}
            @Override
            public void onCancelled(@NonNull DatabaseError error) { };}); }}


