package com.example.widjet.main.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreeOffOnReceiver extends BroadcastReceiver {
    private final String TAG = "CatchScreeOffOn";
    public final static String SCREEN_ON = "android.intent.action.SCREEN_ON";
    public final static String SCREEN_OFF = "android.intent.action.SCREEN_OFF";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: " + intent.getAction());

        switch (intent.getAction()) {
            case SCREEN_ON:
                Log.i(TAG, "onReceive: screen ON");
                context.sendBroadcast(new Intent("android.appwidget.action.APPWIDGET_UPDATE"));
                break;
            case SCREEN_OFF:
                Log.i(TAG, "onReceive: screen OFF");
                break;
        }

    }
}
