package com.redbox.octolendar.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.redbox.octolendar.R;
import com.redbox.octolendar.database.DatabaseHelper;
import com.redbox.octolendar.database.model.Event;
import com.redbox.octolendar.dialogs.TimelineInfoDialog;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    private DatabaseHelper db;
    private List<Event> eventList;
    private SearchView searchView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View timelineView = inflater.inflate(R.layout.timeline_fragment, container, false);

        getTimeLineContent();

        ListView listView = timelineView.findViewById(R.id.timelineListView);
        searchView = timelineView.findViewById(R.id.eventSearchView);

        ArrayList<TimelineRow> timeline = new ArrayList<>();
        int i = 0;
        for (Event e : eventList) {

            TimelineRow timelineRow = new TimelineRow(i);

            timelineRow.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTimelineIcon));

            timelineRow.setBellowLineColor(ContextCompat.getColor(getContext(), R.color.colorTimelineLineColor));
            timelineRow.setBellowLineSize(15);

            timelineRow.setTitle(e.getTitle());
            timelineRow.setDescription(e.getDate());
            timelineRow.setImageSize(30);

            timeline.add(timelineRow);
            i++;
        }

        ArrayAdapter<TimelineRow> timelineRowArrayAdapter = new TimelineViewAdapter(getContext(), 0, timeline, false);

        listView.setAdapter(timelineRowArrayAdapter);

        listView.setOnItemClickListener((AdapterView<?> adapterView, View view, int index, long l) -> {
            final Event e = getItem(index);
            TimelineInfoDialog timelineInfoDialog = new TimelineInfoDialog();

            Bundle args = new Bundle();
            args.putString("Date", e.getDate());
            if (e.getEndTime() != null)
                args.putString("Time", e.getStartTime() + "-" + e.getEndTime());
            else args.putString("Time", e.getStartTime());
            args.putString("Title", e.getTitle());
            args.putString("Comment", e.getComment());
            args.putString("Completed", String.valueOf(e.getCompleted()));

            timelineInfoDialog.setArguments(args);

            try {
                timelineInfoDialog.show(getFragmentManager(), " ");
            } catch (NullPointerException exc) {
                exc.printStackTrace();
            }

        });

        //todo implement database interactions
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnClickListener((View v) -> {

        });

        return timelineView;
    }

    public void getTimeLineContent() {
        db = new DatabaseHelper(getContext());
        eventList = new ArrayList<>();
        eventList = db.getAllEvents();
    }

    public Event getItem(int i) {
        return eventList.get(i);
    }
}
