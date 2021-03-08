package com.cos.phoneapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PhoneService {


    @GET("phone")
    Call<CMRespDto<List<Phone>>> findAll();

    @POST("phone")
    Call<PhoneSaveReqDto> save(@Body PhoneSaveReqDto phoneSaveReqDto);

    @PUT("phone/{id}")
    Call<PhoneSaveReqDto> update(@Path("id") Long id, @Body PhoneSaveReqDto phoneSaveReqDto);

    @DELETE("phone/{id}")
    Call<CMRespDto> deleteById(@Path("id") long id);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://172.30.1.22:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
