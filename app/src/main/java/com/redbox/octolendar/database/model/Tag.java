package com.redbox.octolendar.database.model;

import java.io.Serializable;

public class Tag implements Serializable {

    public static final String TABLE_NAME = "tags";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_EVENT = "event_id";
    public static final String COLUMN_COLOR = "color";

    private int id;
    private String text;
    private int eventId;
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
