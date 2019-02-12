package com.redbox.octolendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class MainActivity extends AppCompatActivity {

   private TextView textView;
   private ProgressBar progressBar;
   private CalendarView calendarView;
   private View overlay;
   private String percentString;
   private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overlay = findViewById(R.id.MainRelativeLayout);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.progressTextView);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        percentString = "%d%% of the month has passed";

        textView.setText(String.format(percentString, UtilityFunctionsClass.getMonthProgress()));

        progressBar.setProgress(UtilityFunctionsClass.getMonthProgress());

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                String date = day + "-" + (month + 1) + "-" + year;
                Intent intent = new Intent(MainActivity.this, DayActivity.class);
                intent.putExtra("Date", date);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("TAG", "onOptionsItemSelected: " + item.getItemId());

                switch (item.getItemId()){
                    case R.id.calendar:{
                        return true;
                    }
                    case R.id.today:{
                        String date =   UtilityFunctionsClass.getToday();
                        Intent intent = new Intent(MainActivity.this, DayActivity.class);
                        intent.putExtra("Date", date);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.timeline:{
                        Intent intent = new Intent(MainActivity.this, TimelineActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.settings:{
                        Intent intent = new Intent(MainActivity.this, MiscActivity.class);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu,menu);
        menu.setQwertyMode(true);
        return  true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.calendar);
    }
}