package com.example.widjet.main.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import com.example.widjet.R;
import com.example.widjet.main.MyWidget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UpdateService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


/*    public int ы(Intent intent, int flag,  int startId) {
        super.onStart(intent, startId);
        RemoteViews views = updateTimeWidget(this);
        ComponentName widget = new ComponentName(this, MyWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        appWidgetManager.updateAppWidget(widget, views);
      //  stopSelf();

        return START_REDELIVER_INTENT;
    }*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("in servuie intent action "+ intent.getAction());
        System.out.println("start if " + startId);
        RemoteViews views = updateTimeWidget(this);
        ComponentName widget = new ComponentName(this, MyWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        appWidgetManager.updateAppWidget(widget, views);
        //  stopSelf();

      //  return START_REDELIVER_INTENT;
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
    }
}