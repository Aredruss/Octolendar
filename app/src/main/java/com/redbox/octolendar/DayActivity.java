package com.redbox.octolendar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DayActivity extends AppCompatActivity implements EventDialog.EventDialogListener {

    private TextView dateTextView;
    private FloatingActionButton button;
    private TextView headingTextView;
    private TextView commentTextView;
    private TextView timeTextView;
    private TextView urgencyTextView;
    private CardView card;
    private ScrollView scrollView;
    private View overlay;
    private int pressX;
    private int releaseX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        overlay = findViewById(R.id.dayRelativeLayout);
        UtilityClass.hideNavBar(overlay);

        dateTextView = findViewById(R.id.dateTextView);
        Intent intentIncoming = getIntent();
        String date = intentIncoming.getStringExtra("Date");
        dateTextView.setText(date);
        button = findViewById(R.id.addFloatingButton);
        scrollView = findViewById(R.id.plannedScrollView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        headingTextView = findViewById(R.id.headingTextView);
        commentTextView = findViewById(R.id.commentTextView);
        urgencyTextView = findViewById(R.id.urgencyTextView);
        timeTextView = findViewById(R.id.timeTextView);
        card = findViewById(R.id.eventCardView);

        card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //TODO Create a dialog for editing events - important
                return true;
            }
        });

        //TODO create logic for swiping between days
        overlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN: {
                        pressX = (int) motionEvent.getX();
                    }
                    case MotionEvent.ACTION_UP:{
                        releaseX = (int) motionEvent.getX();

                    }

                    if (releaseX < pressX){
                        Toast toast = Toast.makeText(getApplicationContext(), "You swiped left", Toast.LENGTH_SHORT);
                        Log.d("Swipe", pressX + "|" +releaseX);
                        toast.show();
                    }
                    else if (releaseX > pressX){
                        Toast toast = Toast.makeText(getApplicationContext(), "You swiped right", Toast.LENGTH_SHORT);
                        Log.d("Swipe", pressX + "|" +releaseX);
                        toast.show();
                    }

                }
                return true;
            }
        });

    }

    //TODO Set up an SQL-Database - can wait
    //TODO Create a generator for cards - important

    @Override
    protected void onResume() {
        super.onResume();
        UtilityClass.hideNavBar(overlay);
    }

    @Override
    public void applyInfo(String head, String comment, String time, String urgency) {
        headingTextView.setText(head);
        commentTextView.setText(comment);
        urgencyTextView.setText(urgency);
        timeTextView.setText(time);
        UtilityClass.hideNavBar(overlay);
    }

    public void openDialog() {
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(getSupportFragmentManager(), "Add an event");
    }


}
