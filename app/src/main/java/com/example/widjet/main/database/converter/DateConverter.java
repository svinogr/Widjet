package com.example.widjet.main.database.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateConverter {

    @TypeConverter
    public  Long fromDate(Date date) {
        System.out.println(date);
        System.out.println(date.getTime());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,0 );
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime().getTime();
    }

    @TypeConverter
    public Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
}
