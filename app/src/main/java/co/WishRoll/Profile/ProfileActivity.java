package co.WishRoll.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import co.WishRoll.Models.User;
import co.WishRoll.Models.UserLocalStore;
import co.WishRoll.Notifications.NotificationActivity;
import co.WishRoll.R;
import co.WishRoll.Search.SearchActivity;
import co.WishRoll.Signup.MainActivity;
import co.WishRoll.Signup.RegisterActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener,
        UploadsFragment.OnFragmentInteractionListener, BookmarksFragment.OnFragmentInteractionListener,
        LikesFragment.OnFragmentInteractionListener
{
    //activity to display the user's profile

    private static final String TAG = "ProfileActivity";



    ImageButton ibSettings, ibNotifs, ibBackMain;
    TextView tvViewsNumber, tvFollowersNumber, tvFollowingNumber, tvUserBio, tvUserFullName, tvUsernameView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UploadsFragment uploadsFragment;
    private LikesFragment likesFragment;
    private BookmarksFragment bookmarksFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);


        ibSettings =  findViewById(R.id.ibSettings);
        ibNotifs = findViewById(R.id.ibNotifications);
        ibBackMain = findViewById(R.id.ibBackMain);
        tvUsernameView = findViewById(R.id.tvUsernameView);
        tvViewsNumber = findViewById(R.id.tvViews);
        tvFollowersNumber = findViewById(R.id.tvFollowers);
        tvFollowingNumber = findViewById(R.id.tvFollowing);
        tvUserBio = findViewById(R.id.tvBio);
        tvUserFullName = findViewById(R.id.tvFullName);
        viewPager = findViewById(R.id.vpViewPager);
        tabLayout = findViewById(R.id.tlTabLayout);

        uploadsFragment = new UploadsFragment();
        likesFragment = new LikesFragment();
        bookmarksFragment = new BookmarksFragment();

        ibSettings.setOnClickListener(this);
        ibNotifs.setOnClickListener(this);
        ibBackMain.setOnClickListener(this);

        tabLayout.addTab(tabLayout.newTab().setText("Uploads"));
        tabLayout.addTab(tabLayout.newTab().setText("Liked"));
        tabLayout.addTab(tabLayout.newTab().setText("Bookmarked"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });













    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ibSettings:
                Log.d(TAG, "onClick: entering the settings activity");

                startActivity(new Intent(this, SettingsActivity.class));

                break;

            case R.id.ibNotifications:
                Log.d(TAG, "onClick: entering the notifications activity");

                startActivity(new Intent(this, NotificationActivity.class));

            break;

            case R.id.ibBackMain:
                Log.d(TAG, "onClick: going back to the Search Activity");

                startActivity(new Intent(this, SearchActivity.class));

                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
