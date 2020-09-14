package com.example.widjet.main;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

public class MyWidget extends AppWidgetProvider {

    //onUpdate вызывается при обновлении виджета. На вход, кроме контекста, метод получает объект AppWidgetManager и список ID экземпляров виджетов, которые обновляются. Именно этот метод обычно содержит код, который обновляет содержимое виджета. Для этого нам нужен будет AppWidgetManager, который мы получаем на вход.
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
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
    }

    //onDisabled вызывается при удалении последнего экземпляра виджета.
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
