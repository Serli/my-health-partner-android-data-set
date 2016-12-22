package com.serli.myhealthpartner.controller;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.serli.myhealthpartner.model.AccelerometerData;
import com.serli.myhealthpartner.model.ProfileDAO;
import com.serli.myhealthpartner.model.ProfileData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Controller for the profile.
 */
public class ProfileController {

    private ProfileDAO dao;

    private ProfileData profile;

    /**
     * Build a new profile controller with the given context.
     *
     * @param context The context of the attached view.
     */
    public ProfileController(Context context) {
        dao = new ProfileDAO(context);

        dao.open();

        profile = dao.getProfile();
    }

    /**
     * @return The profile stored in the database or null if none exist.
     */
    public ProfileData getProfile() {
        if (profile == null) {
            profile = dao.getProfile();
        }
        return profile;
    }

    public void sendProfile(PostTo post) {
        Call<ProfileData> callProfile = post.sendProfileData(getProfile());
        callProfile.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                // SEND PROFILE OK !
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                // SEND PROFILE KO !
            }
        });
    }

    /**
     * Set the profile in the database.
     * @param data The {@link ProfileData} containing the profile.
     */
    public void setProfile(ProfileData data) {
        dao.addEntry(data);
    }

    @Override
    protected void finalize() throws Throwable {
        dao.close();
        super.finalize();
    }
}
