package com.pervasive_computing.bactrackappv2;
/*
  Created by Pratik on 11/15/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GreenActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.green_screen);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");

        user = mAuth.getCurrentUser();
        float bac_level = getIntent().getFloatExtra(getString(R.string.BAC_LEVEL), -1);
        TextView textView = findViewById(R.id.bac_level);
        textView.setText(String.format("%s", bac_level));
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User has BAC: " + textView.getText()));
    }

    public void try_again_clocked(View view) {
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
    }
}
