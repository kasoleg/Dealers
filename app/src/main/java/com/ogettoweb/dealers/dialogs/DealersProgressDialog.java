package com.ogettoweb.dealers.dialogs;

import android.content.Context;

public class DealersProgressDialog extends android.app.ProgressDialog {
    public DealersProgressDialog(Context context, String message) {
        super(context);
        setMessage(message);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }


    @Override
    public void hide() {
        if (isShowing()) {
            super.hide();
        }
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }


    }
}