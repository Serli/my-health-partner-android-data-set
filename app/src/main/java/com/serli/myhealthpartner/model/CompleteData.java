package com.serli.myhealthpartner.model;

import java.util.List;

/**
 * Created by nathan on 06/01/17.
 */

public class CompleteData {
    ProfileData pd;
    List<AccelerometerData> ad;

    public ProfileData getProfileData(){
        return pd;
    }

    public void setProfileData(ProfileData pd){
        this.pd = pd;
    }

    public List<AccelerometerData> getAccelerometerData(){
        return ad;
    }

    public void setAccelerometerData(List<AccelerometerData> ad) {
        this.ad = ad;
    }
}
