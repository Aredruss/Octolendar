package com.redbox.octolendar.parser;

public class SampleEvent {
    public String activity;
    public float access;
    public String type;
    public float participants;
    public float price;
    public String key;

    public SampleEvent(String activity, float access, String type, float participants, float price, String key) {
        this.activity = activity;
        this.access = access;
        this.type = type;
        this.participants = participants;
        this.price = price;
        this.key = key;
    }

    public SampleEvent() {

    }
}
