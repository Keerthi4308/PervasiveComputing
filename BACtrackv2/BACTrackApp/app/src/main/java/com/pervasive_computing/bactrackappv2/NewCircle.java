package com.pervasive_computing.bactrackappv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class NewCircle extends AppCompatActivity {
    private static final String TAG = "DatabaseError";
    private DatabaseReference mStorageRef;
    private EditText location,date,name,uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_circle);
    }
    public void onSubmit(View view) throws ParseException {

        mStorageRef= FirebaseDatabase.getInstance().getReference();        //.child("Wherever you want to store this data");
        location = findViewById(R.id.circle_location);
        date = findViewById(R.id.circle_date);
        name=findViewById(R.id.circle_name);
        uid=findViewById(R.id.user_id);
        String cname=name.getText().toString();
        String loc  = location.getText().toString();
        String cdate = date.getText().toString();
        String userid=uid.getText().toString();

        //String str_date="13-09-2011";
        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date date = (Date)formatter.parse(cdate);
        String ts= String.valueOf(-1*date.getTime());

        if(!TextUtils.isEmpty(cname)&&!TextUtils.isEmpty(loc)&&!TextUtils.isEmpty(cdate))
        {
            //create a map
            HashMap< String,String> hm = new HashMap();
            hm.put("name", cname);
            hm.put("location", loc);
            hm.put("date", ts);
            String PrimaryKey= mStorageRef.child("circles").push().getKey();
            mStorageRef.child("circles").child(PrimaryKey).setValue(hm);
            mStorageRef.child("members").child(PrimaryKey).push().setValue(userid);
            finish();
          //  String key=mStorageRef.child("circles").push().setValue(hm); //see if you need to get anything after added
            //mStorageRef.child("members").push().setValue(hm);

        }

    }
}
