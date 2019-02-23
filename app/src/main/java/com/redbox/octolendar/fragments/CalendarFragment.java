package com.redbox.octolendar.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.redbox.octolendar.R;
import com.redbox.octolendar.utilities.UtilityFunctionsClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

        requestButton = fragmentView.findViewById(R.id.boredButton);
        requestTextView = fragmentView.findViewById(R.id.boredTextView);
        requestTextView.setText("Something you can do");
        new JsonGet().execute(getResources().getString(R.string.string_url));

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonGet().execute(getResources().getString(R.string.string_url));
                requestTextView.setText(jsonResponse);
            }
        });

        return fragmentView;
    }

    private class JsonGet extends AsyncTask<String, String, String> {

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

            BufferedReader bufferedReader = null;
            HttpURLConnection httpURLConnection = null;

            try {
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream stream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buff = new StringBuffer();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    buff.append(line + '\n');
                }
                jsonResponse = fetchStringFromJSON(buff);

                return buff.toString();

            } catch (MalformedURLException exc) {
                exc.printStackTrace();
            } catch (IOException exc) {
                exc.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                    try {
                        if (bufferedReader != null) bufferedReader.close();
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
            return null;
        }

        public String fetchStringFromJSON(StringBuffer buff){
            String fetchedString = "";
            try {
                JSONObject jsonObject = new JSONObject(buff.toString());
                JSONArray jsonArray = jsonObject.names();
                JSONArray valArray = jsonObject.toJSONArray(jsonArray);
                fetchedString = valArray.getString(0);
                return fetchedString;

            } catch (JSONException exc) {
                exc.printStackTrace();
            } catch (NullPointerException exc) {
                exc.printStackTrace();
            }
            return null;
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
