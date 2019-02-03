package com.redbox.octolendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class MainActivity extends AppCompatActivity {

   private TextView textView;
   private ProgressBar progressBar;
   private CalendarView calendarView;
   private View overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overlay = findViewById(R.id.MainRelativeLayout);
        UtilityFunctionsClass.hideNavBar(overlay);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.progressTextView);

        textView.setText(UtilityFunctionsClass.getMonthProgress() + "% of the month has passed.");
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilityFunctionsClass.hideNavBar(overlay);
    }
}