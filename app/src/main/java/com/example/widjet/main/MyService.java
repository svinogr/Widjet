package com.example.widjet.main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextClock;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TextClock textClock = new TextClock(getApplicationContext());
        textClock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("TAG", "afterTextChanged: ");
            }
        });

        return super.onStartCommand(intent, flags, startId);



    }
}
