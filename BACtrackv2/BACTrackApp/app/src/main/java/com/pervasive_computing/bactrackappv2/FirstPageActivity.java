package com.pervasive_computing.bactrackappv2;

/*
  Created by Pratik on 11/09/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirstPageActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");

        user = mAuth.getCurrentUser();
    }

    public void checkBAC(View view) {
        startActivity(new Intent(getApplicationContext(), InitiateActivity.class));
        finish();
    }

    public void checkFriendBAC(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("A friend is using the BAC next"));
        startActivity(new Intent(getApplicationContext(), InitiateActivity.class));
        finish();
    }
}
