package com.example.widjet.main.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

//TODO удалить
public class DataAndPrazdnik {
    @Embedded
    public DataEntity dataEntity;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    public PrazdnikEntity prazdnikEntity;
}
