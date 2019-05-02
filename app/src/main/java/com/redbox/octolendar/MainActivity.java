package com.redbox.octolendar;

import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.redbox.octolendar.fragments.CalendarFragment;
import com.redbox.octolendar.fragments.MiscFragment;
import com.redbox.octolendar.fragments.PlannedEventsFragment;
import com.redbox.octolendar.fragments.TimelineFragment;

public class MainActivity extends AppCompatActivity implements CalendarFragment.OnCalendarInteractionListener, TimelineFragment.OnInfoButtonClickListener {

    private BottomNavigationView bottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;
    public FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.content);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.calendar);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        /* Fragment manager which allows us to load other fragments via NavBar */

        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

            switch (item.getItemId()) {
                case R.id.calendar: {
                    currentFragment = new CalendarFragment();
                    break;
                }

                case R.id.today: {
                    currentFragment = new PlannedEventsFragment();
                    break;
                }

                case R.id.timeline: {
                    currentFragment = new TimelineFragment();
                    break;
                }

                default: {
                    break;
                }
            }
            fragmentTransaction.replace(R.id.content, currentFragment);
            fragmentTransaction.commit();
            return true;
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (frameLayout.getChildAt(0).equals(findViewById(R.id.calendarRelativeLayout))) finish();

        getSupportFragmentManager().popBackStack();
        bottomNavigationView.setSelectedItemId(R.id.calendar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigationView.setSelectedItemId(R.id.calendar);
    }


    /*Because of the fact that calendarFragment is the "Home page" sorts, we implement a listener
    for interactions and launching other fragment.
     */
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

    @Override
    public void OnInfoButtonClickListener() {
        MiscFragment miscFragment = new MiscFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, miscFragment);
        fragmentTransaction.commit();
    }
}