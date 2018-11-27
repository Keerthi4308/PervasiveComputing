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

public class WillYouDrink extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.will_you_drink);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");

        user = mAuth.getCurrentUser();
    }

    public void will_drink(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will drink"));
        startActivity(new Intent(getApplicationContext(), HowGoingHome.class));
        finish();
    }

    public void maybe_will_drink(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will maybe drink"));
        startActivity(new Intent(getApplicationContext(), HowGoingHome.class));
        finish();
    }

    public void will_not_drink(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User reported they will not drink"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }
}
