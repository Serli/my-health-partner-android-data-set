package com.serli.myhealthpartner.controller;

import android.content.Context;
import android.content.Intent;

import com.serli.myhealthpartner.AccelerometerService;
import com.serli.myhealthpartner.R;
import com.serli.myhealthpartner.model.AccelerometerDAO;
import com.serli.myhealthpartner.model.AccelerometerData;
import com.serli.myhealthpartner.model.CompleteData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Controller of the main view.
 */
public class MainController {

    private Context context;
    private AccelerometerDAO dao;

    /**
     * Build a new main controller with the given context.
     *
     * @param context The context of the attached view.
     */
    public MainController(Context context) {
        this.context = context;

        dao = new AccelerometerDAO(context);

        dao.open();
    }

    /**
     * Start the acquisition of the accelerometer data.
     * It will start the service {@link AccelerometerService} with the given parameter.
     * @param duration The duration of the acquisition.
     * @param activity The sport activity performed during the acquisition.
     */
    public void startAcquisition(long duration, int activity) {
        if (!AccelerometerService.isRunning()) {
            Intent intent = new Intent(context, AccelerometerService.class);
            intent.putExtra("duration", duration);
            intent.putExtra("activity", activity);
            context.startService(intent);
        }
    }

    /**
     * Stop the acquisition if the service {@link AccelerometerService} is running.
     */
    public void stopAcquisition() {
        context.stopService(new Intent(context, AccelerometerService.class));
    }

    /**
     * Send the stored data to the server, then delete it.
     */
    public void sendAcquisition() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.valueOf("http://192.168.42.168:8080/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostTo post = retrofit.create(PostTo.class);
        ProfileController controllerProfile = new ProfileController(context);

        ArrayList<AccelerometerData> data = dao.getData();

        CompleteData cd = new CompleteData();
        cd.setProfileData(controllerProfile.getProfile());
        cd.setAccelerometerData(data);

        Call<CompleteData> callData = post.sendData(cd);
        callData.enqueue(new Callback<CompleteData>() {
            @Override
            public void onResponse(Call<CompleteData> call, Response<CompleteData> response) {
                System.out.println("Send Acquisition OK !");
            }

            @Override
            public void onFailure(Call<CompleteData> call, Throwable t) {
                System.out.println("Send Acquisition KO !");
                t.printStackTrace();
            }
        });
    }

    /**
     * Delete the stored data.
     */
    public void DeleteAcquisition() {
        dao.deleteData();
    }

    public ArrayList<AccelerometerData> getData() {
        ArrayList<AccelerometerData> data = dao.getData();
        return data;
    }

    @Override
    protected void finalize() throws Throwable {
        dao.close();
        super.finalize();
    }
}
