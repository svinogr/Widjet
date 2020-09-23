package com.example.widjet.main.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.widjet.main.MyWidget;
import com.example.widjet.main.broadcast.CatchScreeOffOnBootReceiver;
import com.example.widjet.main.broadcast.CatchTickIntentReceiver;

public class UpdateService extends Service implements UpdateWithClock, UpdateWithScreenResume {
    private final String TAG = "UpdateService";
    private CatchTickIntentReceiver catchTickIntentReceiver;
    private CatchScreeOffOnBootReceiver catchScreeOffOnBootReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: " + "service run");
        catchTickIntentReceiver = new CatchTickIntentReceiver();
        catchTickIntentReceiver.setUpdateWithClock(this);
        registerReceiver(catchTickIntentReceiver, new IntentFilter("android.intent.action.TIME_TICK"));

        catchScreeOffOnBootReceiver = new CatchScreeOffOnBootReceiver();
        catchScreeOffOnBootReceiver.setUpdateWithScreenResume(this);
        IntentFilter screenIntentFilter = new IntentFilter();
        screenIntentFilter.addAction("android.intent.action.SCREEN_ON");
        screenIntentFilter.addAction("android.intent.action.SCREEN_OFF");
        screenIntentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        registerReceiver(catchScreeOffOnBootReceiver, screenIntentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_REDELIVER_INTENT;
    }

    @Override
    public void updateWithClock() {
        sendIntentToUpdateWidget();
    }

    private void sendIntentToUpdateWidget() {
        PendingIntent updatePendIntent = MyWidget.createUpdatePendIntent(getApplicationContext());

        try {
            updatePendIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void screenOFF() {
        unregisterReceiver(catchTickIntentReceiver);
    }

    @Override
    public void screenON() {
        sendIntentToUpdateWidget();
        registerReceiver(catchTickIntentReceiver, new IntentFilter("android.intent.action.TIME_TICK"));
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(catchTickIntentReceiver);
        unregisterReceiver(catchScreeOffOnBootReceiver);
        super.onDestroy();
    }
}