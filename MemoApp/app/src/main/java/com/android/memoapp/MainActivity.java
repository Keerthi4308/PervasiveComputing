package com.android.memoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText phNo;
    EditText emailId;
    Button submit;
    public static String user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=(EditText) findViewById(R.id.editText_Name);
        phNo=(EditText) findViewById(R.id.editText_PhNo);
        emailId=(EditText) findViewById(R.id.editText_EmailId);
        submit=(Button) findViewById(R.id.button_Submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty() || name.getText().toString() == null) {
                    Toast.makeText(MainActivity.this, "Name is required", Toast.LENGTH_LONG).show();
                } else if (emailId.getText().toString().isEmpty() || emailId.getText().toString() == null) {
                    Toast.makeText(MainActivity.this, "Email is required", Toast.LENGTH_LONG).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches()) {
                    Toast.makeText(MainActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                } else if (phNo.getText().toString().isEmpty() || phNo.getText().toString() == null) {
                    Toast.makeText(MainActivity.this, "PhNo is required", Toast.LENGTH_LONG).show();
                } else {


                    Intent displayView = new Intent(MainActivity.this, DisplayActivity.class);
                    User user = new User(name.getText().toString(),emailId.getText().toString(), phNo.getText().toString());
                    displayView.putExtra(user_info, user);
                    startActivity(displayView);

                }
            }
        });
    }
}
