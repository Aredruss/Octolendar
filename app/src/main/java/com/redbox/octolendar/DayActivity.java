package com.redbox.octolendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.redbox.octolendar.adapters.EventAdapter;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.utilities.RecyclerTouchListener;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

import java.util.ArrayList;
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
                openDialog();
            }
        });

        db = new DatabaseHelper(this);

        eventList.addAll(db.getDayEvents(date));


        eAdapter = new EventAdapter(this, eventList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(eAdapter);


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


        UtilityFunctionsClass.hideNavBar(overlay);
    }

    private void createNote(Event event) {
        long id = db.insertEvent(event);
        event = db.getEvent(id);
        eventList.add(0, event);
        eAdapter.notifyDataSetChanged();

    }

    private void openActionsDialog(final int position){
        CharSequence options[] = new CharSequence[]{"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                  Toast toast =  Toast.makeText(getApplicationContext(), "Edit the event", Toast.LENGTH_SHORT);
                  toast.show();
                }
                else {
                    Toast toast =  Toast.makeText(getApplicationContext(), "Delete the event", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UtilityFunctionsClass.hideNavBar(overlay);
        db.getDayEvents(date);

    }

    @Override
    public void applyInfo(Event event) {
        if (event != null) {
            event.setDate(date);
            event.setId(db.getEventCount()+1);
            createNote(event);
            Log.d("apply", "applyInfo: " + event.getDate() + " " + event.getTime() + " " + event.getId());

        }
    }

    public void openDialog() {
        EventDialog eventDialog = new EventDialog();
        eventDialog.show(getSupportFragmentManager(), "Add an event");
    }



}
