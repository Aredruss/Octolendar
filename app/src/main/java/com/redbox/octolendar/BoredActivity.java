package com.redbox.octolendar;

import android.os.AsyncTask;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.redbox.octolendar.parser.GetJson;

public class BoredActivity extends AppCompatActivity {
    private Button boredRequestButton;
    private TextView adviceTextView;
    private String jsonResponse;
    private BottomSheetDialog bottomSheetDialog;
    private FloatingActionButton configButton;
    private RadioGroup typeRadioGroup;
    private SeekBar participants;
    private String type = "";
    private int participantsNum = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bored);
        boredRequestButton = findViewById(R.id.boredRequestButton);
        adviceTextView = findViewById(R.id.adviceTextView);
        configButton = findViewById(R.id.boredConfigButton);
        bottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = this.getLayoutInflater().inflate(R.layout.bottom_dialog, null);
        bottomSheetDialog.setContentView(sheetView);

        typeRadioGroup = sheetView.findViewById(R.id.typeRadioGroup);
        participants = sheetView.findViewById(R.id.participantsSeekBar);


        new ApiInteraction().execute(urlBuilder());

        boredRequestButton.setOnClickListener((View v) -> {
            new ApiInteraction().execute(urlBuilder());
            Log.d("Tag", "onCreate: " + jsonResponse);
            adviceTextView.setText(jsonResponse);
        });

        configButton.setOnClickListener((View v) -> {
            bottomSheetDialog.show();
            typeRadioGroup.clearCheck();
            participants.setProgress(0);
            participants.setEnabled(true);
            type ="";
            participantsNum = -1;

        });

        typeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.eduRadioButton: {
                        participants.setProgress(0);
                        participants.setEnabled(false);
                        participantsNum = -1;
                        type = "education";
                        break;
                    }
                    case R.id.socialRadioButton: {
                        type = "social";
                        participants.setEnabled(true);
                        break;

                    }
                    case R.id.recRadioButton: {
                        type = "recreational";
                        participants.setEnabled(true);
                        break;
                    }
                }
            }
        });

        participants.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                participantsNum = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private String urlBuilder() {
        String url = getResources().getString(R.string.string_url);

        if (!type.equals("")) {
            url += "?type=" + type;
        }

        if(participantsNum != -1){
            if (!type.equals("")){
                url += "&participants=" + (participantsNum+1);
            } else {
                url += "?participants=" + (participantsNum+1);
            }
        }
        Log.d("T", "urlBuilder: " + url);



        return url;
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

}
