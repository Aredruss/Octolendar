package com.redbox.octolendar;

import android.content.Intent;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DatabaseHelper db;
    private  List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.timeline);
        db = new DatabaseHelper(this);

        eventList= new ArrayList<>();
        eventList = db.getAllEvents();

        ArrayList<TimelineRow>  timeline = new ArrayList<>();
        int i = 0;
        for (Event e : eventList){
            TimelineRow timelineRow = new TimelineRow(i);

            timelineRow.setBackgroundColor(ContextCompat.getColor(this, R.color.colorTimelineBack));
            timelineRow.setBellowLineColor(ContextCompat.getColor(this, R.color.colorTimelineBack));
            timelineRow.setBellowLineSize(5);

            timelineRow.setTitle(eventList.get(i).getTitle());
            timelineRow.setDescription(eventList.get(i).getTime() + " " + eventList.get(i).getDate() + " - " + eventList.get(i).getUrgency());
            timelineRow.setImageSize(30);

            timeline.add(timelineRow);
            i++;
        }

        ArrayAdapter<TimelineRow> timelineRowArrayAdapter = new TimelineViewAdapter(this, 0, timeline, false);
        ListView myListView = findViewById(R.id.timeline_listView);
        myListView.setAdapter(timelineRowArrayAdapter);


        //todo fix
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Event e = getItem(i);
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View infoView = inflater.inflate(R.layout.timeline_info_layout, null);

                TextView titleTextView = findViewById(R.id.titleTimelineTextView);
                TextView commentTextView = findViewById(R.id.commentTimelineTextView);
                TextView dateTextView = findViewById(R.id.dateTimelineTextView);
                TextView urgencyTextView = findViewById(R.id.urgencyTimelineTextView);

                titleTextView.setText(e.getTitle());
                commentTextView.setText(e.getComment());
                dateTextView.setText(e.getTime() + " " + e.getDate());
                urgencyTextView.setText(e.getUrgency());

            }
        });



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("TAG", "onOptionsItemSelected: " + item.getItemId());

                switch (item.getItemId()){
                    case R.id.calendar:{
                        Intent intent = new Intent(TimelineActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.today:{
                        String date =   UtilityFunctionsClass.getToday();
                        Intent intent = new Intent(TimelineActivity.this, DayActivity.class);
                        intent.putExtra("Date", date);
                        startActivity(intent);
                        return true;
                    }

                    case R.id.settings:{
                        Intent intent = new Intent(TimelineActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    default:{
                        return true;
                    }
                }

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.timeline);
        overridePendingTransition(0, 0);
    }

    public Event getItem(int i){
        return eventList.get(i);
    }
}
