package com.redbox.octolendar.utilities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public class DateTimeUtilityClass extends AppCompatActivity {


    //Get a proper string out of int values
    public static String prepareStringTime(int hour, int minute) {
        String strTime;
        if (minute < 10) {
            strTime = hour + ":0" + minute;
        } else if (minute == 0) strTime = hour + ":" + "00";
        else strTime = hour + ":" + minute;
        if (hour < 10) {
            strTime = "0" + strTime;
        }
        return strTime;
    }

    //Get a proper string from Calendar object, used for Notification info
    public static String prepareDateTime(Calendar c) {
        return "You've set a reminder to" + " " + c.get(Calendar.DAY_OF_MONTH) + "-"
                + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.YEAR) +
                " at " + prepareStringTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));

    }

    //Gets time at the moment of invocation
    public static String getCurrentTime() {
        LocalTime date = LocalTime.now();
        String strTime = prepareStringTime(date.getHour(), date.getMinute());

        return strTime;
    }

    //Gets progress for the month (10%, 23%, etc)
    public static int getMonthProgress() {
        LocalDate now = LocalDate.now();
        int today = now.getDayOfMonth();
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        int l_day = lastDay.getDayOfMonth();
        float percent = ((float) today / (float) l_day) * 100;

        return (int) percent;
    }

    //Gets the date at the moment of invocation
    public static String getToday() {
        LocalDate now = LocalDate.now();
        int day = now.getDayOfMonth();
        int month = now.getMonthValue();
        int year = now.getYear();
        return day + "-" + month + "-" + year;
    }

    //Parses LocalTime Object from String ("xx:xx")
    public static LocalTime getTimeFromString(String strTime) {
        LocalTime time = LocalTime.parse(strTime, DateTimeFormatter.ofPattern("HH:mm"));
        return time;

    }

    //Open up a Timepicker dialog. Used for Creating an Event and Editing it
    public static void openTimeDialog(Context c, TextView textView) {
        TimePickerDialog pickerDialog;
        pickerDialog = new TimePickerDialog(c, (TimePicker view, int hourOfDay, int minute) -> {
            textView.setText(DateTimeUtilityClass.prepareStringTime(hourOfDay, minute));
        }, 12, 0, true);
        pickerDialog.setTitle("Pick time");
        pickerDialog.show();
    }
}
