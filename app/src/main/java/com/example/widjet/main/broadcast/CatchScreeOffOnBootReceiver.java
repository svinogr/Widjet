package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.service.UpdateWithScreenResume;

public class CatchScreeOffOnBootReceiver extends BroadcastReceiver {
    private final String TAG = "CatchScreeOffOnBoot";
    private final String SCREEN_ON = "android.intent.action.SCREEN_ON";
    private final String SCREEN_OFF = "android.intent.action.SCREEN_OFF";
    private final String SCREEN_BOOT = "android.intent.action.BOOT_COMPLETED";

    private UpdateWithScreenResume updateWithScreenResume;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: " + intent.getAction());

        switch (intent.getAction()) {
            case SCREEN_ON:
            case SCREEN_BOOT:
                updateWithScreenResume.screenON();
                break;
            case SCREEN_OFF:
                updateWithScreenResume.screenOFF();
                break;
        }
    }

    public void setUpdateWithScreenResume(UpdateWithScreenResume updateWithScreenResume) {
        this.updateWithScreenResume = updateWithScreenResume;
    }
}
