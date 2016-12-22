package com.serli.myhealthpartner.controller;

import android.content.Context;
import android.content.Intent;

import com.serli.myhealthpartner.AccelerometerService;
import com.serli.myhealthpartner.model.AccelerometerDAO;
import com.serli.myhealthpartner.model.AccelerometerData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void sendAcquisition(PostTo post) {
        List<AccelerometerData> data = dao.getData();
        Call<List<AccelerometerData>> callData = post.sendAccelerometerData(data);
        callData.enqueue(new Callback<List<AccelerometerData>>() {
            @Override
            public void onResponse(Call<List<AccelerometerData>> call, Response<List<AccelerometerData>> response) {
                // SEND ACQUISITION OK !
            }

            @Override
            public void onFailure(Call<List<AccelerometerData>> call, Throwable t) {
                // SEND ACQUISITION KO !
            }
        });
    }

    /**
     * Delete the stored data.
     */
    public void DeleteAcquisition() {
        dao.deleteData();
    }

    public List<AccelerometerData> getData() {
        List<AccelerometerData> data = dao.getData();
        return data;
    }

    @Override
    protected void finalize() throws Throwable {
        dao.close();
        super.finalize();
    }
}
