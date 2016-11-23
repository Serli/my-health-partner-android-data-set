package com.serli.myhealthpartner.controller;

import android.content.Context;

import com.serli.myhealthpartner.model.ProfileDAO;
import com.serli.myhealthpartner.model.ProfileData;

public class ProfileController {

    private Context context;

    private ProfileDAO dao;

    private ProfileData profile;

    public ProfileController(Context context) {
        this.context = context;

        dao = new ProfileDAO(context);

        dao.open();
        profile = dao.getProfile();
        dao.close();
    }

    public ProfileData getProfile() {
        return profile;
    }

    public void setProfile(ProfileData data) {
        dao.open();
        dao.addEntry(data);
        dao.close();
    }


}
