package com.redbox.octolendar.utilities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateTimeUtilityClass extends AppCompatActivity {

    public static String prepareStringTime(int hour, int minute) {
        String strTime;
        if (minute < 10) {
            strTime = hour + ":0" + minute;
        } else if (minute == 0) strTime = hour + ":" + "00";
        else strTime = hour + ":" + minute;
        if (hour < 10){
            strTime = "0" + strTime;
        }
        return strTime;
    }

    public static String getCurrentTime() {
        LocalTime date = LocalTime.now();
        String strTime = prepareStringTime(date.getHour(), date.getMinute());

        return strTime;
    }

    public static int getMonthProgress(){
        LocalDate now = LocalDate.now();
        int today = now.getDayOfMonth();
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        int l_day = lastDay.getDayOfMonth();
        float percent = ((float) today / (float) l_day) * 100;

        return (int)percent;
    }

    public static String getToday(){

        LocalDate now = LocalDate.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();


        return day + "-" + month + "-" + year;
    }

    public static LocalTime getTimeFromString(String strTime){

        LocalTime time =  LocalTime.parse(strTime, DateTimeFormatter.ofPattern("HH:mm"));
        return time;

    }

   public static void openTimeDialog(Context c, TextView textView){
       TimePickerDialog pickerDialog;
       int orig_hour = 0, orig_minute = 0;
       final String[] timeStr = new String[1];
       pickerDialog = new TimePickerDialog(c, (TimePicker view, int hourOfDay, int minute)-> {
            textView.setText(DateTimeUtilityClass.prepareStringTime(hourOfDay,minute));
           Log.d(" f", "getToday: " + timeStr[0]);
       }, orig_hour, orig_minute, true);
       pickerDialog.setTitle("Pick time");
       pickerDialog.show();

   }

}
