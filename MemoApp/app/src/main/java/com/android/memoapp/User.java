package com.android.memoapp;

import java.io.Serializable;

public class User implements Serializable {
    private String Name;
    private String Email;
    private String Phno;

    public User(String name, String email, String phno) {
        Name = name;
        Email = email;
        Phno = phno;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhno() {
        return Phno;
    }

    public void setPhno(String phno) {
        Phno = phno;
    }
}
