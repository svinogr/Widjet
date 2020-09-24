package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private final String TAG = "BootReceiver";
    public final static String SCREEN_BOOT = "android.intent.action.BOOT_COMPLETED"; // нужно обязательно прописывать в манифесте в интент фильтре!!!

    @Override
    public void onReceive(Context context, Intent intent) {
     /*   Intent intentStartUpdateService = new Intent(context, UpdateService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentStartUpdateService);
        } else {
            context.startService(intentStartUpdateService);
        }*/
        Log.i(TAG, " started");
    }
}
