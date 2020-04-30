package co.WishRoll.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import co.WishRoll.R;
import co.WishRoll.homepage;
import co.WishRoll.notificationpage;
import co.WishRoll.profilepage;
import co.WishRoll.searchpage;
import co.WishRoll.uploadpage;

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";


    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setupBottomNavigationView: setting up bottom nav view");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);


    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.ic_search:
                        //ACTIVITY_NUM 0
                        Intent intent1 = new Intent(context, searchpage.class);
                        context.startActivity(intent1);
                        break;

                    case R.id.ic_house:
                        //ACTIVITY_NUM 1
                        Intent intent2 = new Intent(context, homepage.class);
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_upload:
                        //ACTIVITY_NUM 2
                        Intent intent3 = new Intent(context, uploadpage.class);
                        context.startActivity(intent3);
                        break;

                    case R.id.ic_notification:
                        //ACTIVITY_NUM 3
                        Intent intent4 = new Intent(context, notificationpage.class);
                        context.startActivity(intent4);
                        break;

                    case R.id.ic_profile:
                        //ACTIVITY_NUM 4
                        Intent intent5 = new Intent(context, profilepage.class);
                        context.startActivity(intent5);
                        break;


                }


                return false;
            }
        });

    }
}
