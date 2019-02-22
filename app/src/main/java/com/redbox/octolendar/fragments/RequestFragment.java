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
import android.widget.TextView;
import android.widget.Toast;

import com.redbox.octolendar.R;

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


public class RequestFragment extends Fragment {

    private TextView requestTextView;
    private Button requestButton;
    private String jsonResponse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Context context = getContext();

        View view = inflater.inflate(R.layout.request_fragment, container, false);

        requestButton = view.findViewById(R.id.boredButton);
        requestTextView = view.findViewById(R.id.boredTextView);
        requestTextView.setText("Something you can do");

        new JsonGet().execute(getResources().getString(R.string.string_url));

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonGet().execute(getResources().getString(R.string.string_url));
                requestTextView.setText(jsonResponse);
            }
        });

        return view;
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
}