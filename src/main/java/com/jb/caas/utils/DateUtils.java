package com.jb.caas.utils;

/*
 * copyrights @ fadi
 */

import java.sql.Date;

public class DateUtils {
    public static String beautifyDate(Date date) {
        int year = date.toLocalDate().getYear();
        int month = date.toLocalDate().getMonthValue();
        int day = date.toLocalDate().getDayOfMonth();
        return String.format("%02d/%02d/%04d", day, month, year);
    }
}