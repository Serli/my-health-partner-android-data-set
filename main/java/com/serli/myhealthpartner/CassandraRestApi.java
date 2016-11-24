package com.serli.myhealthpartner;

/**
 * Created by nathan on 24/11/16.
 */

import com.serli.myhealthpartner.model.AccelerometerData;

import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by natha_000 on 17/11/2016.
 */

public interface CassandraRestApi {
    @POST("/acceleration")
    public Response sendAccelerationValues(@Body AccelerometerData accel);
}
