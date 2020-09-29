package com.example.widjet.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainBroadcastReceiver extends BroadcastReceiver {
    public static final String REGISTER_RECEIVER = "register_receiver";
    public static final String UN_REGISTER_RECEIVER = "un_register_receiver";

    private Context context;
    private TimeChangeReceiver timeChangeReceiver;
    private ScreeOffOnReceiver screeOffOnReceiver;
    private BootReceiver bootReceiver;

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
        context.unregisterReceiver(timeChangeReceiver);
        context.unregisterReceiver(screeOffOnReceiver);
        context.unregisterReceiver(bootReceiver);
    }

    private void registerReceiver() {
        timeChangeReceiver = new TimeChangeReceiver();
        IntentFilter timeIntentFilter = new IntentFilter();
        timeIntentFilter.addAction(TimeChangeReceiver.TIME_SET);
        timeIntentFilter.addAction(TimeChangeReceiver.TIME_TICK);
        context.registerReceiver(timeChangeReceiver, timeIntentFilter);

        screeOffOnReceiver = new ScreeOffOnReceiver();
        IntentFilter screenIntentFilter = new IntentFilter();
        screenIntentFilter.addAction(ScreeOffOnReceiver.SCREEN_ON);
        screenIntentFilter.addAction(ScreeOffOnReceiver.SCREEN_OFF);
        context.registerReceiver(screeOffOnReceiver, screenIntentFilter);

        bootReceiver = new BootReceiver();
        context.registerReceiver(bootReceiver, new IntentFilter(BootReceiver.SCREEN_BOOT));
    }
}
