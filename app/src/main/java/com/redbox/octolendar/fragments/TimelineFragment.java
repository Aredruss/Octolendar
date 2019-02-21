package com.redbox.octolendar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.redbox.octolendar.R;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.dialogs.TimelineInfoDialog;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private DatabaseHelper db;
    private List<Event> eventList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View timelineView = inflater.inflate(R.layout.timeline_fragment_layout, container, false);

        db = new DatabaseHelper(getContext());
        eventList = new ArrayList<>();
        eventList = db.getAllEvents();

        ArrayList <TimelineRow> timeline = new ArrayList<>();
        int i = 0;
        for (Event e : eventList){
            TimelineRow timelineRow = new TimelineRow(i);

            if(i % 2 == 0){
                timelineRow.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTimelineBack));
            }

            else{
                timelineRow.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.timelineSecondColor));
            }

            timelineRow.setBellowLineColor(ContextCompat.getColor(getContext(), R.color.timelineBackground));
            timelineRow.setBellowLineSize(12);

            timelineRow.setTitle(e.getTitle());
            timelineRow.setDescription(e.getTime() + " " + e.getDate());
            timelineRow.setImageSize(20);

            timeline.add(timelineRow);
            i++;
        }

        ArrayAdapter<TimelineRow> timelineRowArrayAdapter = new TimelineViewAdapter(getContext(), 0, timeline, false);
        ListView listView = timelineView.findViewById(R.id.timelineListView);
        listView.setAdapter(timelineRowArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Event e = getItem(i);
                TimelineInfoDialog timelineInfoDialog = new TimelineInfoDialog();

                Bundle args = new Bundle();
                args.putString("Date", e.getDate());
                args.putString("Time", e.getTime());
                args.putString("Title",e.getTitle());
                args.putString("Comment", e.getComment());
                args.putString("Urgency", e.getUrgency());

                timelineInfoDialog.setArguments(args);

                timelineInfoDialog.show(getFragmentManager()," ");
            }
        });

        return timelineView;
    }

    public Event getItem(int i){
        return eventList.get(i);
    }
}
