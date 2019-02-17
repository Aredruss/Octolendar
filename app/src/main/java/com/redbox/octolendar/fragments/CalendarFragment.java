package com.redbox.octolendar.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.redbox.octolendar.R;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class CalendarFragment extends Fragment {

    private String percentString;
    private CalendarView calendarView;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private String date;

    private OnCalendarInteractionListener interactionListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.calendar_fragment_layout, container, false);

        calendarView = fragmentView.findViewById(R.id.fragmentCalendarView);
        progressTextView = fragmentView.findViewById(R.id.monthProgressTextView);
        progressBar = fragmentView.findViewById(R.id.monthProgressBar);
        percentString = "%d%% of the month has passed";
        date = UtilityFunctionsClass.getToday();

        progressTextView.setText(String.format(percentString, UtilityFunctionsClass.getMonthProgress()));
        progressBar.setProgress(UtilityFunctionsClass.getMonthProgress());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
                date = day + "-" + (month + 1) + "-" + year;
                sendDate();
            }
        });
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
}
