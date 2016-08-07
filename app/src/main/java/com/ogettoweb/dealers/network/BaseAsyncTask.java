package com.ogettoweb.dealers.network;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.dialogs.DealersProgressDialog;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    protected Activity activity;
    DealersProgressDialog progressDialog;
    AlertDialog networkDialog;

    public BaseAsyncTask(Activity activity, String message, boolean isShow) {
        this.activity = activity;
        progressDialog = new DealersProgressDialog(activity, message);
    }

    public BaseAsyncTask(Activity activity, boolean isShow) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isOnline(activity)) {
            progressDialog.show();
        } else {
            cancel(true);
            showNetworkDialog();
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        progressDialog.hide();
    }

    private void showNetworkDialog() {
        if (networkDialog == null) {
            networkDialog = new AlertDialog.Builder(activity)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.unavailable_network)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    })
                    .setNegativeButton(activity.getString(R.string.exit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            activity.finish();
                        }
                    })
                    .setCancelable(false)
                    .show();
        } else {
            if (!networkDialog.isShowing()) {
                networkDialog.show();
            }
        }
    }

    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
