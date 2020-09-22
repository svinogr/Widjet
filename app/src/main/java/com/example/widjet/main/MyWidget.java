package com.example.widjet.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.RemoteViews;

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

    private Date time;
    private final static String UPDATE_WIDGET = "com.example.widjet.main.MyWidget.UPDATE_WIDGET";
    private final String TAG = "MyWidget";

    public static PendingIntent createUpdatePendIntent(Context context) {
        Intent intent = new Intent(UPDATE_WIDGET);
        System.out.println("createUpdatePendIntent");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //onUpdate вызывается при обновлении виджета. На вход, кроме контекста, метод получает объект AppWidgetManager и список ID экземпляров виджетов, которые обновляются. Именно этот метод обычно содержит код, который обновляет содержимое виджета. Для этого нам нужен будет AppWidgetManager, который мы получаем на вход.
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
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
        Log.i(TAG, "onEnabled: ");
        context.startService(new Intent(context, UpdateService.class));

    /*    Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Log.i(TAG, "onEnabled: time" + calendar.getTime());
        AlarmManager systemService = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
      //  systemService.setWindow(AlarmManager.RTC, calendar.getTime().getTime(), 60000, createUpdatePendIntent(context));

        systemService.setExact(AlarmManager.RTC, calendar.getTime().getTime(), createUpdatePendIntent(context));*/


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive: " + new Date() );
        Log.i(TAG, "onReceive: " + intent.getAction() );
        System.out.println("on recive");

        if (UPDATE_WIDGET.equals(intent.getAction())) {
           updateViews(context);
        }
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
        System.out.println(prazdnik);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        views.setTextViewText(R.id.timeImg,
                calendar.get(Calendar.HOUR_OF_DAY) + " : " + (calendar.get(Calendar.MINUTE) < 10 ? "0 " + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE)));
        views.setTextViewText(R.id.dateImg, simpleDateFormat.format(calendar.getTime()));

        views.setTextViewText(R.id.textImg, prazdnik.getName());
        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));
        return views;
    }

    /*public static class UpdateService extends Service {

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flag,  int startId) {
            super.onStart(intent, startId);
            RemoteViews views = updateTimeWidget(this);
            ComponentName widget = new ComponentName(this, MyWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            appWidgetManager.updateAppWidget(widget, views);
            return 1;
        }

        private RemoteViews updateTimeWidget(Context context) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());

            views.setTextViewText(R.id.timeImg,
                    calendar.get(Calendar.HOUR_OF_DAY) + " : " + (calendar.get(Calendar.MINUTE) < 10 ? "0 " + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE)));
            views.setTextViewText(R.id.dateImg, simpleDateFormat.format(calendar.getTime()));

            return views;


       *//* calendar.set(Calendar.HOUR_OF_DAY, 0);
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

        appWidgetManager.updateAppWidget(appWidgetId, views);*//*
        }
    }*/


        //onDisabled вызывается при удалении последнего экземпляра виджета.
        @Override
        public void onDisabled (Context context){
            super.onDisabled(context);
            // как я понимаю удаляет интент с которым в сервисе что то крутится

        }

        private PrazdnikDTO getPrazdnik(){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar = new GregorianCalendar();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0); // facking calendar need set millsecond every time
            // TODO for testing in code удалить-----
            //    calendar.set(Calendar.YEAR, 2023);
            // TODO --------------
            time = calendar.getTime();

            System.out.println(simpleDateFormat.format(time));
            System.out.println(time.getTime());

            return getPrazdnikByDate(time);



        }

        private PrazdnikDTO getPrazdnikByDate(Date date){

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

        private PrazdnikEntity setDefault () {
            PrazdnikEntity prazdnikEntity = new PrazdnikEntity();
            prazdnikEntity.setName("Да будет Бог с вами");
            prazdnikEntity.setImg("god");
            prazdnikEntity.setId(-1);
            return prazdnikEntity;
        }
    }