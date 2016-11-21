package com.serli.myhealthpartner;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.serli.myhealthpartner.model.AccelerometerDAO;

public class AccelerometerService extends Service {

    private AccelerometerDAO dao;

    @Override
    public void onCreate() {
        // TODO
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void startAcquisition() {
        // TODO
    }

    private void stopAcquisition() {
        // TODO
    }
}
