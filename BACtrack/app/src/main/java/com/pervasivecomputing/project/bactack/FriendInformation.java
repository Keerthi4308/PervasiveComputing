package com.pervasivecomputing.project.bactack;

import android.provider.ContactsContract;
import android.widget.ListView;

class FriendInformation {
    private String name,location;


    public FriendInformation(String name, String location) {
        this.location = location;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
