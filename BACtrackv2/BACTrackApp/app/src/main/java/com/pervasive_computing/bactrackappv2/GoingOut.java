package com.pervasive_computing.bactrackappv2;

import java.util.Date;

/**
 * Created by Cassie on 3/25/2018.
 */

public class GoingOut {
    Date stamp;
    String drinkingTonight;
    String gettingHome;

    GoingOut(String drinkingTonight, String gettingHome)
    {
        this.drinkingTonight = drinkingTonight;
        this.gettingHome = gettingHome;
        stamp = new Date();
    }

    GoingOut()
    {
        stamp = new Date();
    }

    public Date getStamp() {
        return stamp;
    }

    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public String getDrinkingTonight() {
        return drinkingTonight;
    }

    public void setDrinkingTonight(String drinkingTonight) {
        this.drinkingTonight = drinkingTonight;
    }

    public String getGettingHome() {
        return gettingHome;
    }

    public void setGettingHome(String gettingHome) {
        this.gettingHome = gettingHome;
    }
}
