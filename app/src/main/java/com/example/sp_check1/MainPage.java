package com.example.sp_check1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainPage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private GatePageFragment gatePageFragment = new GatePageFragment();
    private BookingPageFragment bookingPagefragment = new BookingPageFragment();
    private ProfilePageFragment profilePageFragment = new ProfilePageFragment();
    private StatisticPageFragment statisticPageFragment = new StatisticPageFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_page);
        bottomNavigationView  = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, gatePageFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();


                if (itemId == R.id.home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, gatePageFragment).commit();

                    return true;

                } else if (itemId == R.id.statistic) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, statisticPageFragment).commit();
                    return true;
                }   else if (itemId == R.id.book) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,bookingPagefragment).commit();
                    return true;
                }
                else if (itemId == R.id.profile) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profilePageFragment).commit();
                    return true;
                }

                return false;
            }
        });

    }
}