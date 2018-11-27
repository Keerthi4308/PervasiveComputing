package com.pervasive_computing.bactrackappv2;
/*
  Created by Pratik on 11/28/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RestartActivity extends BaseActivity {
    private static final String RESTART_NEEDED = "RESTART_NEEDED";
    private static final String INTERNET_NEEDED = "INTERNET_NEEDED";
    private static boolean restartNeeded;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restart_screen);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");

        user = mAuth.getCurrentUser();
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("Restarted Needed Detected"));
        restartNeeded = getIntent().getBooleanExtra(RESTART_NEEDED, true);
        boolean internetNeeded = getIntent().getBooleanExtra(INTERNET_NEEDED, false);
        Button restart = findViewById(R.id.retry_btn);
        TextView body = findViewById(R.id.retry_message);
        TextView title = findViewById(R.id.retry_title);
        if (restartNeeded) {
            title.setText("Restart Needed");
            if (!internetNeeded)
                body.setText("The app needs to be restarted");
            else body.setText("Please connect to the Internet and Restart the App");
            restart.setText("Restart Now!");
        } else {
            title.setText("Retry Needed");
            body.setText("Retry BAC Test");
            restart.setText("Retry Now!");
        }
    }

    public void retry_clicked(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User restarted app"));
        if (restartNeeded)
            startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        else
            startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
    }
}
