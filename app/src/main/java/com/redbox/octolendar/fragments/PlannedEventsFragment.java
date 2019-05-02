package com.redbox.octolendar.fragments;

import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.redbox.octolendar.R;
import com.redbox.octolendar.adapters.EventAdapter;
import com.redbox.octolendar.singleton.App;
import com.redbox.octolendar.utilities.RecyclerTouchListener;
import com.redbox.octolendar.utilities.DateTimeUtilityClass;

import java.util.ArrayList;
import java.util.List;

public class PlannedEventsFragment extends Fragment {

    private TextView dateTextView;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<App.Event> eventList = new ArrayList<>();
    private String date;
    private EventAdapter eventAdapter;
    private App.EventDatabase database;
    private App.EventDao dao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.planned_events_fragment, container, false);

        dateTextView = fragmentView.findViewById(R.id.dateFragmentTextView);
        recyclerView = fragmentView.findViewById(R.id.cardFragmentRecyclerView);
        floatingActionButton = fragmentView.findViewById(R.id.addFragmentFloatingButton);

        date = DateTimeUtilityClass.getToday();

        try {
            date = getArguments().getString("Date");
        } catch (NullPointerException exc) {
            exc.printStackTrace();
        }

        database = App.getInstance().getEventDatabase();
        dao = database.eventDao();

        dateTextView.setText(date);

        getRecyclerViewContent();

        floatingActionButton.setOnClickListener((View v) -> openCreateDialog());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) floatingActionButton.hide();
                else floatingActionButton.show();
            }
        });

        /*This is a very problematic part. I don't think it's the right way, and yet I can't find any other working solution

            The problem is that I can't notify Recycler View from the ViewHolder, and if I don't, user will have to reload fragment to see the changes.
        */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {

            //Deleting the event from recyclerView
            @Override
            public void onClick(View view, int position) {
                EventAdapter.ViewHolder childViewHolder = (EventAdapter.ViewHolder) recyclerView.getChildViewHolder(view);
                ImageButton deleteButton = childViewHolder.getDeleteButton();

                deleteButton.setOnClickListener((View v) -> {
                    App.Event event = eventList.get(position);
                    dao.delete(event);
                    eventAdapter.notifyItemRemoved(position);

                    Toast toast = Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT);
                    toast.show();

                    getRecyclerViewContent();
                });
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }
        ));


        return fragmentView;
    }

    //Get Content for the recyclerView [A basic reload]
    public void getRecyclerViewContent() {
        eventList.clear();

        String[] dateArray = date.split("-");
        eventList = dao.getDayEvents(dateArray[0], dateArray[1], dateArray[2]);

        for(App.Event e: eventList){
            Log.e("EVENT ", "getRecyclerViewContent: " + e.title);
        }

        eventAdapter = new EventAdapter(getContext(), eventList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(eventAdapter);

        eventAdapter.notifyDataSetChanged();
    }

    //Opens event creation dialog
    private void openCreateDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View dialogView = layoutInflater.inflate(R.layout.event_dialog, null);
        App.Event newEvent = new App.Event();

        EditText titleEditText = dialogView.findViewById(R.id.titleEditText);
        EditText commentEditText = dialogView.findViewById(R.id.commentEditText);
        RadioGroup urgencyRadioGroup = dialogView.findViewById(R.id.urgencyRadioGroup);
        TextView timeTextView = dialogView.findViewById(R.id.startTextView);

        newEvent.urgency = "Ugh";

        timeTextView.setOnClickListener((View v) -> {
            DateTimeUtilityClass.openTimeDialog(getContext(), timeTextView);
        });

        urgencyRadioGroup.setOnCheckedChangeListener((RadioGroup radioGroup, int i) -> {
            for (int x = 0; x < 3; x++) {
                RadioButton btn = (RadioButton) urgencyRadioGroup.getChildAt(x);
                if (btn.getId() == i) {
                    newEvent.urgency = btn.getText().toString();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView).setTitle("New Event").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Ok", (DialogInterface dialogInterface, int i) -> {
            if (!titleEditText.getText().toString().isEmpty()) {
                newEvent.title = titleEditText.getText().toString();
                newEvent.setDate(date);
                newEvent.id = dao.getAvID()+1;

                if (timeTextView.getText().toString().equals("Select time"))
                    newEvent.timeStart = DateTimeUtilityClass.getCurrentTime();
                else newEvent.timeStart = timeTextView.getText().toString();

                if (commentEditText.getText().toString().isEmpty()) {
                    newEvent.comment = "";
                } else {
                    newEvent.comment = commentEditText.getText().toString();

                }

                dao.insert(newEvent);
                getRecyclerViewContent();

                Log.e("T", "openCreateDialog: " + eventList.isEmpty());

            } else {
                Toast toast = Toast.makeText(getContext(), "The title field is not optional", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        builder.show();
    }
}
