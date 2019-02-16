package com.redbox.octolendar.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.redbox.octolendar.DayActivity;
import com.redbox.octolendar.R;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class CalendarFragment extends Fragment {

    private String percentString;
    private CalendarView calendarView;
    private ProgressBar progressBar;
    private TextView progressTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //todo Replace ALL activities with fragments

        View fragmentView = inflater.inflate(R.layout.calendar_fragment_layout, container, false);

        calendarView = fragmentView.findViewById(R.id.fragmentCalendarView);
        progressTextView = fragmentView.findViewById(R.id.monthProgressTextView);
        progressBar = fragmentView.findViewById(R.id.monthProgressBar);
        percentString = "%d%% of the month has passed";

        progressTextView.setText(String.format(percentString, UtilityFunctionsClass.getMonthProgress()));
        progressBar.setProgress(UtilityFunctionsClass.getMonthProgress());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {

                //todo send info to the planned events fragment

                String date = day + "-" + (month + 1) + "-" + year;
                Intent intent = new Intent(getContext(), DayActivity.class);
                intent.putExtra("Date", date);
                startActivity(intent);
            }
        });


        return fragmentView;
    }
}
