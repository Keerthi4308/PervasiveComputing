package com.pervasivecomputing.project.bactack;



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

import java.util.HashMap;


public class CheckIn extends AppCompatActivity {

    private static final String TAG ="my message" ;
    private DatabaseReference mStorageRef;
    private EditText location,date,time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_in);

   }

    public void onSubmit(View view) {

        mStorageRef= FirebaseDatabase.getInstance().getReference("https://bactrack-52c10.firebaseio.com/");        //.child("Wherever you want to store this data");
        location = findViewById(R.id.field1);
        time = findViewById(R.id.field2);
        date = findViewById(R.id.field3);
        String myloc  = location.getText().toString();
        String mytime = location.getText().toString();
        String mydate = location.getText().toString();
        if(!TextUtils.isEmpty(myloc)&&!TextUtils.isEmpty(mytime)&&!TextUtils.isEmpty(mydate))
        {
           //create a map
            HashMap< String,String> hm = new HashMap< String,String>();
            hm.put("location", myloc);
            hm.put("time", mytime);
            hm.put("date", mydate);
            mStorageRef.setValue(hm);

            mStorageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String value = dataSnapshot.getValue(String.class);
                   Log.d(TAG, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

    }
}
