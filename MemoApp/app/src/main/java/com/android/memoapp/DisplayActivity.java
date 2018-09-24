package com.android.memoapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    TextView Name;
    TextView Email;
    TextView PhNo;
    User user;
    Button add;
    DatabaseHelper databaseHelper;
    ListView listView;
    public static int REQ_CODE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        add = (Button) findViewById(R.id.button_Add);
        databaseHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);

            user = (User) getIntent().getExtras().getSerializable(MainActivity.user_info);

        Name = findViewById(R.id.textView_Name);
        Name.setText("Name:" + user.getName());


        Email = findViewById(R.id.textView_EmailId);
        Email.setText("Name:" + user.getEmail());

        PhNo = findViewById(R.id.textView_PhNo);
        PhNo.setText("Name:" + user.getPhno());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(DisplayActivity.this, ThirdActivity.class);
                startActivityForResult(addIntent,REQ_CODE);
            }
        });
        dispList();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==RESULT_OK){
//            dispList();
//        }
        dispList();
    }



    private void dispList(){
        Cursor data=databaseHelper.getData();
        ArrayList<String> listData= new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));

        }
        ListAdapter listAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listData);
        listView.setAdapter(listAdapter);

    }
}
