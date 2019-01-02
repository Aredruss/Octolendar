package com.redbox.octolendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.view.View;
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

        calendarView = findViewById(R.id.calendarView);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.progressTextView);
        getProgress();
    }

    public void getProgress() {
        LocalDate now = LocalDate.now();
        int today = now.getDayOfMonth();
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        int l_day = lastDay.getDayOfMonth();

        float percent = ((float)today/(float)l_day)*100;

        textView.setText((int)percent + "% of the month has passed.");
        progressBar.setProgress((int)percent);
    }
}
