package com.example.widjet.main.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;
import com.example.widjet.main.MyWidget;

public class UpdateService extends Service {

    private final String TAG = "UpdateService";
    private boolean isRunningservice = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        isRunningservice = true;
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (isRunningservice) {
                    handler.postDelayed(this, 1000);

                    PendingIntent updatePendIntent = MyWidget.createUpdatePendIntent(getApplicationContext());
                    try {
                        updatePendIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        handler.post(runnable);

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        isRunningservice = false;
        super.onDestroy();
    }
}