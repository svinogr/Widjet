package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.service.UpdateService;

public class TimeChangeReceiver extends BroadcastReceiver {
private final String TAG = "CatchTickIntentReceiver";
public final static String TIME_SET = "android.intent.action.TIME_SET";
public static final String TIME_TICK = "android.intent.action.TIME_TICK";

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()) {
            case TIME_SET:
                Log.i(TAG, "onReceive: " + "Time change");

                break;
            case TIME_TICK:

                Log.i(TAG, "onReceive: " + "Tik Tak");
                break;
        }
        context.startService(UpdateService.getIntentStartService(context));
    }
}
