package com.redbox.octolendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.redbox.octolendar.utilities.UtilityFunctionsClass;

public class SettingsActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.settings_icon);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("TAG", "onOptionsItemSelected: " + item.getItemId());

                switch (item.getItemId()){
                    case R.id.calendar_icon:{
                        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.today_icon:{
                        String date =   UtilityFunctionsClass.getToday();
                        Intent intent = new Intent(SettingsActivity.this, DayActivity.class);
                        intent.putExtra("Date", date);
                        startActivity(intent);
                        return true;
                    }

                    case R.id.timeline_icon:{
                        Intent intent = new Intent(SettingsActivity.this, TimelineActivity.class);
                        startActivity(intent);
                    }
                    case R.id.settings_icon:{
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        menu.setQwertyMode(true);
        return true;
    }
}
