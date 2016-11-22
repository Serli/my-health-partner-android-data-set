package com.serli.myhealthpartner;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;

import com.serli.myhealthpartner.model.AccelerometerDAO;

/**
 * This service store the accelerometer data for the duration specified in the intent.<br/>
 * The intent given in the start command must contain 2 extra data :<br/>
 * &nbsp;&nbsp;&nbsp;<b>duration</b> : an long which specify the duration of the acquisition.<br/>
 * &nbsp;&nbsp;&nbsp;<b>activity</b> : an int which specify the sport activity performed during the acquisition.<br/>
 */
public class AccelerometerService extends Service {

    private static boolean isRunning = false;

    public static final String BROADCAST_START_ACTION = "com.serli.AccelerometerService.START";
    public static final String BROADCAST_STOP_ACTION = "com.serli.accelerometer.STOP";

    private AccelerometerDAO dao;

    private int activity;

    private Handler handler = new Handler();

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long timestamp = sensorEvent.timestamp * 1000000;
            dao.addEntry(x, y, z, timestamp);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    public void onCreate() {
        isRunning = true;
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        dao = new AccelerometerDAO(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        dao.open();
        activity = intent.getIntExtra("activity", 0);
        startAcquisition(intent.getLongExtra("duration", 0));
        return START_NOT_STICKY;
    }

    /**
     * Start the acquisition of the accelerometer data for the duration given.
     *
     * @param duration The duration of the acquisition.
     */
    private void startAcquisition(long duration) {
        sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sendBroadcast(new Intent(BROADCAST_START_ACTION));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                AccelerometerService.this.stopSelf();
            }
        }, duration);

    }

    /**
     * Stop the acquisition of the accelerometer data.
     */
    private void stopAcquisition() {
        sensorManager.unregisterListener(sensorEventListener, accelerometerSensor);
        sendBroadcast(new Intent(BROADCAST_STOP_ACTION));
    }

    @Override
    public void onDestroy() {
        stopAcquisition();
        dao.close();
        isRunning = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    /**
     * Tell if the service is already running.
     *
     * @return true if the service is already running.
     */
    public static boolean isRunning() {
        return isRunning;
    }

}
