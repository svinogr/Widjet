package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.service.UpdateService;

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
                context.startService(UpdateService.getIntentStartService(context));
                break;
            case SCREEN_OFF:
                Log.i(TAG, "onReceive: screen OFF");
                break;
        }
    }
}
