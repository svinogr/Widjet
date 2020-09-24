package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.service.UpdateWithClock;

public class TimeChangeReceiver extends BroadcastReceiver {
private final String TAG = "CatchTickIntentReceiver";
public final static String TIME_SET = "android.intent.action.TIME_SET";
public static final String TIME_TICK = "android.intent.action.TIME_TICK";

private UpdateWithClock updateWithClock;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: is intreface update " + String.valueOf(updateWithClock != null));

        switch (intent.getAction()) {
            case TIME_SET:
                Log.i(TAG, "onReceive: " + "Time change");
                updateWithClock.updateWithClock();
                break;
            case TIME_TICK:
                updateWithClock.updateWithClock();
                Log.i(TAG, "onReceive: " + "Tik Tak");
                break;

        }
    }

    public void setUpdateWithClock(UpdateWithClock updateWithClock) {
        this.updateWithClock = updateWithClock;
    }
}
