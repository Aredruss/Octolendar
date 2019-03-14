package com.redbox.octolendar;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;

import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.utilities.DateTimeUtilityClass;
import com.redbox.octolendar.utilities.NotificationUtils;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class EventManagerActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText commentEditText;
    private TextView dateTextView;
    private TextView timeStartTextView;
    private TextView timeEndTextView;
    private Spinner urgencySpinner;
    private Button saveButton;
    private ImageButton closeButton;
    private Event openedEvent;
    private Switch timeSwitch;
    private DatabaseHelper db;
    private ImageButton notificationButton;
    private NotificationUtils notificationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manager);

        Intent intent = getIntent();

        db = new DatabaseHelper(getApplicationContext());

        titleEditText = findViewById(R.id.managerTitleEditText);
        commentEditText = findViewById(R.id.managerCommentEditText);
        dateTextView = findViewById(R.id.managerDateTextView);
        timeStartTextView = findViewById(R.id.managerStartTextView);
        timeEndTextView = findViewById(R.id.mangerEndTextView);
        urgencySpinner = findViewById(R.id.managerUrgencySpinner);
        timeSwitch = findViewById(R.id.endTimeSwitch);
        closeButton = findViewById(R.id.managerCloseImageButton);
        saveButton = findViewById(R.id.managerSaveButton);
        notificationButton = findViewById(R.id.notificationImageButton);

        openedEvent = (Event) intent.getSerializableExtra("Event");

        if (openedEvent.getEndTime() == null) {
            timeEndTextView.setEnabled(false);
            timeSwitch.setChecked(false);

        }
        else {
            timeEndTextView.setText(openedEvent.getEndTime());
            timeSwitch.setChecked(true);
        }

        titleEditText.setText(openedEvent.getTitle());
        commentEditText.setText(openedEvent.getComment());
        dateTextView.setText(openedEvent.getDate());
        timeStartTextView.setText(openedEvent.getStartTime());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.urgency_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        urgencySpinner.setAdapter(adapter);

        closeButton.setOnClickListener((View v) -> super.onBackPressed());

        saveButton.setOnClickListener(this::saveChanges);

        timeStartTextView.setOnClickListener((View v) -> setTime(0));
        timeEndTextView.setOnClickListener((View v) -> setTime(1));

        timeSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                timeEndTextView.setEnabled(true);
                if (!isChecked) {
                    timeEndTextView.setEnabled(false);
                    timeEndTextView.setText(getResources().getText(R.string.string_time_select));
                    openedEvent.setEndTime(null);
                }
        });

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String comment = commentEditText.getText().toString();


                    Notification.Builder nb = notificationUtils.getEventChannelNotification(title, comment);
                    notificationUtils.getManager().notify(101, nb.build());

            }
        });
    }

    public void setTime(int type) {
        if (type == 0) DateTimeUtilityClass.openTimeDialog(EventManagerActivity.this, timeStartTextView);
        else DateTimeUtilityClass.openTimeDialog(EventManagerActivity.this, timeEndTextView);
    }

    public void saveChanges(View v) {
        if (!titleEditText.getText().toString().equals("")) {
            openedEvent.setTitle(titleEditText.getText().toString());
            openedEvent.setComment(commentEditText.getText().toString());
            openedEvent.setUrgency(urgencySpinner.getSelectedItem().toString());
            openedEvent.setStartTime(timeStartTextView.getText().toString());

            if (timeSwitch.isChecked()) {
                try {
                    if (!checkTime()) {
                        openedEvent.setEndTime(timeEndTextView.getText().toString());
                        db.updateEvent(openedEvent);
                        super.onBackPressed();
                    }
                } catch (DateTimeParseException exc) {
                    Snackbar s = Snackbar.make(v, "Enter a proper time", Snackbar.LENGTH_SHORT);
                    s.show();
                }

            } else{
                db.updateEvent(openedEvent);
                super.onBackPressed();
            }

        } else {
            Snackbar s = Snackbar.make(v, "Enter a proper title", Snackbar.LENGTH_SHORT);
            s.show();
        }

    }

    public boolean checkTime(){
        LocalTime startTime = DateTimeUtilityClass.getTimeFromString(timeStartTextView.getText().toString());
        LocalTime endTime = DateTimeUtilityClass.getTimeFromString(timeEndTextView.getText().toString());
        boolean timeError = false;

        if (startTime.compareTo(endTime) > 0) {
            timeEndTextView.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));
            timeError = true;
        } else
            timeEndTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.design_default_color_primary));

        return  timeError;
    }
}