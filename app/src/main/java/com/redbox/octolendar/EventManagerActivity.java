package com.redbox.octolendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TimePicker;

import com.allyants.notifyme.NotifyMe;
import com.redbox.octolendar.singleton.App;
import com.redbox.octolendar.utilities.DateTimeUtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

/*An activity for managing event's info */

public class EventManagerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText titleEditText;
    private EditText commentEditText;
    private TextView dateTextView;
    private TextView timeStartTextView;
    private TextView timeEndTextView;
    private Spinner urgencySpinner;
    private Button saveButton;
    private ImageButton closeButton;
    private App.Event openedEvent;
    private Switch timeSwitch;
    private TextView notificationTextView;
    private ImageButton notificationButton;
    private App.EventDatabase database;
    private App.EventDao dao;

    private Calendar now = Calendar.getInstance();
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manager);

        Intent intent = getIntent();

        database = App.getInstance().getEventDatabase();
        dao = database.eventDao();

        titleEditText = findViewById(R.id.managerTitleEditText);
        commentEditText = findViewById(R.id.managerCommentEditText);
        dateTextView = findViewById(R.id.managerDateTextView);
        timeStartTextView = findViewById(R.id.managerStartTextView);
        timeEndTextView = findViewById(R.id.mangerEndTextView);
        urgencySpinner = findViewById(R.id.managerUrgencySpinner);
        timeSwitch = findViewById(R.id.endTimeSwitch);
        closeButton = findViewById(R.id.managerCloseImageButton);
        saveButton = findViewById(R.id.managerSaveButton);
        notificationTextView = findViewById(R.id.notificationInfoTextView);
        notificationButton = findViewById(R.id.notificationButton);

        openedEvent = (App.Event) intent.getSerializableExtra("Event");

        // We check if opened event has a duration
        if (openedEvent.timeEnd == null) {
            timeEndTextView.setEnabled(false);
            timeSwitch.setChecked(false);

        } else {
            timeEndTextView.setText(openedEvent.timeEnd);
            timeSwitch.setChecked(true);
        }

        titleEditText.setText(openedEvent.title);
        commentEditText.setText(openedEvent.comment);
        dateTextView.setText(openedEvent.getDate());
        timeStartTextView.setText(openedEvent.timeStart);

        // We load info to spinner which contains event's urgency info
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.urgency_string_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        urgencySpinner.setAdapter(adapter);

        //Close and Save button
        closeButton.setOnClickListener((View v) -> super.onBackPressed());
        saveButton.setOnClickListener(this::saveChanges);

        //We call function from DateTimeUtility class, a separate method for the timepicker dialog
        timeStartTextView.setOnClickListener((View v) -> setTime(timeStartTextView));
        timeEndTextView.setOnClickListener((View v) -> setTime(timeEndTextView));

        //Applying listener for the event-has-a-duration switch
        timeSwitch.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            timeEndTextView.setEnabled(true);
            if (!isChecked) {
                timeEndTextView.setEnabled(false);
                timeEndTextView.setText(getResources().getText(R.string.string_time_select));
                openedEvent.timeEnd = null;
            }
        });

        //We init. two dialogs for the notification params
        datePickerDialog = new DatePickerDialog(this,
                EventManagerActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this,
                EventManagerActivity.this,
                12, 0, true);

        notificationButton.setOnClickListener((View v) -> {
            datePickerDialog.show();
        });
    }

    //We set Calendar's fields via DatePicker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month);
        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        timePickerDialog.show();
    }

    //We set Calendar's fields via TimePicker
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        now.set(Calendar.HOUR_OF_DAY, hourOfDay);
        now.set(Calendar.MINUTE, minute);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        notificationTextView.setText(DateTimeUtilityClass.prepareDateTime(now));

        NotifyMe.Builder notifyMe = new NotifyMe.Builder(getApplicationContext());

        notifyMe.title(openedEvent.title)
                .small_icon(R.drawable.ic_action_notification)
                .time(now)
                .addAction(intent, "Go to calendar", true)
                .key("event");

        notifyMe.build();
    }


    //This method serves to call separate TimePicker from DateTime UtilityClass
    public void setTime(TextView t) {
        DateTimeUtilityClass.openTimeDialog(EventManagerActivity.this, t);
    }

    //This method gets info our user typed into all the fields and writes it to Event object and to the database
    public void saveChanges(View v) {
        //Check if title is empty
        if (!titleEditText.getText().toString().equals("")) {
            openedEvent.title = titleEditText.getText().toString();
            openedEvent.comment = commentEditText.getText().toString();
            openedEvent.urgency = urgencySpinner.getSelectedItem().toString();
            openedEvent.timeStart = timeStartTextView.getText().toString();

            //Check user has selected the proper end time
            if (timeSwitch.isChecked()) {
                try {
                    if (!checkTime()) {
                        openedEvent.timeEnd = timeEndTextView.getText().toString();
                        dao.update(openedEvent);
                        super.onBackPressed();
                    }
                } catch (DateTimeParseException exc) {
                    Snackbar s = Snackbar.make(v, "Enter a proper time", Snackbar.LENGTH_SHORT);
                    s.show();
                }

            } else {
                dao.update(openedEvent);
                super.onBackPressed();
            }

        } else {
            Snackbar s = Snackbar.make(v, "Enter a proper title", Snackbar.LENGTH_SHORT);
            s.show();
        }

    }

    //Compare endTime and startTime to check if endTime does not "happen" before the startTime
    public boolean checkTime() {
        LocalTime startTime = DateTimeUtilityClass.getTimeFromString(timeStartTextView.getText().toString());
        LocalTime endTime = DateTimeUtilityClass.getTimeFromString(timeEndTextView.getText().toString());
        boolean timeError = false;

        if (startTime.compareTo(endTime) > 0) {
            timeEndTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
            timeError = true;
        } else
            timeEndTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.design_default_color_primary));

        return timeError;
    }
}