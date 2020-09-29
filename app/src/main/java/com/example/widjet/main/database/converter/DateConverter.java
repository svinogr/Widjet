package com.example.widjet.main.database.converter;

import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
   public static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @TypeConverter
    public String fromDate(Date date) {
        String strDate = dateFormat.format(date);

        return strDate;
    }

    @TypeConverter
    public Date fromTimestamp(String value) {
        try {
            return value == null ? null : dateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }
    }
}
