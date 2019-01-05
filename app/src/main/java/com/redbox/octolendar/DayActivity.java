package com.redbox.octolendar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity implements EventDialog.EventDialogListener {

    private TextView dateTextView;
    private FloatingActionButton button;
    private TextView headingTextView;
    private TextView commentTextView;
    private TextView timeTextView;
    private TextView urgencyTextView;
    private CardView card;
    private View overlay;

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
