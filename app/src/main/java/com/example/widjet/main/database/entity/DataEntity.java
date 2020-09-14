package com.example.widjet.main.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.widjet.main.database.converter.DateConverter;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = PrazdnikEntity.class, childColumns = "parent_id", parentColumns = "id"))
public class DataEntity {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @TypeConverters({DateConverter.class})
    private Date date;
    @ColumnInfo(name = "parent_id")
    private long parent_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }
}
