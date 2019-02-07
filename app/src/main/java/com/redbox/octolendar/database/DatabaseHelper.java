package com.redbox.octolendar.database;

import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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

        values.put(Event.COLUMN_DATE, event.getDate());
        values.put(Event.COLUMN_TIME, event.getTime());
        values.put(Event.COLUMN_TITLE, event.getTitle());
        values.put(Event.COLUMN_COMMENT, event.getComment());
        values.put(Event.COLUMN_URGENCY, event.getUrgency());

        long id = db.insert(Event.TABLE_NAME, null, values);

        db.close();
        return id;
    }

    //Get one event
    public Event getEvent(long id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(Event.TABLE_NAME, new String[]{Event.COLUMN_ID, Event.COLUMN_DATE, Event.COLUMN_TIME, Event.COLUMN_TITLE, Event.COLUMN_COMMENT, Event.COLUMN_URGENCY},
                Event.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null , null);

        if (cursor != null) cursor.moveToFirst();
        Event event = new Event(
                cursor.getInt(cursor.getColumnIndex(Event.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_DATE)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_TIME)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_COMMENT)),
                cursor.getString(cursor.getColumnIndex(Event.COLUMN_URGENCY)));

        cursor.close();
        return event;
    }

    //Get all events for the day
    //todo sort events by time parameter

    public List<Event> getDayEvents(String date) {
        List<Event> events = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Event.TABLE_NAME + " WHERE date=?" + " ORDER BY " + Event.COLUMN_TIME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{date});

        if (cursor.moveToFirst()) {
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndex(Event.COLUMN_ID)));
                event.setDate(cursor.getString(cursor.getColumnIndex(Event.COLUMN_DATE)));
                event.setTime(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TIME)));
                event.setTitle(cursor.getString(cursor.getColumnIndex(Event.COLUMN_TITLE)));
                event.setComment(cursor.getString(cursor.getColumnIndex(Event.COLUMN_COMMENT)));
                event.setUrgency(cursor.getString(cursor.getColumnIndex(Event.COLUMN_URGENCY)));
                events.add(event);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return events;
    }

    //Count Events
    public int getEventCount(){
        String countQuery = "SELECT * FROM " + Event.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    //Update event
    public int updateEvent (Event event){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Event.COLUMN_TIME, event.getTime());
        values.put(Event.COLUMN_TITLE, event.getTitle());
        values.put(Event.COLUMN_COMMENT, event.getComment());
        values.put(Event.COLUMN_URGENCY, event.getUrgency());

        return db.update(Event.TABLE_NAME, values, Event.COLUMN_ID + " =?", new String[]{String.valueOf(event.getId())});
    }


    public void deleteEvent(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Event.TABLE_NAME, Event.COLUMN_ID + " =?", new String[]{String.valueOf(event.getId())});
        db.close();
    }

}
