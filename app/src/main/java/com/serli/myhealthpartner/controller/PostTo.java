package com.serli.myhealthpartner.controller;

import com.serli.myhealthpartner.model.AccelerometerData;
import com.serli.myhealthpartner.model.CompleteData;
import com.serli.myhealthpartner.model.ProfileData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by nathan on 21/12/16.
 */

public interface PostTo {
    @POST("/data")
    Call<CompleteData> sendData (@Body CompleteData data);
}
