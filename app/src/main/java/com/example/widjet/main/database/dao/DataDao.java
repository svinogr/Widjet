package com.example.widjet.main.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.widjet.main.database.entity.DataEntity;

import java.util.List;

@Dao
public interface DataDao {

        @Query("select * from dataentity")
        List<DataEntity> getAllDate();

        @Query("select * from dataentity where id = :id")
        DataEntity getById(long id);

        @Insert
        long insert(DataEntity dataEntity);
    }

