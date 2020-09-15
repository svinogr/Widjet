package com.example.widjet.main;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.widjet.R;
import com.example.widjet.main.database.App;
import com.example.widjet.main.database.dao.DataDao;
import com.example.widjet.main.database.dao.PrazdnikDao;
import com.example.widjet.main.database.database.PrazdnikDataBase;
import com.example.widjet.main.database.entity.DataEntity;
import com.example.widjet.main.database.entity.PrazdnikEntity;
import com.example.widjet.main.database.tdo.PrazdnikDTO;

import java.util.Date;
import java.util.List;

public class MyWidget extends AppWidgetProvider {

    //onUpdate вызывается при обновлении виджета. На вход, кроме контекста, метод получает объект AppWidgetManager и список ID экземпляров виджетов, которые обновляются. Именно этот метод обычно содержит код, который обновляет содержимое виджета. Для этого нам нужен будет AppWidgetManager, который мы получаем на вход.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            updateTimeWidget(context, appWidgetManager, appWidgetId);
        }
    }

    //onDeleted вызывается при удалении каждого экземпляра виджета. На вход, кроме контекста, метод получает список ID экземпляров виджетов, которые удаляются.
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    //onEnabled вызывается системой при создании первого экземпляра виджета (мы ведь можем добавить в Home несколько экземпляров одного и того же виджета).
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        //TODO хрен знает здесь нужно начальную базу создать или нет???
        System.out.println("enabled widget");
    }

    //onDisabled вызывается при удалении последнего экземпляра виджета.
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }


    private void updateTimeWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        PrazdnikDTO prazdnik = getPrazdnik(new Date(1736813400000L));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.timeImg, "20:20");
        views.setTextViewText(R.id.dateImg, "20/12/2020");
        views.setTextViewText(R.id.textImg, prazdnik.getName());
        System.out.println(prazdnik.getImg());

        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private PrazdnikDTO getPrazdnik(Date date) {
        PrazdnikDataBase prazdnikDataBase = App.getInstance().getPrazdnikDataBase();
        DataDao dataDao = prazdnikDataBase.dataDao();
        System.out.println("gP" + date.getTime() );
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
            prazdnikEntity = prazdnikDao.getById(allDateByDate.get(0).getParent_id());
        }
        System.out.println(prazdnikEntity);

        return new PrazdnikDTO(prazdnikEntity);
    }
}


