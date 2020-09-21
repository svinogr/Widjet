package com.example.widjet.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
    private final String UPDATE_WIDGET = "com.example.widjet.main.MyWidget.UPDATE_WIDGET";

    private PendingIntent createUpdatePendIntent(Context context) {
        Intent intent = new Intent(UPDATE_WIDGET);
        System.out.println("createUpdatePendIntent");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    //onUpdate вызывается при обновлении виджета. На вход, кроме контекста, метод получает объект AppWidgetManager и список ID экземпляров виджетов, которые обновляются. Именно этот метод обычно содержит код, который обновляет содержимое виджета. Для этого нам нужен будет AppWidgetManager, который мы получаем на вход.
    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
      /*  System.out.println("onUpdate");
        Intent intent = new Intent(context, UpdateService.class);
        System.out.println("onUpdate getACtion intent " + intent.getAction());
        context.startService(intent);*/
 /*       for (int appWidgetId : appWidgetIds) {
            updateTimeWidget(context, appWidgetManager, appWidgetId);
        }*/

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(true) {
                    handler.postDelayed(this, 1000);
                    System.out.println("---");
                    System.out.println("onUpdate");
                    Intent intent = new Intent(context, UpdateService.class);
                    System.out.println("onUpdate getACtion intent " + intent.getAction());
                    context.startService(intent);
                }
            }
        };
        handler.post(runnable);

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
        //TODO хрен знает здесь нужно начальную базу создать или нет???
        System.out.println("enabled widget");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 1);



        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 60000, createUpdatePendIntent(context));

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        System.out.println("getaction on recive " + intent.getAction());
        if (UPDATE_WIDGET.equals(intent.getAction())) {
            context.startService(new Intent(context, UpdateService.class));
            System.out.println("onREcive in if");
        }
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
    public void onDisabled(Context context) {
        super.onDisabled(context);
        // как я понимаю удаляет интент с которым в сервисе что то крутится
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createUpdatePendIntent(context));
    }

    private void updateTimeWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
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

        PrazdnikDTO prazdnik = getPrazdnik(time);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.timeImg, getTime());
        views.setTextViewText(R.id.dateImg, simpleDateFormat.format(time));
        views.setTextViewText(R.id.textImg, prazdnik.getName());
        System.out.println(prazdnik.getImg());

        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private CharSequence getTime() {
        return "20:20";
    }

    private PrazdnikDTO getPrazdnik(Date date) {

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