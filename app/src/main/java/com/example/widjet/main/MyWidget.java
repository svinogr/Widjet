package com.example.widjet.main;

import android.app.ActivityManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.widjet.R;
import com.example.widjet.main.broadcast.BootReceiver;
import com.example.widjet.main.broadcast.ScreeOffOnReceiver;
import com.example.widjet.main.broadcast.TimeChangeReceiver;
import com.example.widjet.main.database.App;
import com.example.widjet.main.database.converter.DateConverter;
import com.example.widjet.main.database.dao.DataDao;
import com.example.widjet.main.database.dao.PrazdnikDao;
import com.example.widjet.main.database.database.PrazdnikDataBase;
import com.example.widjet.main.database.entity.DataEntity;
import com.example.widjet.main.database.entity.PrazdnikEntity;
import com.example.widjet.main.database.tdo.PrazdnikDTO;
import com.example.widjet.main.service.UpdateService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class MyWidget extends AppWidgetProvider {
    //TODO сделать изменение праздника только после изменения даты в системе
    private Date time;
    public final static String UPDATE_WIDGET = "com.example.widjet.main.MyWidget.UPDATE_WIDGET";
    private final String TAG = "MyWidget";

    private TimeChangeReceiver timeChangeReceiver;
    private ScreeOffOnReceiver screeOffOnReceiver;
    private BootReceiver bootReceiver;



/*    public static PendingIntent createUpdatePendIntent(Context context) {
        Intent intent = new Intent(UPDATE_WIDGET);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }*/


    private Intent getUpdateServiceIntent(Context context) {
        return new Intent(context, UpdateService.class);
    }

    //onEnabled вызывается системой при создании первого экземпляра виджета (мы ведь можем добавить в Home несколько экземпляров одного и того же виджета).
    @Override
    public void onEnabled(final Context context) {
        super.onEnabled(context);
        boolean myServiceRunning = isMyServiceRunning(UpdateService.class, context);

        if (!myServiceRunning) {
            context.startService(getUpdateServiceIntent(context));
            Log.i(TAG, "startOrStopService: " + myServiceRunning);
            Log.i(TAG, "startOrStopService: to run");
        }

        createReceivers(context.getApplicationContext());
    }

    private void createReceivers(Context context) {
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

    private void unRegistrationReceivers(Context context) {
        context.unregisterReceiver(timeChangeReceiver);
        context.unregisterReceiver(screeOffOnReceiver);
    }

    //onUpdate вызывается при обновлении виджета. На вход, кроме контекста, метод получает объект AppWidgetManager и список ID экземпляров виджетов, которые обновляются. Именно этот метод обычно содержит код, который обновляет содержимое виджета. Для этого нам нужен будет AppWidgetManager, который мы получаем на вход.
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // возможно здесь стоит запустить сервис
        updateViews(context);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    //onDeleted вызывается при удалении каждого экземпляра виджета. На вход, кроме контекста, метод получает список ID экземпляров виджетов, которые удаляются.
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (UPDATE_WIDGET.equals(intent.getAction())) {
            //Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();

        }
        updateViews(context);
        Log.i(TAG, "onReceive: " + intent.getAction());

    }

    private void updateViews(Context context) {
        RemoteViews views = updateTimeWidget(context);
        ComponentName widget = new ComponentName(context, MyWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(widget, views);
    }

    private RemoteViews updateTimeWidget(Context context) {
        PrazdnikDTO prazdnik = getPrazdnik();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        String hour = calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : String.valueOf(calendar.get(Calendar.MINUTE));
        views.setTextViewText(R.id.timeImg, hour + ":" + minute);
        views.setTextViewText(R.id.dateImg, simpleDateFormat.format(calendar.getTime()));

        views.setTextViewText(R.id.textImg, prazdnik.getName());
        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));
        Log.i(TAG, "updateTimeWidget: " + prazdnik);

        views.setOnClickPendingIntent(R.id.textImg, DescriptionActivity.getActivityIntent(context, prazdnik.getId()));
        views.setOnClickPendingIntent(R.id.img, DescriptionActivity.getActivityIntent(context, prazdnik.getId()));

        return views;
    }

    //onDisabled вызывается при удалении последнего экземпляра виджета.
    @Override
    public void onDisabled(Context context) {
        boolean myServiceRunning = isMyServiceRunning(UpdateService.class, context);

        if (myServiceRunning) {
            context.stopService(getUpdateServiceIntent(context));
            Log.i(TAG, "startOrStopService: to stop");
        }

        //  unRegistrationReceivers(context.getApplicationContext());

        super.onDisabled(context);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {

                return true;
            }
        }

        return false;
    }

    private PrazdnikDTO getPrazdnik() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0); // facking calendar need set millsecond every time
       calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        time = calendar.getTime();

        //return getPrazdnikByDate(time);
        return getPrazdnikByDate(DateConverter.dateFormat.format(new Date()));
    }

    private PrazdnikDTO getPrazdnikByDate(String date) {
        Log.i(TAG, "getPrazdnikByDate: " + date + " - " + date);
        Log.i(TAG, "getPrazdnikByDate: " +new Date() +" - " + new Date().getTime());
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