package com.redbox.octolendar.fragments;

import android.content.Context;
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

import com.redbox.octolendar.R;
import com.redbox.octolendar.parser.GetJson;
import com.redbox.octolendar.utilities.DateTimeUtilityClass;

public class CalendarFragment extends Fragment {

    private String percentString;
    private CalendarView calendarView;
    private ProgressBar progressBar;
    private TextView progressTextView;
    private String date;
    private TextView requestTextView;
    private Button requestButton;
    private String jsonResponse;

    private OnCalendarInteractionListener interactionListener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.calendar_fragment_layout, container, false);

        calendarView = fragmentView.findViewById(R.id.fragmentCalendarView);
        progressTextView = fragmentView.findViewById(R.id.monthProgressTextView);
        progressBar = fragmentView.findViewById(R.id.monthProgressBar);
        percentString = "%d%% of the month has passed";
        date = DateTimeUtilityClass.getToday();

        progressTextView.setText(String.format(percentString, DateTimeUtilityClass.getMonthProgress()));
        progressBar.setProgress(DateTimeUtilityClass.getMonthProgress());

        calendarView.setOnDateChangeListener((CalendarView calendarView, int year, int month, int day)-> {
                date = day + "-" + (month + 1) + "-" + year;
                sendDate();
        });

        requestButton = fragmentView.findViewById(R.id.boredButton);
        requestTextView = fragmentView.findViewById(R.id.boredTextView);
        requestTextView.setText("Something you can do");
        new ApiInteraction().execute(getResources().getString(R.string.string_url));

        requestButton.setOnClickListener((View v) -> {
                new ApiInteraction().execute(getResources().getString(R.string.string_url));
                requestTextView.setText(jsonResponse);
        });
        return fragmentView;
    }

    private class ApiInteraction extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            jsonResponse = GetJson.getInfoFromUrl(strings);
            return "Launch success";
        }
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
