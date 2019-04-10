package com.redbox.octolendar.database;

import com.redbox.octolendar.database.model.Event;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VER = 1;
    public static final String DATABASE_NAME = "events_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Event.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Event.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //Insert Event
    public long insertEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Event.COLUMN_DAY, event.getDay());
        values.put(Event.COLUMN_MONTH, event.getMonth());
        values.put(Event.COLUMN_YEAR, event.getYear());
        values.put(Event.COLUMN_START_TIME, event.getStartTime());
        values.put(Event.COLUMN_END_TIME,event.getEndTime());
        values.put(Event.COLUMN_TITLE, event.getTitle());
        values.put(Event.COLUMN_COMMENT, event.getComment());
        values.put(Event.COLUMN_URGENCY, event.getUrgency());
        values.put(Event.COLUMN_COMPLETED, event.getCompleted());

        long id = db.insert(Event.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    //Get one event
    @Deprecated
    public Event getEvent(long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(Event.TABLE_NAME, new String[]{Event.COLUMN_ID, Event.COLUMN_DAY, Event.COLUMN_MONTH, Event.COLUMN_YEAR,
                        Event.COLUMN_START_TIME, Event.COLUMN_END_TIME, Event.COLUMN_TITLE, Event.COLUMN_COMMENT, Event.COLUMN_URGENCY, Event.COLUMN_COMPLETED},
                Event.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) cursor.moveToFirst();
        Event event = new Event(
                cursor.getInt(cursor.getColumnIndex(Event.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_DAY)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_MONTH)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_YEAR)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_START_TIME)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_COMMENT)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_URGENCY)),
                cursor.getInt(cursor.getColumnIndex(Event.COLUMN_COMPLETED)));

        cursor.close();
        return event;
    }

    public List<Event> getDayEvents(String date) {
        List<Event> events = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Event.TABLE_NAME + " WHERE day=? AND month=? AND year=?" + "ORDER BY " + Event.COLUMN_START_TIME + " ASC";
        String[] splitDateStr = date.split("-");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{splitDateStr[0], splitDateStr[1], splitDateStr[2]});

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(Event.COLUMN_ID)));
                event.setDay(cursor.getString(cursor.getColumnIndex(Event.COLUMN_DAY)));
                event.setMonth(cursor.getString(cursor.getColumnIndex(Event.COLUMN_MONTH)));
                event.setYear(cursor.getString(cursor.getColumnIndex(Event.COLUMN_YEAR)));
                event.setStartTime(cursor.getString(cursor.getColumnIndex(Event.COLUMN_START_TIME)));
                event.setEndTime(cursor.getString(cursor.getColumnIndex(Event.COLUMN_END_TIME)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)));
                event.setComment(cursor.getString(cursor.getColumnIndex(Event.COLUMN_COMMENT)));
                event.setUrgency(cursor.getString(cursor.getColumnIndex(Event.COLUMN_URGENCY)));
                event.setCompleted(cursor.getInt(cursor.getColumnIndex(Event.COLUMN_COMPLETED)));
                events.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }


    public List<Event> getAllEvents() {
        List<Event> eventList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Event.TABLE_NAME + " ORDER BY " + Event.COLUMN_MONTH + " ASC, "+ Event.COLUMN_DAY + " ASC," + Event.COLUMN_YEAR + " ASC, " + Event.COLUMN_START_TIME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(Event.COLUMN_ID)));
                event.setDay(cursor.getString(cursor.getColumnIndex(Event.COLUMN_DAY)));
                event.setMonth(cursor.getString(cursor.getColumnIndex(Event.COLUMN_MONTH)));
                event.setYear(cursor.getString(cursor.getColumnIndex(Event.COLUMN_YEAR)));
                event.setStartTime(cursor.getString(cursor.getColumnIndex(Event.COLUMN_START_TIME)));
                event.setEndTime(cursor.getString(cursor.getColumnIndex(Event.COLUMN_END_TIME)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)));
                event.setComment(cursor.getString(cursor.getColumnIndex(Event.COLUMN_COMMENT)));
                event.setUrgency(cursor.getString(cursor.getColumnIndex(Event.COLUMN_URGENCY)));
                event.setCompleted(cursor.getInt(cursor.getColumnIndex(Event.COLUMN_COMPLETED)));
                eventList.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        int i = 0;

        return eventList;
    }

    //Count Events
    public int getEventCount() {
        String countQuery = "SELECT * FROM " + Event.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Update event
    public int updateEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Event.COLUMN_START_TIME, event.getStartTime());
        values.put(Event.COLUMN_END_TIME, event.getEndTime());
        values.put(Event.COLUMN_TITLE, event.getTitle());
        values.put(Event.COLUMN_COMMENT, event.getComment());
        values.put(Event.COLUMN_URGENCY, event.getUrgency());
        values.put(Event.COLUMN_COMPLETED,event.getCompleted());

        return db.update(Event.TABLE_NAME, values, Event.COLUMN_ID + " =?", new String[]{String.valueOf(event.getId())});
    }

    public void deleteEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Event.TABLE_NAME, Event.COLUMN_ID + " =?", new String[]{String.valueOf(event.getId())});
        db.close();
    }

    //todo find event by name
    public List<Event> findEvent(String arg){

        List<Event> eventList = new ArrayList<>();

        return eventList;
    }
}
