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

public class MiscActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.activity_misc);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.settings);


        //todo come up with some fucking settings

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("TAG", "onOptionsItemSelected: " + item.getItemId());

                switch (item.getItemId()){
                    case R.id.calendar:{
                        Intent intent = new Intent(MiscActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.today:{
                        String date =   UtilityFunctionsClass.getToday();
                        Intent intent = new Intent(MiscActivity.this, DayActivity.class);
                        intent.putExtra("Date", date);
                        startActivity(intent);
                        return true;
                    }

                    case R.id.timeline:{
                        Intent intent = new Intent(MiscActivity.this, TimelineActivity.class);
                        startActivity(intent);
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
