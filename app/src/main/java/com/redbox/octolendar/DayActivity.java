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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

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
                //TODO Create a dialog for editing events

                return true;
            }
        });

        hideNavBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavBar();
    }

    @Override
    public void applyInfo(String head, String comment, String time, String urgency) {
        headingTextView.setText(head);
        commentTextView.setText(comment);
        urgencyTextView.setText(urgency);
        timeTextView.setText(time);
        hideNavBar();
    }

    public void hideNavBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    public void openDialog() {
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(getSupportFragmentManager(), "Add an event");
        hideNavBar();
    }

}
