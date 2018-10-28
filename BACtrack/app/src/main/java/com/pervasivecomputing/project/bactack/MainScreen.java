package com.pervasivecomputing.project.bactack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainScreen extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
    }
    /** Called when the user taps the Send button */
    public void OnCheck(View view) {
        Intent intent = new Intent(this, CheckIn.class);
//        TextView editText = findViewById(R.id.textView3);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void OnActivity(View view){
        Intent intent = new Intent(this, Others.class);
        startActivity(intent);
    }

    public void OnShare(View view){
        Intent intent = new Intent(this, Share.class);
        startActivity(intent);
    }
}
