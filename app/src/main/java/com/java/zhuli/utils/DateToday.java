package com.java.zhuli.utils;

import android.os.Build;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateToday {

    public  static String returnDate(){
        String today = "0";

        DateTimeFormatter dtf = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        }
        LocalDate localDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = dtf.format(localDate);
        }

        return today;
    }

    public static String cutDate(String date){

        String[] splitStr = date.split("\\s+");
        return splitStr[0];
    }

}
