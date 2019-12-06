package com.example.androidverifier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiInterface {

    //@Headers( "Content-Type: application/json; charset=utf-8")
    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST("/api/process")
    Call<JsonElement> getProcess(@Body ConfigObject configObject);
    //Call<JsonElement> getProcess(@Body JSONObject jsonObject);

    @GET("/api")
    Call<JsonElement> getapi();

    @POST("/api/pro")
    Call<JsonElement>getRequest(@Body String jsonObject);
}
