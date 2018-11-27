package com.pervasive_computing.bactrackappv2;
/*
  Created by Pratik on 11/13/2017.
 */

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BlowActivity extends BaseActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blow);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");

        user = mAuth.getCurrentUser();
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User is on blow activity"));

    }

}
