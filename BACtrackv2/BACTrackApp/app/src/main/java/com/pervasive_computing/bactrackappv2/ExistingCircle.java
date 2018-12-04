package com.pervasive_computing.bactrackappv2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExistingCircle extends AppCompatActivity {

    private static final String TAG = "DatabaseError";
    private TextView Circle_name;
    private TextView display_location;
    private TextView display_date;
    private DatabaseReference mStorageRef;//mStorageRef2;
    private ArrayList<String> dataModel;
   // private HashMap<String,String> dataModel;
    private String PRIMARY_KEY;
    ListView listView;
    private String BAClevel="0.0";
    private   ValueEventListener oneTimeListener;
    private  ChildEventListener constantListener,constantBACListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_circle);
        Bundle extras = getIntent().getExtras();
        PRIMARY_KEY = null;
        if (extras != null) {
            PRIMARY_KEY = extras.getString("CIRCLE_ID");
        }

        listView = (ListView) findViewById(R.id.members_view);
        Circle_name=(TextView)findViewById(R.id.circlename);
        display_location=(TextView)findViewById(R.id.location);
        display_date=(TextView)findViewById(R.id.date);
        //dataModel = new HashMap<>();
        dataModel=new ArrayList<>();
        mStorageRef = FirebaseDatabase.getInstance().getReference();
        //.child("users").child("1");
       // mStorageRef2 = FirebaseDatabase.getInstance().getReference().child("members").child("1");
    }

    public void onStart() {
        super.onStart();

       oneTimeListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                CircleDetails cinfo = dataSnapshot.getValue(CircleDetails.class);
                Circle_name.setText(cinfo.name);
                display_location.setText(cinfo.location);
                //convert timestamp into date
                Date d = new Date(-1*(Long.parseLong(cinfo.date)));
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
                String formattedDate = formatter.format(d);

                display_date.setText(formattedDate);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        };

        constantListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String UserId = dataSnapshot.getValue(String.class);
                //fetch BAC from dataset
                mStorageRef.child("users").child(UserId).addChildEventListener(constantBACListener);
                dataModel.add(UserId);
                ListAdapter listAdapter = new ArrayAdapter<String>(ExistingCircle.this, R.layout.simple_list_item, dataModel);
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

        constantBACListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Userprofile up = dataSnapshot.getValue(Userprofile.class);
                //fetch BAC from dataset
                BAClevel =up.BAC;

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


        mStorageRef.child("circles").child(PRIMARY_KEY).addValueEventListener(oneTimeListener);

        mStorageRef.child("members").child(PRIMARY_KEY).addChildEventListener(constantListener);


    }
    public void onClickJoin(View view) {

        mStorageRef.child("members").child(PRIMARY_KEY).push().setValue(useremail);
        startActivity(new Intent(getApplicationContext(), ViewCircles.class));

    }

    public void onClickShare(View view) {

        startActivity(new Intent(getApplicationContext(), LocationService.class));

    }
    public void onClickBook(View view){

        //redirect to https://m.uber.com/?client_id=<CLIENT_ID>

    }


    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (oneTimeListener != null) {
            //mStorageRef1
            mStorageRef.child("circles").child(PRIMARY_KEY).removeEventListener(oneTimeListener);
        }

        if (constantListener != null) {
            //mStorageRef2
            mStorageRef.child("members").child(PRIMARY_KEY).removeEventListener(constantListener);
        }



    }

    }
