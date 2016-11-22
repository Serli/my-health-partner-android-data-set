package com.serli.myhealthpartner.controller;

import android.content.Context;
import android.content.Intent;

import com.serli.myhealthpartner.AccelerometerService;
import com.serli.myhealthpartner.model.AccelerometerDAO;
import com.serli.myhealthpartner.model.AccelerometerData;

import java.util.List;

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
    }

    /**
     * Start the acquisition of the accelerometer data.
     * It will start the service {@link AccelerometerService} with the given parameter.
     * @param duration The duration of the acquisition.
     * @param activity The sport activity performed during the acquisition.
     */
    public void startAcquisition(int duration, int activity) {
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
        dao.open();
        List<AccelerometerData> datas = dao.getDatas();
        // TODO : implement client-server communication.
        dao.close();
    }

    /**
     * Delete the stored data.
     */
    public void DeleteAcquisition() {
        dao.open();
        dao.deleteDatas();
        dao.close();
    }

    @Override
    protected void finalize() throws Throwable {
        dao.close();
        super.finalize();
    }
}
