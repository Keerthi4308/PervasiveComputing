package com.pervasive_computing.bactrackappv2;


/*
  Created by Keerthi on 11/27/2018.
 */

class Userprofile {

    public String memberBAC;
    public String address;
    public String reachedHome; //Reached home or not
    public String alert; //indicate other members that help is required
    public String fullName;
    public Long phone;


    public Userprofile(){

    }

    public Userprofile(String memberBAC, String address,String reachedHome,String alert, String fullName,Long phone) {
        this.memberBAC = memberBAC;
        this.address = address;
        this.reachedHome=reachedHome;
        this.alert = alert;
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getMemberBAC() {
        return memberBAC;
    }

    public void setMemberBAC(String memberBAC) {
        this.memberBAC = memberBAC;
    }

    public String getReachedHome() {
        return reachedHome;
    }

    public void setReachedHome(String reachedHome) {
        this.reachedHome = reachedHome;
    }


    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }



}

