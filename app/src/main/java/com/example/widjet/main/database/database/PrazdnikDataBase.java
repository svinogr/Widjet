package com.example.widjet.main.database.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.widjet.main.database.dao.DataDao;
import com.example.widjet.main.database.dao.PrazdnikDao;
import com.example.widjet.main.database.entity.DataEntity;
import com.example.widjet.main.database.entity.PrazdnikEntity;


@Database(entities = {PrazdnikEntity.class, DataEntity.class}, version = 1)
public abstract class PrazdnikDataBase extends RoomDatabase {
    public abstract PrazdnikDao prazdnikDao();
    public abstract DataDao dataDao();
}
