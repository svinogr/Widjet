package com.example.widjet.main;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RemoteViews;

public class TimeWatcher implements TextWatcher {
    private WidgetUpdateInterface widgetUpdateInterface;
    private RemoteViews views;

    public TimeWatcher(WidgetUpdateInterface widgetUpdateInterface, RemoteViews remoteViews) {
        this.widgetUpdateInterface = widgetUpdateInterface;

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        widgetUpdateInterface.updateWidgetByDate();
    }
}
