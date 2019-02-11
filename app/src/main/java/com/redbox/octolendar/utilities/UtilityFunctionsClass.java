package com.redbox.octolendar.utilities;

import android.support.annotation.Keep;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class UtilityFunctionsClass extends AppCompatActivity {

    public static String prepareStringTime(int hour, int minute) {
        String strTime = null;


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

    @Deprecated
    public static void hideNavBar(View overlay) {
        overlay.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
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
        Log.d(" f", "getToday: " + day + " " + month + " " + year);

        return day + "-" + month + "-" + year;
    }

    public static int[] getIntTime(String strTime){
        int[] time = new int[2];
        String[] splitStr = strTime.split(":");
        time[0] = Integer.parseInt(splitStr[0]);
        time[1] = Integer.parseInt(splitStr[1]);
        return  time;
    }

    @Keep
    public static String getNextDay(String prevDay){
        String nextDay = null;
        return prevDay;
    }

    @Keep
    public static void parseTimeFromString(String timeStr){
        DateFormat sdf = new SimpleDateFormat("hh:mm");
        try{
            Date time = sdf.parse(timeStr);
            Log.d("U", "parseTimeFromString: " + time);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
