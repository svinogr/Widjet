package com.example.widjet.main.broadcast;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeBroadcast extends BootReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TimeBroadcast", "onReceive: " + intent.getAction());
        super.onReceive(context, intent);
    }
}
