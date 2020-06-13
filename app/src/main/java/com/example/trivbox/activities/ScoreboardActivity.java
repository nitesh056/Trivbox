package com.example.trivbox.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.trivbox.fragments.LeaderboardFragment;
import com.example.trivbox.R;
import com.example.trivbox.fragments.ScoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScoreboardActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        bottomNav = findViewById(R.id.nav_bottom_id);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, new ScoreFragment()).commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.score_id:
                            selectedFragment = new ScoreFragment();
                            break;

                        case R.id.leaderboard_id:
                            selectedFragment = new LeaderboardFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_id, selectedFragment).commit();
                    return true;
                }
            };

}
