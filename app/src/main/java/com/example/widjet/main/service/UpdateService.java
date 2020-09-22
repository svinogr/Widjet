package com.example.widjet.main.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.widjet.main.MyWidget;

public class UpdateService extends Service {

    private final String TAG = "UpdateService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand: " + intent.getAction());
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (true) {
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




       /* calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0); // facking calendar need set millsecond every time
        // TODO for testing in code удалить-----
        //    calendar.set(Calendar.YEAR, 2023);
        // TODO --------------
        Date time = calendar.getTime();

        System.out.println(simpleDateFormat.format(time));
        System.out.println(time.getTime());

        PrazdnikDTO prazdnik = getPrazdnik(time);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.timeImg, getTime());
        views.setTextViewText(R.id.dateImg, simpleDateFormat.format(time));
        views.setTextViewText(R.id.textImg, prazdnik.getName());
        System.out.println(prazdnik.getImg());

        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));

        appWidgetManager.updateAppWidget(appWidgetId, views);*/
    }



    /*    System.out.println("in servuie intent action "+ intent.getAction());
        System.out.println("start if " + startId);
        RemoteViews views = updateTimeWidget(this);
        ComponentName widget = new ComponentName(this, MyWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        appWidgetManager.updateAppWidget(widget, views);*/
    //  stopSelf();
       /* final String action = intent.getAction();



        return START_REDELIVER_INTENT;
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(true) {
                    handler.postDelayed(this, 1000);

                    System.out.println("onUpdate getACtion intent " + action);

                }
            }
        };
        handler.post(runnable);

        return super.onStartCommand(intent, flags, startId);
    }

    private RemoteViews updateTimeWidget(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        views.setTextViewText(R.id.timeImg,
                calendar.get(Calendar.HOUR_OF_DAY) + " : " + (calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE)));
        views.setTextViewText(R.id.dateImg, simpleDateFormat.format(calendar.getTime()));

        return views;


       /* calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0); // facking calendar need set millsecond every time
        // TODO for testing in code удалить-----
        //    calendar.set(Calendar.YEAR, 2023);
        // TODO --------------
        Date time = calendar.getTime();

        System.out.println(simpleDateFormat.format(time));
        System.out.println(time.getTime());

        PrazdnikDTO prazdnik = getPrazdnik(time);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.timeImg, getTime());
        views.setTextViewText(R.id.dateImg, simpleDateFormat.format(time));
        views.setTextViewText(R.id.textImg, prazdnik.getName());
        System.out.println(prazdnik.getImg());

        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));

        appWidgetManager.updateAppWidget(appWidgetId, views);*/

