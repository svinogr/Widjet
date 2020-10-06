package com.example.widjet.main.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.widjet.main.entity.PrazdnikEntity;

import java.util.List;


@Dao
public  interface PrazdnikDao {

    @Query("select * from prazdnikentity")
    List<PrazdnikEntity> getAllprazdnik();

    @Query("select * from prazdnikEntity where id = :id")
    PrazdnikEntity getById(long id);

    @Insert
    long insert(PrazdnikEntity prazdnik);
}
