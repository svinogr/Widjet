package com.example.widjet.main.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.widjet.main.database.entity.DataAndPrazdnik;
import com.example.widjet.main.database.entity.PrazdnikEntity;

import java.util.Date;
import java.util.List;


@Dao
public  interface PrazdnikDao {

    @Query("select * from prazdnikentity")
    List<PrazdnikEntity> getAllprazdnik();

    @Query("select * from prazdnikEntity where id = :id")
    PrazdnikEntity getById(long id);

    @Insert
    long insert(PrazdnikEntity prazdnik);

    @Transaction
    @Query("select * from PrazdnikEntity where 'date' = :date ")
    public List<DataAndPrazdnik> getPrazdnikBy(Date date);
}
