package com.redbox.octolendar.fragments;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.redbox.octolendar.EventManagerActivity;
import com.redbox.octolendar.R;
import com.redbox.octolendar.adapters.EventAdapter;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.utilities.RecyclerTouchListener;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlannedEventsFragment extends Fragment {

    private TextView dateTextView;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<Event> eventList;
    private String date;
    private EventAdapter eventAdapter;
    private DatabaseHelper db;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.planned_events_fragment, container, false);

        dateTextView = fragmentView.findViewById(R.id.dateFragmentTextView);
        recyclerView = fragmentView.findViewById(R.id.cardFragmentRecyclerView);
        floatingActionButton = fragmentView.findViewById(R.id.addFragmentFloatingButton);

        eventList = new ArrayList<>();

        date = UtilityFunctionsClass.getToday();

        try {
            date = getArguments().getString("Date");
        }
        catch (NullPointerException exc){
            exc.printStackTrace();
        }

        dateTextView.setText(date);

        db = new DatabaseHelper(getContext());
        getRecyclerViewContent();

        floatingActionButton.setOnClickListener((View v) -> openCreateDialog());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) floatingActionButton.hide();
                if (dy < 0) floatingActionButton.show();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                openActionsDialog(position);
            }

        }
        ));

        return fragmentView;
    }


    //Get Content for the recyclerView
    public void getRecyclerViewContent() {
        if(!eventList.isEmpty()) {
           eventList.clear();
        }
        eventList.addAll(db.getDayEvents(date));
        eventAdapter = new EventAdapter(getContext(), eventList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(eventAdapter);
    }

    //Opens Action Menu
    private void openActionsDialog(final int position) {

        Event openedEvent = eventList.get(position);

        CharSequence options[] = new CharSequence[]{"Edit", "Delete","Manage Notifications"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose an option");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    Intent intent = new Intent(getActivity(), EventManagerActivity.class);
                    Event openedEvent = eventList.get(position);
                    intent.putExtra("Event", openedEvent);
                    startActivity(intent);
                } else if(i ==1){
                    Toast toast = Toast.makeText(getContext(), "The event was deleted", Toast.LENGTH_SHORT);
                    toast.show();
                    db.deleteEvent(openedEvent);
                    getRecyclerViewContent();
                }  else {
                    Toast toast = Toast.makeText(getContext(), "Launch notification management dialogue", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        builder.show();
    }


    //Opens event creation dialog
    private void openCreateDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View dialogView = layoutInflater.inflate(R.layout.event_dialog,null);
        Event newEvent = new Event();

        EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        EditText commentEditText = dialogView.findViewById(R.id.commentEditText);
        RadioGroup urgencyRadioGroup = dialogView.findViewById(R.id.urgencyRadioGroup);
        TextView timeTextView = dialogView.findViewById(R.id.startTextView);


        newEvent.setStartTime(UtilityFunctionsClass.getCurrentTime());
        newEvent.setUrgency("Ugh");

        timeTextView.setOnClickListener((View v) -> {
            TimePickerDialog pickerDialog;
            int hour = 0, minute = 0;
            pickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String timeStr = UtilityFunctionsClass.prepareStringTime(hourOfDay, minute);
                    timeTextView.setText(timeStr);
                    newEvent.setStartTime(timeStr);
                }
            }, hour, minute, true);
            pickerDialog.setTitle("Pick time");
            pickerDialog.show();
        });

        urgencyRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for (int x = 0; x < 2; x++) {
                    RadioButton btn = (RadioButton) urgencyRadioGroup.getChildAt(x);
                    if (btn.getId() == i) {
                        String innerUrgencyType = btn.getText().toString();
                        newEvent.setUrgency(innerUrgencyType);
                    }
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView).setTitle("New Event").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                    if(!titleEditText.getText().toString().isEmpty()){
                        if(commentEditText.getText().toString().isEmpty()){
                            newEvent.setTitle(titleEditText.getText().toString());
                            newEvent.setComment(" ");
                            newEvent.setDate(date);
                            newEvent.setId(db.getEventCount()+1);

                            eventList.add(0, newEvent);
                            eventAdapter.notifyDataSetChanged();

                            db.insertEvent(newEvent);
                            getRecyclerViewContent();
                        }
                        else{
                            newEvent.setTitle(titleEditText.getText().toString());
                            newEvent.setComment(commentEditText.getText().toString());

                            newEvent.setDate(date);
                            newEvent.setId(db.getEventCount()+1);

                            eventList.add(0, newEvent);
                            eventAdapter.notifyDataSetChanged();

                            db.insertEvent(newEvent);
                            getRecyclerViewContent();
                        }
                    }
                    else {
                        Toast toast = Toast.makeText(getContext(), "The title field is not optional", Toast.LENGTH_SHORT);
                        toast.show();
                    }
            }
        });
        builder.show();
    }

    //Add Notifications
    private void openNotificationsDialog(){
    }

}
