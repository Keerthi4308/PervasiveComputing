package com.pervasive_computing.bactrackappv2;

class CircleDetails {
    public String name;
    public String location;
    public String date;

    public CircleDetails(){

    }


    public CircleDetails(String name, String location, String date) {
        this.name = name;
        this.location = location;
        this.date = date;
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
