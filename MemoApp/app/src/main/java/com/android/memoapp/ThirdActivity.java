package com.android.memoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText title;
    EditText content;
    Button save;
    DatabaseHelper databaseHelper;

    public String mtitle,mcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        title=(EditText) findViewById(R.id.editText_Title);
        content=(EditText) findViewById(R.id.editText_Content);
        save=(Button) findViewById(R.id.button_Save);
        databaseHelper=new DatabaseHelper(this);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mtitle=title.getText().toString();
                mcontent=content.getText().toString();
                if(mtitle.length()!=0 && mcontent.length()!=0){
                    AddData(mtitle,mcontent);
                    Intent viewIntent = new Intent(ThirdActivity.this, DisplayActivity.class);
                    finish();
                }else{
                    Toast.makeText(ThirdActivity.this, "Missing fields", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void AddData(String title,String content){
        boolean insertData=databaseHelper.addData(title,content);

        if(insertData){
            Toast.makeText(ThirdActivity.this, "Inserted data", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(ThirdActivity.this, "Couldnt Insert data", Toast.LENGTH_LONG).show();

        }

    }
}
