package com.example.widjet.main;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.example.widjet.main.database.PrazdnikDataBase;

import java.io.File;


public class App extends Application {
    private static final String DB_NAME = "prazdnik";
    public static App instance;
    private PrazdnikDataBase prazdnikDataBase;
    private final String TAG = "App";

    public static App getInstance() {
        return instance;
    }

    public PrazdnikDataBase getPrazdnikDataBase() {
        return prazdnikDataBase;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate: ");
        super.onCreate();

        instance = this;
        //убрать allowqweryy времено для работы в UI
        final File dbFile = this.getApplicationContext().getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            prazdnikDataBase = Room.databaseBuilder(this, PrazdnikDataBase.class, "prazdnik")
                    .allowMainThreadQueries()
                    .createFromAsset("prazdnik")
                    .build();

            System.out.println("база пошла делаться");
        } else {
            prazdnikDataBase = Room.databaseBuilder(this, PrazdnikDataBase.class, "prazdnik")
                    .allowMainThreadQueries()
                    .build();
            System.out.println("файл уже есть");
        }


    }

    @Override
    public void onTerminate() {
        Log.i(TAG, "onTerminate: ");
        super.onTerminate();
    }


}