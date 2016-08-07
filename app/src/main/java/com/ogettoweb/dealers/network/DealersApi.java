package com.ogettoweb.dealers.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DealersApi {
    private static DealersApi singleton;
    private Retrofit retrofit;

    private DealersApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://mob.rockwool.oggettoweb.com/")
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static DealersApi getInstance() {
        if (singleton == null) {
            singleton = new DealersApi();
        }
        return singleton;
    }

    public DealersService dealers() {
        return retrofit.create(DealersService.class);
    }

}
