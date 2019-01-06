package com.redbox.octolendar;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class UtilityClass extends AppCompatActivity {

    public static String prepareStringTime(int hour, int minute) {
        String strTime = null;

        if (minute < 10) {
            strTime = hour + ":0" + minute;
        } else if (minute == 0) strTime = hour + ":" + "00";
        else strTime = hour + ":" + minute;

        return strTime;
    }

    public static String getCurrentTime() {
        LocalTime date = LocalTime.now();
        String strTime = prepareStringTime(date.getHour(), date.getMinute());

        return strTime;
    }

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
}
