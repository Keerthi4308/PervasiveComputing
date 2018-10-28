package com.pervasivecomputing.project.bactack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<FriendInformation> {

    Context mContext;
    int mResource;


    public CustomAdapter(Context context, int resource, ArrayList<FriendInformation> data) {
        super(context, resource, data);
        mContext = context;
        mResource=resource;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //FriendInformation dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        //final View result;

        String name=getItem(position).getName();
        String location=getItem(position).getLocation();
        FriendInformation Finfo=new FriendInformation(name,location);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
      // Return the completed view to render on screen
        TextView tvName=convertView.findViewById(R.id.textView1);
        TextView tvLocation=convertView.findViewById(R.id.textView2);

        tvName.setText(name);
        tvLocation.setText(location);
      return convertView;
    }
}