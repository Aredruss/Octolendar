package com.redbox.octolendar.database.model;

public class Event {

    public static final String TABLE_NAME = "events";
    public static final String COLUMN_ID = "id";
    public static final String DATE = "date";
    public static final String TITLE = "title";
    public static final String COMMENT = "comment";
    public static final String URGENCY = "urgency";

    private int id;
    private String date;
    private String title;
    private String comment;
    private String urgency;

    public Event() {

    }

    public Event(int id, String date, String title, String comment, String urgency) {
        this.id = id;
        this.date = date;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
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
}
