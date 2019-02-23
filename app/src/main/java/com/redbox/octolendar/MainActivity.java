package com.redbox.octolendar;

import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.redbox.octolendar.fragments.CalendarFragment;
import com.redbox.octolendar.fragments.MiscFragment;
import com.redbox.octolendar.fragments.PlannedEventsFragment;
import com.redbox.octolendar.fragments.TimelineFragment;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnCalendarInteractionListener {

    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.calendar);

        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.calendar: {

                        currentFragment = new CalendarFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, currentFragment);
                        fragmentTransaction.commit();
                        return true;
                    }

                    case R.id.today: {

                        currentFragment = new PlannedEventsFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, currentFragment);
                        fragmentTransaction.commit();
                        return true;
                    }

                    case R.id.timeline: {

                        currentFragment = new TimelineFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, currentFragment);
                        fragmentTransaction.commit();
                        return true;
                    }

                    case R.id.info: {
                        currentFragment = new MiscFragment();
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.content, currentFragment);
                        fragmentTransaction.commit();
                        return true;
                    }

                    default: {
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
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.calendar);
    }

    @Override
    public void onCalendarInteraction(String date) {
        Bundle bundle = new Bundle();
        bundle.putString("Date", date);

        PlannedEventsFragment plannedEventsFragment = new PlannedEventsFragment();
        plannedEventsFragment.setArguments(bundle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, plannedEventsFragment);
        fragmentTransaction.commit();
    }

}