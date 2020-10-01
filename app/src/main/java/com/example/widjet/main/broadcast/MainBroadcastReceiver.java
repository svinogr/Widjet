package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class MainBroadcastReceiver extends BroadcastReceiver {
    public static final String REGISTER_RECEIVER = "register_receiver";
    public static final String UN_REGISTER_RECEIVER = "un_register_receiver";

    private Context context;
    private TickTackReceiver tickTackReceiver;
    private ScreeOffOnReceiver screeOffOnReceiver;
    private BootReceiver bootReceiver;
    private final String TAG = "MainBroadcastReceiver";

    public MainBroadcastReceiver(Context context) {
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (REGISTER_RECEIVER.equals(intent.getAction())) {
            registerReceiver();
        }

        if (UN_REGISTER_RECEIVER.equals(intent.getAction())) {
            unregisterReceiver();
        }
    }

    private void unregisterReceiver() {
        Log.i(TAG, "unregisterReceiver: ");
        context.unregisterReceiver(tickTackReceiver);
        context.unregisterReceiver(screeOffOnReceiver);
        //context.unregisterReceiver(bootReceiver);
    }

    private void registerReceiver() {
        Log.i(TAG, "registerReceiver: ");
        tickTackReceiver = new TickTackReceiver();
        IntentFilter timeIntentFilter = new IntentFilter();
        timeIntentFilter.addAction(TickTackReceiver.TIME_TICK);
        context.registerReceiver(tickTackReceiver, timeIntentFilter);

        screeOffOnReceiver = new ScreeOffOnReceiver();
        IntentFilter screenIntentFilter = new IntentFilter();
        screenIntentFilter.addAction(ScreeOffOnReceiver.SCREEN_ON);
        screenIntentFilter.addAction(ScreeOffOnReceiver.SCREEN_OFF);
        context.registerReceiver(screeOffOnReceiver, screenIntentFilter);
        // two receiver is defined in manifest
    }
}
