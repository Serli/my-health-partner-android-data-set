package com.serli.myhealthpartner.model;

/**
 * Contains the data that we collect with the smartphone's Accelerometer
 */
public class AccelerometerData {

    private long timestamp;
    private float x, y, z;
    private int activity;

    /**
     * @return The data's timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp The data's timestamp
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * X position getter
     *
     * @return The data's X position
     */
    public float getX() {
        return x;
    }

    /**
     * @param x The data's X position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return The data's Y position
     */
    public float getY() {
        return y;
    }

    /**
     * @param y The data's Y position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return The data's Z position
     */
    public float getZ() {
        return z;
    }

    /**
     * @param z The data's Z position
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * @return The data's activity type
     */
    public int getActivity() {
        return activity;
    }

    /**
     * @param activity The data's activity type
     */
    public void setActivity(int activity) {
        this.activity = activity;
    }

}
