package com.example.widjet.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.widjet.R;
import com.example.widjet.main.broad.BootReceiver;
import com.example.widjet.main.broad.ScreeOffOnReceiver;
import com.example.widjet.main.broad.TimeChangeReceiver;
import com.example.widjet.main.converter.DateConverter;
import com.example.widjet.main.dao.DataDao;
import com.example.widjet.main.dao.PrazdnikDao;
import com.example.widjet.main.database.PrazdnikDataBase;
import com.example.widjet.main.entity.DataEntity;
import com.example.widjet.main.entity.PrazdnikEntity;
import com.example.widjet.main.tdo.PrazdnikDTO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MyWidget extends AppWidgetProvider {
    public final static String UPDATE_WIDGET = "android.appwidget.action.APPWIDGET_UPDATE";
    private final String TAG = "MyWidget";
    private BootReceiver bootReceiver;
    private ScreeOffOnReceiver screeOffOnReceiver;
    private TimeChangeReceiver timeChangeReceiver;

    //onEnabled вызывается системой при создании первого экземпляра виджета (мы ведь можем добавить в Home несколько экземпляров одного и того же виджета).
    @Override
    public void onEnabled(final Context context) {
        registerBroadcast(context);
        super.onEnabled(context);//
    }

    private void registerBroadcast(Context context) {
        Intent intent = new Intent(context, MyWidget.class);
        intent.setAction(UPDATE_WIDGET);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),
                60000, pIntent);

        bootReceiver = new BootReceiver();
        IntentFilter intentFilterBoot = new IntentFilter();
        intentFilterBoot.addAction(BootReceiver.SCREEN_BOOT);

        screeOffOnReceiver = new ScreeOffOnReceiver();
        IntentFilter intentFilterScreen = new IntentFilter();
        intentFilterScreen.addAction(ScreeOffOnReceiver.SCREEN_ON);
        intentFilterScreen.addAction(ScreeOffOnReceiver.SCREEN_OFF);

        timeChangeReceiver = new TimeChangeReceiver();
        IntentFilter intentFilterTime = new IntentFilter();
        intentFilterTime.addAction(TimeChangeReceiver.TIME_SET);
        intentFilterTime.addAction(TimeChangeReceiver.TIME_TICK);


        context.getApplicationContext().registerReceiver(bootReceiver, intentFilterBoot);
        context.getApplicationContext().registerReceiver(screeOffOnReceiver, intentFilterScreen);
        context.getApplicationContext().registerReceiver(timeChangeReceiver, intentFilterTime);
    }

    private void unregisterBroadcast(Context context) {
        context.getApplicationContext().unregisterReceiver(bootReceiver);
        context.getApplicationContext().unregisterReceiver(screeOffOnReceiver);
        context.getApplicationContext().unregisterReceiver(timeChangeReceiver);
    }

    //onUpdate вызывается при обновлении виджета. На вход, кроме контекста, метод получает объект AppWidgetManager и список ID экземпляров виджетов, которые обновляются. Именно этот метод обычно содержит код, который обновляет содержимое виджета. Для этого нам нужен будет AppWidgetManager, который мы получаем на вход.
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int i : appWidgetIds) {
            RemoteViews views = getRemoteView(context);
            appWidgetManager.updateAppWidget(i, views);
        }
    }

    //onDeleted вызывается при удалении каждого экземпляра виджета. На вход, кроме контекста, метод получает список ID экземпляров виджетов, которые удаляются.
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: " + intent.getAction());
        super.onReceive(context, intent);

        if (intent.getAction().equalsIgnoreCase(UPDATE_WIDGET)) {
            ComponentName thisAppWidget = new ComponentName(
                    context.getPackageName(), getClass().getName());
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            int ids[] = appWidgetManager.getAppWidgetIds(thisAppWidget);
            for (int appWidgetID : ids) {
                updateWidget(context, appWidgetManager, appWidgetID);
            }
        }
    }

    void updateWidget(Context context, AppWidgetManager appWidgetManager,
                      int appWidgetId) {
        RemoteViews views = getRemoteView(context);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    private RemoteViews getRemoteView(Context context) {
        PrazdnikDTO prazdnik = getPrazdnik();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        String hour = calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));

        views.setTextViewText(R.id.textImg, prazdnik.getName());
        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));

        views.setOnClickPendingIntent(R.id.textImg, DescriptionActivity.getActivityIntent(context, prazdnik.getId()));
        views.setOnClickPendingIntent(R.id.img, DescriptionActivity.getActivityIntent(context, prazdnik.getId()));
        ;
        Log.i(TAG, "updateTimeWidget: " + prazdnik);

        return views;
    }


    //onDisabled вызывается при удалении последнего экземпляра виджета.
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

        unregisterBroadcast(context);
        Intent intent = new Intent(context, MyWidget.class);
        intent.setAction(UPDATE_WIDGET);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    private PrazdnikDTO getPrazdnik() {
        return getPrazdnikByDate(DateConverter.dateFormat.format(new Date()));
    }

    private PrazdnikDTO getPrazdnikByDate(String date) {
        Log.i(TAG, "getPrazdnikByDate: " + date + " - " + date);
        Log.i(TAG, "getPrazdnikByDate: " + new Date() + " - " + new Date().getTime());
        PrazdnikDataBase prazdnikDataBase = App.getInstance().getPrazdnikDataBase();
        DataDao dataDao = prazdnikDataBase.dataDao();

        List<DataEntity> allDateByDate = dataDao.getAllDateByDate(date);

        PrazdnikDao prazdnikDao = prazdnikDataBase.prazdnikDao();
        PrazdnikEntity prazdnikEntity = null;

        if (allDateByDate.size() > 1) {

            for (DataEntity d : allDateByDate) {
                PrazdnikEntity byId = prazdnikDao.getById(d.getParent_id());

                if (!byId.isPost()) {
                    prazdnikEntity = byId;
                }
            }
        } else {

            if (allDateByDate.size() != 0) {
                prazdnikEntity = prazdnikDao.getById(allDateByDate.get(0).getParent_id());
            }
        }

        if (prazdnikEntity == null) {
            prazdnikEntity = setDefault();
        }

        return new PrazdnikDTO(prazdnikEntity);
    }

    private PrazdnikEntity setDefault() {
        PrazdnikEntity prazdnikEntity = new PrazdnikEntity();
        prazdnikEntity.setName("Да будет Бог с вами");
        prazdnikEntity.setImg("god");
        prazdnikEntity.setId(-1);
        return prazdnikEntity;
    }

}