package com.redbox.octolendar.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.redbox.octolendar.BoredActivity;
import com.redbox.octolendar.MainActivity;
import com.redbox.octolendar.R;
import com.redbox.octolendar.parser.GetJson;
import com.redbox.octolendar.singleton.App;
import com.redbox.octolendar.utilities.DateTimeUtilityClass;

public class CalendarFragment extends Fragment {

    private String percentString= "%d%% of the month has passed";
    private CalendarView calendarView;
    private ProgressBar progressBar;
    private Button boredButton;
    private TextView progressTextView;
    private TextView countTextView;
    private String date;

    private App.EventDatabase database;
    private App.EventDao dao;
    private int count;
    private int completed;

    private OnCalendarInteractionListener interactionListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.calendar_fragment, container, false);

        countTextView = fragmentView.findViewById(R.id.countTextView);
        calendarView = fragmentView.findViewById(R.id.fragmentCalendarView);
        progressTextView = fragmentView.findViewById(R.id.monthProgressTextView);
        progressBar = fragmentView.findViewById(R.id.monthProgressBar);
        boredButton = fragmentView.findViewById(R.id.boredRequestButton);

        date = DateTimeUtilityClass.getToday();
        setStatistics();

        progressTextView.setText(String.format(percentString, DateTimeUtilityClass.getMonthProgress()));
        progressBar.setProgress(DateTimeUtilityClass.getMonthProgress());

        calendarView.setOnDateChangeListener((CalendarView calendarView, int year, int month, int day) -> {
            date = day + "-" + (month + 1) + "-" + year;
            sendDate();
        });

        boredButton.setOnClickListener((v -> {
            Intent intent = new Intent(v.getContext(), BoredActivity.class);
            startActivity(intent);
        }));

        return fragmentView;
    }

    public interface OnCalendarInteractionListener {
        void onCalendarInteraction(String date);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interactionListener = (OnCalendarInteractionListener) context;
        } catch (ClassCastException exc) {
            throw new ClassCastException(context.toString() + " must implement OnCalendarInteractionListener! \n");
        }
    }

    public void sendDate() {
        interactionListener.onCalendarInteraction(date);
    }

    public void setStatistics(){
        String countString = "You have %d planned events";
        App.EventDatabase database = App.getInstance().getEventDatabase();
        App.EventDao dao = database.eventDao();

        count = dao.getAll().size();
        if(count ==1 ) {
          countString = countString.substring(0,countString.length()-1);
        }

        countTextView.setText(String.format(countString, count));
    }

}
