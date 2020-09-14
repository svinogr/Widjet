package com.example.widjet.main;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.RemoteViews;

import com.example.widjet.R;
import com.example.widjet.main.database.tdo.PrazdnikDTO;
import com.example.widjet.main.imagecreator.ImageCreater;

import java.util.Date;

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

        // System.out.println(prazdnikDataBase);
    }

    //onDisabled вызывается при удалении последнего экземпляра виджета.
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }


    private void updateTimeWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        PrazdnikDTO prazdnik = getPrazdnik(new Date());
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.timeImg, "20:20" );
        views.setTextViewText(R.id.dateImg, "20/12/2020" );
        views.setTextViewText(R.id.textImg, prazdnik.getName() );
        views.setImageViewResource(R.id.img, context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private PrazdnikDTO getPrazdnik(Date date) {

        //    PrazdnikDataBase prazdnikDataBase = App.getInstance().getPrazdnikDataBase();
        //    PrazdnikDao prazdnikDao = prazdnikDataBase.prazdnikDao();
        //     PrazdnikEntity byId = prazdnikDao.getById(2);
        //     List<PrazdnikEntity> allprazdnik = prazdnikDao.getAllprazdnik();
        //    System.out.println(byId);
        //    System.out.println(allprazdnik.size());
        PrazdnikDTO prazdnikDTO = new PrazdnikDTO();
        prazdnikDTO.setName(" Рождество пасха кирилица второй день каждый ");
        prazdnikDTO.setDescription("wdwdhjkdhqdggdhwdgwdgjhwgdjwgdjwgd");
        prazdnikDTO.setImg("pasha");
        return prazdnikDTO;

    }

}
