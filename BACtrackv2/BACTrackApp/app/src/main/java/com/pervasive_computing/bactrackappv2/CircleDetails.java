package com.pervasive_computing.bactrackappv2;
/*
  Created by Keerthi on 11/27/2018.
 */

class CircleDetails {
    public String name;
    public String location;
    public String date;
    public String volunteer;

    public CircleDetails(){

    }


    public CircleDetails(String name, String location, String date, String volunteer) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.volunteer=volunteer;
    }


    public String getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(String volunteer) {
        this.volunteer = volunteer;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getName() {
        return name;
    }
}
