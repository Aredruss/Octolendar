package com.redbox.octolendar;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import android.widget.TimePicker;

import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

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
    private String[] urgency = {"It's important", "Ugh", "You can skip it"};

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

        openedEvent = (Event) intent.getSerializableExtra("Event");

        if (openedEvent.getEndTime() == null) {
            timeEndTextView.setEnabled(false);
            timeSwitch.setChecked(false);

        }
        else {
            timeEndTextView.setText(openedEvent.getEndTime());
            timeSwitch.setChecked(true);
        }

        closeButton = findViewById(R.id.managerCloseImageButton);
        saveButton = findViewById(R.id.managerSaveButton);

        titleEditText.setText(openedEvent.getTitle());
        commentEditText.setText(openedEvent.getComment());
        dateTextView.setText(openedEvent.getDate());
        timeStartTextView.setText(openedEvent.getStartTime());

        ArrayAdapter<String> urgencyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, urgency);
        urgencySpinner.setAdapter(urgencyAdapter);

        closeButton.setOnClickListener((View v) -> super.onBackPressed());

        saveButton.setOnClickListener((View v) -> {
            saveChanges(v);
        });

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
    }

    public void setTime(int type) {
        TimePickerDialog pickerDialog;
        int o_hour = 0, o_minute = 0;
        pickerDialog = new TimePickerDialog(EventManagerActivity.this, (TimePicker view, int hourOfDay, int minute)-> {

                String timeStr = UtilityFunctionsClass.prepareStringTime(hourOfDay, minute);

                if (type == 0) timeStartTextView.setText(timeStr);
                else timeEndTextView.setText(timeStr);

        }, o_hour, o_minute, true);
        pickerDialog.setTitle("Pick time");
        pickerDialog.show();
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
        LocalTime startTime = UtilityFunctionsClass.getTimeFromString(timeStartTextView.getText().toString());
        LocalTime endTime = UtilityFunctionsClass.getTimeFromString(timeEndTextView.getText().toString());
        boolean timeError = false;

        if (startTime.compareTo(endTime) > 0) {
            timeEndTextView.setTextColor(getResources().getColor(R.color.colorAccent));
            timeError = true;
        } else
            timeEndTextView.setTextColor(getResources().getColor(R.color.colorCheck));

        return  timeError;
    }
}