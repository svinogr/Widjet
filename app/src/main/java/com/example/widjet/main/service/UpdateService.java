package com.example.widjet.main.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.widjet.main.MyWidget;
import com.example.widjet.main.broadcast.ScreeOffOnReceiver;
import com.example.widjet.main.broadcast.TimeChangeReceiver;

public class UpdateService extends Service implements UpdateWithClock, UpdateWithScreenResume {
    private final String TAG = "UpdateService";
    private TimeChangeReceiver timeChangeReceiver;
    private ScreeOffOnReceiver catchScreeOffOnBootReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: " + "service run");
        timeChangeReceiver = new TimeChangeReceiver();
        timeChangeReceiver.setUpdateWithClock(this);
        IntentFilter timeIntentFilter = new IntentFilter();
        timeIntentFilter.addAction(TimeChangeReceiver.TIME_SET);
        timeIntentFilter.addAction(TimeChangeReceiver.TIME_TICK);
        registerReceiver(timeChangeReceiver, timeIntentFilter);

        catchScreeOffOnBootReceiver = new ScreeOffOnReceiver();
        catchScreeOffOnBootReceiver.setUpdateWithScreenResume(this);
        IntentFilter screenIntentFilter = new IntentFilter();
        screenIntentFilter.addAction(ScreeOffOnReceiver.SCREEN_ON);
        screenIntentFilter.addAction(ScreeOffOnReceiver.SCREEN_OFF);
        registerReceiver(catchScreeOffOnBootReceiver, screenIntentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: ");
        return START_REDELIVER_INTENT;
    }

    @Override
    public void updateWithClock() {
        sendIntentToUpdateWidget();
    }

    private void sendIntentToUpdateWidget() {
        Log.i(TAG, "sendIntentToUpdateWidget: ");
        getApplicationContext().sendBroadcast(new Intent(MyWidget.UPDATE_WIDGET));
    /*    PendingIntent updatePendIntent = MyWidget.createUpdatePendIntent(getApplicationContext());

        try {
            updatePendIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void screenOFF() {
        Log.i(TAG, "screenOFF: in update method");
        unregisterReceiver(timeChangeReceiver);
    }

    @Override
    public void screenON() {
        sendIntentToUpdateWidget();
        registerReceiver(timeChangeReceiver, new IntentFilter(TimeChangeReceiver.TIME_TICK));
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy: ");
        unregisterReceiver(timeChangeReceiver);
        unregisterReceiver(catchScreeOffOnBootReceiver);
        super.onDestroy();
    }
}