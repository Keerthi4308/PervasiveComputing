package com.pervasive_computing.bactrackappv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogInActivity extends AppCompatActivity {
    Button loginButton;
    TextView emailText;
    TextView passwordText;
    Button signUpButton;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    String TAG = "Login Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");

        user = mAuth.getCurrentUser();
        if(user != null)
        {
            myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User is already logged in"));
            Intent i = new Intent(LogInActivity.this, SplashActivity.class);
            LogInActivity.this.startActivity(i);

        }

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.joinUsButton);
        emailText = (TextView) findViewById(R.id.emailTextView);
        passwordText = (TextView) findViewById(R.id.passwordTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailText.getText() == "")
                {
                    Toast.makeText(view.getContext(), "Please Enter Your Email", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(passwordText.getText() == "")
                    {
                        Toast.makeText(view.getContext(), "Please Enter Your Password", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        //actually log in
                        mAuth.signInWithEmailAndPassword(emailText.getText().toString().trim(), passwordText.getText().toString().trim())
                                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                      if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithEmail:success");
                                            user = mAuth.getCurrentUser();
                                            myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User Logged Into Account"));
                                            Intent i = new Intent(LogInActivity.this, SplashActivity.class);
                                            LogInActivity.this.startActivity(i);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        }

                                        // ...
                                    }
                                });
                        //if successful:

                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailText.getText() == "")
                {
                    Toast.makeText(view.getContext(), "Please Enter an Email", Toast.LENGTH_LONG).show();
                }
                else
                {
                    if(passwordText.getText() == "")
                    {
                        Toast.makeText(view.getContext(), "Please Enter a Password", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        mAuth.createUserWithEmailAndPassword(emailText.getText().toString().trim(), passwordText.getText().toString().trim())
                                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "createUserWithEmail:success");
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User Created Account"));
                                            Intent i = new Intent(LogInActivity.this, SplashActivity.class);
                                            LogInActivity.this.startActivity(i);
                                        } else {
                                            Log.d(TAG, "createUserWithEmail:Failed");
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }
}
