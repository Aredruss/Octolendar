package com.redbox.octolendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

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

        openedEvent = (Event) intent.getSerializableExtra("Event");

        closeButton = findViewById(R.id.managerCloseImageButton);
        saveButton = findViewById(R.id.managerSaveButton);

        titleEditText.setText(openedEvent.getTitle());
        commentEditText.setText(openedEvent.getComment());
        dateTextView.setText(openedEvent.getDate());
        timeStartTextView.setText(openedEvent.getTime());

        ArrayAdapter<String> urgencyAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, urgency);
        urgencySpinner.setAdapter(urgencyAdapter);

        closeButton.setOnClickListener((View v) -> super.onBackPressed());

        saveButton.setOnClickListener((View v) -> {
        });

        timeStartTextView.setOnClickListener( (View v) -> setTime(0));
        timeEndTextView.setOnClickListener( (View v) -> setTime(1));
    }

    public void setTime(int type) {
        TimePickerDialog pickerDialog;
        int hour = 0, minute = 0;
        pickerDialog = new TimePickerDialog(EventManagerActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
               String timeStr = UtilityFunctionsClass.prepareStringTime(hourOfDay, minute);

               if(type == 0) timeStartTextView.setText(timeStr);
               else timeEndTextView.setText(timeStr);
            }
        }, hour, minute, true);
        pickerDialog.setTitle("Pick time");
        pickerDialog.show();
    }
}