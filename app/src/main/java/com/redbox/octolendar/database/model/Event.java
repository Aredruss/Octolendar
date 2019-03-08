package com.redbox.octolendar.database.model;

import java.io.Serializable;

public class Event implements Serializable {

    public static final String TABLE_NAME = "events_db";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_START_TIME = "time_start";
    public static final String COLUMN_END_TIME = "time_end";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_URGENCY = "urgency";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_MONTH = "month";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_COMPLETED = "completed";


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_DAY + " INTEGER NOT NULL," +
            COLUMN_MONTH + " TEXT NOT NULL," +
            COLUMN_YEAR + " TEXT NOT NULL," +
            COLUMN_START_TIME + " TEXT NOT NULL," +
            COLUMN_END_TIME + " TEXT," +
            COLUMN_TITLE + " TEXT NOT NULL," +
            COLUMN_COMMENT + " TEXT," +
            COLUMN_URGENCY + " TEXT NOT NULL," +
            COLUMN_COMPLETED + " INTEGER NOT NULL)";

    private int id;
    private String timeStart;
    private String timeEnd;
    private String title;
    private String comment;
    private String urgency;
    private String day;
    private String month;
    private String year;

    private int completed;

    public Event() { }

    public Event(int id, String timeSt, String title, String comment, String urgency, String day, String month, String year, int completed) {
        this.id = id;
        this.timeStart = timeSt;
        this.title = title;
        this.comment = comment;
        this.urgency = urgency;
        this.day = day;
        this.month = month;
        this.year = year;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }



    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getStartTime() {
        return timeStart;
    }

    public String getEndTime() {
        return timeEnd;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }
    public int getCompleted() {
        return completed;
    }


    public void setDay(String day) {
        this.day = day;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartTime(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setEndTime(String timeEnd){
        this.timeEnd = timeEnd;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }


    public String getDate() {
        return day + "-" + month + "-" + year;
    }

    public void setDate(String date) {
        this.day = date.split("-")[0];
        this.month = date.split("-")[1];
        this.year = date.split("-")[2];
    }

}
