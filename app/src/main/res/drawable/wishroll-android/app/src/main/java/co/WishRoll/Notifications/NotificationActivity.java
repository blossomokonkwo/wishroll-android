package co.wishroll.Notifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import co.wishroll.R;

public class NotificationActivity extends AppCompatActivity {
    private static final String TAG = "NotificationActivity";
    private Context mContext = NotificationActivity.this;
    private static final int ACTIVITY_NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);


        Log.d(TAG, "onCreate: starting");

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView(){
        //Bottom Navigation View Setup
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        TopNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        TopNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);



    }
}
