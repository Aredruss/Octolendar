package com.redbox.octolendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.dialogs.TimelineInfoDialog;
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
                        Intent intent = new Intent(TimelineActivity.this, MiscActivity.class);
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
