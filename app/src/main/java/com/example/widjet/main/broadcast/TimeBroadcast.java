package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.MyWidget;

public class TimeBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TimeBroadcast", "onReceive: " + intent.getAction());
        context.sendBroadcast(new Intent(MyWidget.UPDATE_WIDGET));

    }
}
