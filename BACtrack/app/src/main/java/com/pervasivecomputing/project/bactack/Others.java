package com.pervasivecomputing.project.bactack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Others extends AppCompatActivity {

    //private static final String TAG = "my message:" ;
    //private DatabaseReference mStorageRef;
    ArrayList<FriendInformation> dataModel;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);

//        mStorageRef= FirebaseDatabase.getInstance().getReference();
//        // Read from the database
//        mStorageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for(DataSnapshot dbID: dataSnapshot.getChildren())
//                {
//                    if(dbID.=="5/10/18"){
//                        showData(dbID);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
        listView=(ListView)findViewById(R.id.list_view);
        dataModel= new ArrayList<>();

        FriendInformation john = new FriendInformation("John","Chicago");
        FriendInformation priya = new FriendInformation("priya","10th Street");
        FriendInformation ravi= new FriendInformation("ravi","China");
        FriendInformation nathan = new FriendInformation("nathan","Seattle");
        FriendInformation ben = new FriendInformation("ben","Indianapolis");
        FriendInformation joseph = new FriendInformation("joseph","Bloomington");
        FriendInformation navya= new FriendInformation("navya","Taj");

        dataModel.add(john);
        dataModel.add(priya);
        dataModel.add(ravi);
        dataModel.add(nathan);
        dataModel.add(ben);
        dataModel.add(joseph);
        dataModel.add(navya);

        adapter= new CustomAdapter(this,R.layout.new_adaptar_view,dataModel);
        listView.setAdapter(adapter);
    }

    private void showData(DataSnapshot dataSnapshot) {
//        listView=(ListView)findViewById(R.id.list_view);
//        dataModel= new ArrayList<>();

//        for(DataSnapshot ds : dataSnapshot.getChildren()){
//            FriendInformation fInfo=new FriendInformation();
//            fInfo.setName(ds.getValue(FriendInformation.class).getName());
////            fInfo.setName(ds.getValue(FriendInformation.class).getPhone());
//            fInfo.setLocation(ds.getValue(FriendInformation.class).getLocation());
//
//            dataModel.add(fInfo);
//
//        }


    }
}

