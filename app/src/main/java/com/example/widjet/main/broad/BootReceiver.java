package com.example.widjet.main.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private final String TAG = "BootReceiver";
    public final static String SCREEN_BOOT = "android.intent.action.BOOT_COMPLETED"; // нужно обязательно прописывать в манифесте в интент фильтре!!!

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, " started");
       context.sendBroadcast(new Intent("android.appwidget.action.APPWIDGET_UPDATE"));
    }
}
