package com.ogettoweb.dealers.network;

import com.ogettoweb.dealers.models.Dealer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface DealersService {
    @Headers({
            "Accept: application/json"
    })
    @GET("api/rest/dealers")
    Call<List<Dealer>> getDealers();
}
