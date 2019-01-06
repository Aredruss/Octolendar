package com.redbox.octolendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CalendarView;
import android.content.Intent;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ProgressBar progressBar;
    CalendarView calendarView;
    ScrollView cardScroll;

    int releaseY;
    int pressY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View overlay = findViewById(R.id.MainRelativeLayout);
        UtilityClass.hideNavBar(overlay);

        cardScroll = findViewById(R.id.upcomingScrollView);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.progressTextView);

        textView.setText(UtilityClass.getMonthProgress() + "% of the month has passed.");
        progressBar.setProgress(UtilityClass.getMonthProgress());

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year;
                Intent intent = new Intent(MainActivity.this, DayActivity.class);
                intent.putExtra("Date", date);
                startActivity(intent);
            }
        });

        overlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        pressY = (int) motionEvent.getY();
                    }
                    case MotionEvent.ACTION_UP:{
                        releaseY = (int) motionEvent.getY();

                    }

                    if (releaseY < pressY){
                        Toast toast = Toast.makeText(getApplicationContext(), "You swiped up", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (releaseY > pressY){
                        //TODO create Timeline activity
                        Toast toast = Toast.makeText(getApplicationContext(), "You swiped down", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                return true;
            }
        });

    }

}