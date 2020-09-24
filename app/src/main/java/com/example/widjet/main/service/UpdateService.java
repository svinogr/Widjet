package com.example.widjet.main.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.widjet.main.MyWidget;

public class UpdateService extends Service {
    private final String TAG = "UpdateService";

    public static Intent getIntentStartService(Context context) {
        return new Intent(context, UpdateService.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: " + "service run");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        Log.i(TAG, "sendIntentToUpdateWidget: ");
        getApplicationContext().sendBroadcast(new Intent(MyWidget.UPDATE_WIDGET));

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}