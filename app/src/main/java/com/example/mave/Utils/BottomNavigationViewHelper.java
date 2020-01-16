package com.example.mave.Utils;

import android.app.Activity;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.view.MenuItem;


import com.example.mave.Add.AddActivity;

import com.example.mave.Camera.CameraActivity;
import com.example.mave.Home.HomeActivity;
import com.example.mave.MainActivity;
import com.example.mave.Search.SearchActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.example.mave.R;

import androidx.annotation.NonNull;


public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public  static void SetupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG,"setupBottomNavigationView:settingupBottomNavigationView");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);}
    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationViewEx.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, MainActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_add:
                        Intent intent2  = new Intent(context, CameraActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_search:
                        Intent intent3 = new Intent(context, SearchActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                    case R.id.ic_out:
                        Intent intent4 = new Intent(context, AddActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        break;

                }


                return false;
            }
        });
    }

}
