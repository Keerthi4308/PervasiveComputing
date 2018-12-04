package com.pervasive_computing.bactrackappv2;

/*
  Created by Keerthi on 11/27/2018.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ExistingCircle extends AppCompatActivity {

    private static final String TAG = "DatabaseError";
    private TextView Circle_name;
    private TextView display_location;
    private TextView display_date;
    private DatabaseReference mStorageRef;//mStorageRef2;
   //private ArrayList<String> dataModel;
    private HashMap<String,String> dataModel;
    private String PRIMARY_KEY;
    ListView listView;
    String name="test";
    String BAClevel="0";
    String memberID=null;
    private   ValueEventListener circleDetailsListener,each_memberListener;
    private  ChildEventListener generate_membersListener;


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
        dataModel = new HashMap<>();
        //dataModel=new ArrayList<>();
        mStorageRef = FirebaseDatabase.getInstance().getReference();
        //.child("users").child("1");
       // mStorageRef2 = FirebaseDatabase.getInstance().getReference().child("members").child("1");
    }

    public void onStart() {
        super.onStart();

        //To update location and date on current Circle screen

       circleDetailsListener=new ValueEventListener() {
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

       //To update members list on current circle

        generate_membersListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                memberID = dataSnapshot.getValue(String.class);

                //get member details
                mStorageRef.child("user profile").child(memberID).addValueEventListener(each_memberListener);
                Log.d("Jey", dataModel.toString());
//                dataModel.put(UserId,other details);
//
//                //dataModel.add(UserId);
//                ListAdapter listAdapter = new ArrayAdapter<String>(ExistingCircle.this, R.layout.simple_list_item, dataModel);
//                listView.setAdapter(listAdapter);

//                ListAdapter listAdapter = new CustomMemberListAdapter(ExistingCircle.this,R.layout.member_list_item,dataModel);
//                listView.setAdapter(listAdapter);



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

        //To update each memeber details in member list
        each_memberListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Userprofile up = dataSnapshot.getValue(Userprofile.class);
                BAClevel=up.memberBAC;
                String name=up.fullName;


                dataModel.put(BAClevel,name);
                //listView.deferNotifyDataSetChanged();

                ListAdapter listAdapter = new CustomMemberListAdapter(ExistingCircle.this,R.layout.member_list_item,dataModel);
                listView.deferNotifyDataSetChanged();
                listView.setAdapter(listAdapter);
                Log.d("liseen", dataModel.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());

            }
        };


        mStorageRef.child("circles").child(PRIMARY_KEY).addValueEventListener(circleDetailsListener);

        mStorageRef.child("members").child(PRIMARY_KEY).addChildEventListener(generate_membersListener);


    }
    public void onClickJoin(View view) {


        final String current_userid="2";

        //add user and ask for volunteer
        mStorageRef.child("members").child(PRIMARY_KEY).push().setValue(current_userid);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.askvolunteer);
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //update as volunteer for circle
                                mStorageRef.child("circles").child(PRIMARY_KEY).child("volunteer").setValue(current_userid);

                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        //update list
        listView.deferNotifyDataSetChanged();

    }

    public void onClickShare(View view) {

        Intent openmap=new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps"));
        startActivity(openmap);
    }
    public void onClickBook(View view){

        Intent openmap=new Intent(Intent.ACTION_VIEW,Uri.parse("https://m.uber.com/ul/?action=setPickup"));
        startActivity(openmap);

    }
    public void onClickNotify(View view){

        final String current_userid="2";

        //update alert in firebase
        mStorageRef.child("user profile").child(current_userid).child("alert").setValue("yes");
    }

    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (circleDetailsListener != null) {
            //mStorageRef1
            mStorageRef.child("circles").child(PRIMARY_KEY).removeEventListener(circleDetailsListener);
        }

        if (generate_membersListener != null) {
            //mStorageRef2
            mStorageRef.child("members").child(PRIMARY_KEY).removeEventListener(generate_membersListener);
        }

        if (each_memberListener != null) {
            //mStorageRef2
            mStorageRef.child("user profile").child(PRIMARY_KEY).removeEventListener(each_memberListener);
        }


    }

    }
