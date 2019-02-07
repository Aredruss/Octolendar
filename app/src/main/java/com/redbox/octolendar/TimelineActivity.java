package com.redbox.octolendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class TimelineActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.timeline_icon);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("TAG", "onOptionsItemSelected: " + item.getItemId());

                switch (item.getItemId()){
                    case R.id.calendar_icon:{
                        Intent intent = new Intent(TimelineActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.today_icon:{
                        String date =   UtilityFunctionsClass.getToday();
                        Intent intent = new Intent(TimelineActivity.this, DayActivity.class);
                        intent.putExtra("Date", date);
                        startActivity(intent);
                        return true;
                    }

                    case R.id.settings_icon:{
                        Intent intent = new Intent(TimelineActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    default:{
                        return true;
                    }
                }

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.timeline_icon);
        overridePendingTransition(0, 0);
    }

}
