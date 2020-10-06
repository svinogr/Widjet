package com.example.widjet.main.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.widjet.main.converter.DateConverter;
import com.example.widjet.main.entity.DataEntity;

import java.util.List;

@Dao
public interface DataDao {

        @Query("select * from dataentity")
        List<DataEntity> getAllDate();

        @Query("select * from dataentity where id = :id")
        DataEntity getById(long id);

        @Insert
        long insert(DataEntity dataEntity);

        @Query("select * from dataentity where date = :date")
        @TypeConverters({DateConverter.class})
        List<DataEntity> getAllDateByDate(String date);
}

