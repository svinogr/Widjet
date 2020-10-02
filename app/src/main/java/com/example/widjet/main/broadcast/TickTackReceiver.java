package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.MyWidget;

public class TickTackReceiver extends BroadcastReceiver {
private final String TAG = "TickTackReceiver";

public static final String TIME_TICK = "android.intent.action.TIME_TICK";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: Tick Tack");
        context.sendBroadcast(new Intent(MyWidget.UPDATE_WIDGET));
    }
}
