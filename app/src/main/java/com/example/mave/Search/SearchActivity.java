package com.example.mave.Search;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.content.Context;
import androidx.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;

import com.example.mave.R;
import com.example.mave.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class SearchActivity extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 2;

    private Context mContext = SearchActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: started.");

        setupBottomNavigationView();
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.SetupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}

