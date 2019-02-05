package com.redbox.octolendar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.redbox.octolendar.adapters.EventAdapter;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.utilities.RecyclerTouchListener;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DayActivity extends AppCompatActivity implements EventDialog.EventDialogListener {

    private TextView dateTextView;
    private FloatingActionButton button;
    private RecyclerView recyclerView;
    private View overlay;
    private DatabaseHelper db;
    private EventAdapter eAdapter;
    private List<Event> eventList = new ArrayList<>();
    private String date;
    private int pressX;
    private int releaseX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        overlay = findViewById(R.id.dayRelativeLayout);
        button = findViewById(R.id.addFloatingButton);
        recyclerView = findViewById(R.id.cardRecyclerView);

        dateTextView = findViewById(R.id.dateTextView);
        Intent intentIncoming = getIntent();
        date = intentIncoming.getStringExtra("Date");
        dateTextView.setText(date);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });

        db = new DatabaseHelper(this);

        getRecyclerViewContent();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("TOUCH", "onClick: " + position);
            }

            @Override
            public void onLongClick(View view, int position) {
                openActionsDialog(position);
            }
        }
        ));

        dateTextView.setOnTouchListener(new View.OnTouchListener() {
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
                        String next  = UtilityFunctionsClass.getNextDay(date);
                        dateTextView.setText(next);

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

    private void createNote(Event event) {
        long id = db.insertEvent(event);
        event = db.getEvent(id);
        eventList.add(0, event);
        eAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        db.getDayEvents(date);

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void applyInfo(Event event) {
        if (event != null) {
            event.setDate(date);
            event.setId(db.getEventCount() + 1);
            createNote(event);
            Log.d("apply", "applyInfo: " + event.getDate() + " " + event.getTime() + " " + event.getId());

        }
    }

    public void openAddDialog() {
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(getSupportFragmentManager(), "Add an event");
    }

    private void openActionsDialog(final int position) {

        Event openedEvent = eventList.get(position);

        CharSequence options[] = new CharSequence[]{"Edit", "Delete","Manage Notifications"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    openEditDialog(i);
                } else if(i ==1){
                    Toast toast = Toast.makeText(getApplicationContext(), "The event was deleted", Toast.LENGTH_SHORT);
                    toast.show();
                    db.deleteEvent(openedEvent);
                    getRecyclerViewContent();
                }  else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Launch notification management dialogue", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        builder.show();
    }

    public void openEditDialog(final int position) {

        Event openedEvent = eventList.get(position);
        String time = openedEvent.getTime();
        Log.d("Time", "openEditDialog: " + time);
        String urgencyType = openedEvent.getUrgency();

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View view;
        view = inflater.inflate(R.layout.add_event_dialog, null);

        EditText titleEditText = view.findViewById(R.id.titleEditText);
        EditText commentEditText = view.findViewById(R.id.commentEditText);
        RadioGroup urgencyRadioGroup = view.findViewById(R.id.urgencyRadioGroup);
        TimePicker timePicker = view.findViewById(R.id.timePicker);

        titleEditText.setText(openedEvent.getTitle());
        commentEditText.setText(openedEvent.getComment());

        int urgencyChildrenCount = urgencyRadioGroup.getChildCount();
        timePicker.setIs24HourView(true);


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minute) {
                String innerTime = UtilityFunctionsClass.prepareStringTime(hour, minute);
                openedEvent.setTime(innerTime);
            }
        });

        urgencyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for (int x = 0; x < urgencyChildrenCount; x++) {
                    RadioButton btn = (RadioButton) urgencyRadioGroup.getChildAt(x);
                    if (btn.getId() == i) {
                        String innerUrgencyType = btn.getText().toString();
                        openedEvent.setUrgency(innerUrgencyType);
                    }
                }
            }
        });

        int[] prevTime = UtilityFunctionsClass.getIntTime(time);
        timePicker.setHour(prevTime[0]);
        timePicker.setMinute(prevTime[1]);

        for (int x = 0; x < urgencyChildrenCount; x++) {
            RadioButton btn = (RadioButton) urgencyRadioGroup.getChildAt(x);
            if (urgencyType.equals(btn.getText().toString())) {
                Log.d("urgency", "openEditDialog: " + urgencyType.equals(btn.getText().toString()) );
                btn.setChecked(true);
            }
            else {  Log.d("urgency", "openEditDialog: " + urgencyType.equals(btn.getText().toString()) + " not");}
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).setTitle("Edit the event").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openedEvent.setTitle(titleEditText.getText().toString());
                openedEvent.setComment(commentEditText.getText().toString());

                db.updateEvent(openedEvent);
                getRecyclerViewContent();
            }
        });

        builder.show();

        getRecyclerViewContent();

    }

    public void getRecyclerViewContent() {

        Iterator<Event> iter = eventList.iterator();
        while (iter.hasNext()) {
            Event e = iter.next();
            iter.remove();
        }
        eventList.addAll(db.getDayEvents(date));
        eAdapter = new EventAdapter(this, eventList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(eAdapter);
    }
}


