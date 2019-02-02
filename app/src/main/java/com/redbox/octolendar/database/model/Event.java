package com.redbox.octolendar.database.model;

public class Event {

    public static final String TABLE_NAME = "events_db";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME= "time";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_URGENCY = "urgency";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_DATE + " TEXT NOT NULL," +
            COLUMN_TIME + " TEXT NOT NULL," +
            COLUMN_TITLE + " TEXT NOT NULL," +
            COLUMN_COMMENT + " TEXT," +
            COLUMN_URGENCY + " TEXT NOT NULL)";

    private int id;
    private String date;

    private String time;
    private String title;
    private String comment;
    private String urgency;

    public Event() {

    }

    public Event(int id, String date, String time, String title, String comment, String urgency) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.comment = comment;
        this.urgency = urgency;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
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

    public String getTime() { return time; }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) { this.time = time; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
}
