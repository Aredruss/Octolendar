package com.redbox.octolendar.singleton;

import android.app.Application;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.Update;

import java.io.Serializable;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;
import static android.arch.persistence.room.ForeignKey.SET_NULL;

public class App extends Application {
    public static App instance;
    private EventDatabase eventDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        eventDatabase = Room.databaseBuilder(getApplicationContext(),
                EventDatabase.class, "event").fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public EventDatabase getEventDatabase() {
        return eventDatabase;
    }

    @Entity
    public static class Event implements Serializable {
        @PrimaryKey(autoGenerate = true)
        public long id;

        public String title;
        public String comment;
        public String urgency;
        public String day;
        public String month;
        public String year;
        public int completed;
        public String timeStart;
        public String timeEnd;

        public int tagId = 0;

        public void setDate(String date) {
            this.day = date.split("-")[0];
            this.month = date.split("-")[1];
            this.year = date.split("-")[2];
        }

        public String getDate() {
            return day + "-" + month + "-" + year;
        }

        public String getShareText() {
            return "I've planned " + this.title + " on " + this.getDate() + " at " + timeStart;
        }
    }

    @Entity
    public static class Tag {
        @PrimaryKey(autoGenerate = true)
        public long id;

        public String text;

        public int color;
    }

    @Dao
    public interface EventDao {

        @Query("SELECT * FROM event ORDER BY day, month, year, timeStart")
        List<Event> getAll();

        @Query("SELECT MAX(id) FROM event")
        int getAvID();

        @Query("SELECT * FROM event WHERE title = :title")
        List<Event> getByTitle(String title);


        @Query("SELECT * FROM event WHERE id = :id")
        Event getEvent(long id);

        @Query("SELECT * FROM event WHERE day = :day AND month=:month AND year = :year ORDER BY timeStart ASC")
        List<Event> getDayEvents(String day, String month, String year);

        @Insert
        void insert(Event event);

        @Update
        void update(Event event);

        @Delete
        void delete(Event event);
    }

    @Dao
    public interface TagDao {

        @Query("SELECT MAX(id) FROM tag")
        int getTagCount();

        @Query("SELECT * FROM tag")
        List<Tag> getAll();

        @Insert
        void insert(Tag tag);

        @Update
        void update(Tag tag);

        @Delete
        void delete(Tag tag);
    }

    @Database(entities = {Event.class, Tag.class}, version = 7)
    public abstract static class EventDatabase extends RoomDatabase {
        public abstract EventDao eventDao();

        public abstract TagDao tagDao();
    }
}
