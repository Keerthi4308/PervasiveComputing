package com.pervasive_computing.bactrackappv2;


import java.util.Date;

/**
 * Created by Cassie on 3/25/2018.
 */

public class LogSnip {
    String data;
    Date stamp;
    String loc;

    LogSnip(String data)
    {
        this.data = data;
        this.stamp = new Date();
        this.loc = "No Location Given";

    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }
}
