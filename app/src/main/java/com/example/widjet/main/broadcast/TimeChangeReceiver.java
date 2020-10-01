package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.service.UpdateService;

public class TimeChangeReceiver extends BroadcastReceiver {
    private final String TAG = "TimeChangeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: ");
        context.startService(UpdateService.getIntentStartService(context));
    }
}
