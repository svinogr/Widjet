package com.example.widjet.main;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.widjet.R;
import com.example.widjet.main.database.App;
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

public class MyWidget extends AppWidgetProvider {
    //TODO сделать изменение праздника только после изменения даты в системе
    private Date time;
    private final static String UPDATE_WIDGET = "com.example.widjet.main.MyWidget.UPDATE_WIDGET";
    private final String TAG = "MyWidget";

    public static PendingIntent createUpdatePendIntent(Context context) {
        Intent intent = new Intent(UPDATE_WIDGET);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //onUpdate вызывается при обновлении виджета. На вход, кроме контекста, метод получает объект AppWidgetManager и список ID экземпляров виджетов, которые обновляются. Именно этот метод обычно содержит код, который обновляет содержимое виджета. Для этого нам нужен будет AppWidgetManager, который мы получаем на вход.
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        updateViews(context);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    //onDeleted вызывается при удалении каждого экземпляра виджета. На вход, кроме контекста, метод получает список ID экземпляров виджетов, которые удаляются.
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    //onEnabled вызывается системой при создании первого экземпляра виджета (мы ведь можем добавить в Home несколько экземпляров одного и того же виджета).
    @Override
    public void onEnabled(final Context context) {
        super.onEnabled(context);
        startOrStopService(context);
    }

    private void startOrStopService(Context context) {
        boolean myServiceRunning = isMyServiceRunning(UpdateService.class, context);
        Log.i(TAG, "startOrStopService: " + myServiceRunning);
        if (myServiceRunning) {
            context.stopService(new Intent(context, UpdateService.class));
            Log.i(TAG, "startOrStopService: to stop");

        } else {
            context.startService(new Intent(context, UpdateService.class));
            Log.i(TAG, "startOrStopService: to run");
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (UPDATE_WIDGET.equals(intent.getAction())) {
            //Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
            updateViews(context);
        }

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
        }

        Log.i(TAG, "onReceive: "+ intent.getAction());


        super.onReceive(context, intent);
    }

    private void updateViews(Context context) {
        RemoteViews views = updateTimeWidget(context);
        ComponentName widget = new ComponentName(context, MyWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.updateAppWidget(widget, views);
    }

    private RemoteViews updateTimeWidget(Context context) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        PrazdnikDTO prazdnik = getPrazdnik();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        views.setTextViewText(R.id.timeImg,
                calendar.get(Calendar.HOUR_OF_DAY) + ":" + (calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE)));
        views.setTextViewText(R.id.dateImg, simpleDateFormat.format(calendar.getTime()));

        views.setTextViewText(R.id.textImg, prazdnik.getName());
        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));
        return views;
    }

    //onDisabled вызывается при удалении последнего экземпляра виджета.
    @Override
    public void onDisabled(Context context) {
        startOrStopService(context);
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
        time = calendar.getTime();

        return getPrazdnikByDate(time);
    }

    private PrazdnikDTO getPrazdnikByDate(Date date) {
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