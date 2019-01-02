package com.redbox.octolendar;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DayActivity extends AppCompatActivity implements AddDialog.AddDialogListener {

    private TextView textView;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        textView = findViewById(R.id.dateTextView);
        Intent intentIncoming = getIntent();
        String date = intentIncoming.getStringExtra("Date");
        textView.setText(date);
        button = findViewById(R.id.addFloatingButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        hideNavBar();

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavBar();
    }

    @Override
    public void applyInfo(String head, String comment, String time, String urgency) {

    }

    public void hideNavBar() {
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    public void openDialog() {
        AddDialog addDialog = new AddDialog();
        addDialog.show(getSupportFragmentManager(), "Add an event");

    }

}
