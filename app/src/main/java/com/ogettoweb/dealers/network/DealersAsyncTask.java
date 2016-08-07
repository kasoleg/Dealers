package com.ogettoweb.dealers.network;

import android.app.Activity;

import com.ogettoweb.dealers.models.Dealer;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class DealersAsyncTask extends BaseAsyncTask<Void, Void, List<Dealer>> {
    public DealersAsyncTask(Activity activity) {
        super(activity, "Loading...", true);
        this.activity = activity;
    }

    @Override
    protected List<Dealer> doInBackground(Void... params) {
        try {
            Call<List<Dealer>> call = DealersApi.getInstance().dealers().getDealers();
            List<Dealer> dealers = call.execute().body();
            return dealers;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
