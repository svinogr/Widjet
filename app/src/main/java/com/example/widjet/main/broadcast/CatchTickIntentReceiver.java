package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.widjet.main.service.UpdateWithClock;

public class CatchTickIntentReceiver extends BroadcastReceiver {
private String TAG = "CatchTickIntentReceiver";
private UpdateWithClock updateWithClock;
    @Override
    public void onReceive(Context context, Intent intent) {
       if ("android.intent.action.TIME_TICK".equals(intent.getAction())) {
           Log.i(TAG, "onReceive: " + "CatchTickIntentReceiver");
           updateWithClock.updateWithClock();
       }
    }

    public UpdateWithClock getUpdateWithClock() {
        return updateWithClock;
    }

    public void setUpdateWithClock(UpdateWithClock updateWithClock) {
        this.updateWithClock = updateWithClock;
    }
}
