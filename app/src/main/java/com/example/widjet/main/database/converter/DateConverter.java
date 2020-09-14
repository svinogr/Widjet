package com.example.widjet.main.database.converter;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public  Long fromDate(Date date) {
        System.out.println(date);
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
}
