package com.java.zhuli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.java.zhuli.Models.Data;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity implements FragmentCommunicator{

    TextView text;
    ArrayList<Integer> numbers;
    BottomNavigationView bottomNavigationView, nav_view;

    DetailFragment detailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // History and Saved
        bottomNavigationView = findViewById(R.id.bottom_nav);
        nav_view = findViewById(R.id.nav_bar);
        SavedFragment savedFragment = new SavedFragment();
        HistoryFragment historyFragment = new HistoryFragment();

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.personal);

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.personal:
                        return true;
                }
                return false;
            }
        });

        nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.saved){
                    setFragment(savedFragment);
                    return true;
                }
                else if (id == R.id.history){
                    setFragment(historyFragment);
                    return true;
                }
                return false;
            }
        });

        nav_view.setSelectedItemId(R.id.saved);

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void sendObject(Data data) {
        detailFragment = new DetailFragment();
        Bundle sendBundle = new Bundle();
        sendBundle.putSerializable("object", data);
        detailFragment.setArguments(sendBundle);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, detailFragment);
        fragmentTransaction.commit();

    }
}