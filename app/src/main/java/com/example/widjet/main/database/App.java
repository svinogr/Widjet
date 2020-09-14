package com.example.widjet.main.database;

import android.app.Application;

import androidx.room.Room;

import com.example.widjet.main.database.database.PrazdnikDataBase;


public class App extends Application {
    public static App instance;
    private PrazdnikDataBase prazdnikDataBase;

    public static App getInstance() {
        return instance;
    }

    public PrazdnikDataBase getPrazdnikDataBase() {
        return prazdnikDataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        //убрать allowqweryy времено для работы в UI
        prazdnikDataBase = Room.databaseBuilder(this, PrazdnikDataBase.class, "prazdnik").allowMainThreadQueries().build();
    }
}
