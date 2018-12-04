package com.pervasive_computing.bactrackappv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomMemberListAdapter extends BaseAdapter {
//
    Context mContext;
    int mResource;
    private final ArrayList mData;

    public CustomMemberListAdapter(Context context, int resource, HashMap<String, String> data) {
        //super(context, resource, data);
        mData = new ArrayList();
        mData.addAll(data.entrySet());
        mContext = context;
        mResource=resource;

    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Map.Entry<String,String> mview = getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
        }
        TextView baclevel = convertView.findViewById(R.id.member_bac_level);
        TextView  name= convertView.findViewById(R.id.member_name);
                if (mview != null) {
                    name.setText(mview.getValue());
                    baclevel.setText(mview.getKey());
        }

        return convertView;
    }

}
