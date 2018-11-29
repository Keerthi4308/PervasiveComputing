package com.pervasive_computing.bactrackappv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewCircles extends AppCompatActivity {

    private static final String TAG = "DatabaseError";
    private DatabaseReference mStorageRef;
    private ArrayList<String> circles;
    private HashMap<String,String> circles_dict=new HashMap<>();
    FloatingActionButton fb;
    ListView listView;
    Query query;
    //private static CustomAdapter adapter;
    private ChildEventListener constantListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_circles);
        listView = (ListView) findViewById(R.id.circles_list);
        fb=(FloatingActionButton)findViewById(R.id.add_circle);
        circles= new ArrayList<String>();
        mStorageRef = FirebaseDatabase.getInstance().getReference().child("circles");
        query=mStorageRef.orderByChild("date");
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewCircles.this,NewCircle.class);
                startActivity(intent);

            }
        });

    }
    public void onStart(){
        super.onStart();

        constantListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                CircleDetails cval = dataSnapshot.getValue(CircleDetails.class);
                String ckey=dataSnapshot.getKey();
                circles.add(cval.name);
                circles_dict.put(cval.name,ckey);
                ListAdapter listAdapter=new ArrayAdapter<String>(ViewCircles.this,R.layout.simple_list_item,circles);
                listView.setAdapter(listAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // mStorageRef.addListenerForSingleValueEvent(oneTimeListener);
//        adapter= new CustomAdapter(Circles_list.this,R.layout.new_adaptar_view,dataModel);
//        listView.setAdapter(adapter);
           query.addChildEventListener(constantListener);
           listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   String tempInfo = (String) parent.getItemAtPosition(position);
                   String circles_id = circles_dict.get(tempInfo);
                   //DatabaseReference itemRef = adapter.getRef(position); itemRef.key() should give key in case firebase adapter is used

                   Intent intent = new Intent(ViewCircles.this, ExistingCircle.class);
                   intent.putExtra("CIRCLE_ID", circles_id);
                   startActivity(intent);
                   listView.setAdapter(null);

               }
           });
    }

    @Override
    public void onStop() {
        super.onStop();

        if (constantListener != null) {
            mStorageRef.removeEventListener(constantListener);
            listView.setAdapter(null);
        }


    }
}
