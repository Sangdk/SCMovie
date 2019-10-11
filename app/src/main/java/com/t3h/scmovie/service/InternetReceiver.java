package com.t3h.scmovie.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;

public class InternetReceiver extends BroadcastReceiver {
    private OnInternetConnectListener mCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        NetworkInfo info = extras.getParcelable("networkInfo");
        NetworkInfo.State state = info.getState();
        mCallback = (OnInternetConnectListener) context;

        if (state == NetworkInfo.State.CONNECTED) {
            mCallback.onConnected();
        } else {
            mCallback.onDisconnected();
        }
    }

    public interface OnInternetConnectListener {
        void onConnected();

        void onDisconnected();
    }
}