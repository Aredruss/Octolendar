package com.redbox.octolendar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.CalendarView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ProgressBar;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View overlay = findViewById(R.id.MainRelativeLayout);
        UtilityClass.hideNavBar(overlay);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.progressTextView);
        getProgress();

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year;
                Intent intent = new Intent(MainActivity.this, DayActivity.class);
                intent.putExtra("Date", date);
                startActivity(intent);
            }
        });

    }

    //TODO Generate a list of upcoming events for the ScrollView

    public void getProgress() {
        LocalDate now = LocalDate.now();
        int today = now.getDayOfMonth();
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        int l_day = lastDay.getDayOfMonth();

        float percent = ((float) today / (float) l_day) * 100;

        textView.setText((int) percent + "% of the month has passed.");
        progressBar.setProgress((int) percent);
    }

}