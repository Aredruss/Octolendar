package com.redbox.octolendar.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetJson {

    public static String getInfoFromUrl(String... strings) {
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

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            SampleEvent sampleEvent = gson.fromJson(buff.toString(), SampleEvent.class);

            return sampleEvent.activity;

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
        return "Json not Parsed";
    }
}

