package co.WishRoll.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import co.WishRoll.Models.User;
import co.WishRoll.R;
import co.WishRoll.Utils.BottomNavigationViewHelper;

public class SearchActivity extends AppCompatActivity {



    private static final String TAG = "SearchActivity";
    private Context mContext = SearchActivity.this;
    private static final int ACTIVITY_NUM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
        Log.d(TAG, "onCreate: starting");

        Intent intent = getIntent();

        /*
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");
        String fullName = intent.getStringExtra("fullName");
        String password = intent.getStringExtra("password");


        if(fullName == "" || fullName == null){
            User user = new User(email, password, email);
        }else{
            User user = new User(email, username, fullName, password);
        }

        */






















        setupBottomNavigationView();


    }

    private void setupBottomNavigationView(){
        //Bottom Navigation View Setup
        Log.d(TAG, "setupBottomNavigationView: setting up bottom navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);


    }
}
