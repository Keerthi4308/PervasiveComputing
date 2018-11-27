package com.pervasive_computing.bactrackappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
  Created by Pratik on 11/07/2017.
 */

public class HowGoingHome extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_going_home);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");

        user = mAuth.getCurrentUser();


    }

    public void other_clicked(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will use other transportation"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }

    public void taxi_clicked(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will use taxi transportation"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }

    public void bus_clicked(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will use bus transportation"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }

    public void friends_clicked(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will use friends to get home"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }

    public void car_clicked(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will use car transportation"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }

    public void walk_clicked(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will walk home"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }
}
