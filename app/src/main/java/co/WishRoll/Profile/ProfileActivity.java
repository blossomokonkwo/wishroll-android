package co.WishRoll.Profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import co.WishRoll.R;
import co.WishRoll.Signup.MainActivity;
import co.WishRoll.Signup.RegisterActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ProfileActivity";
    private Context mContext = ProfileActivity.this;
    private static final int ACTIVITY_NUM = 4;

    ImageButton ibSettings;
    UserLocalStore userLocalStore;
    TextView tvUsernameView;
    TextView tvViewsNumber, tvFollowersNumber, tvFollowingNumber, tvUserBio, tvUserFullName;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private UploadsFragment uploadsFragment;
    private LikesFragment likesFragment;
    private BookmarksFragment bookmarksFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        ibSettings = (ImageButton) findViewById(R.id.ibSettings);

        tvUsernameView = findViewById(R.id.tvUsernameView);
        tvViewsNumber = findViewById(R.id.tvViewsNumber);
        tvFollowersNumber = findViewById(R.id.tvFollowersNumber);
        tvFollowingNumber = findViewById(R.id.tvFollowingNumber);
        tvUserBio = findViewById(R.id.tvBioProfileView);
        tvUserFullName = findViewById(R.id.tvFullNameProfileView);


        ibSettings.setOnClickListener(this);

        Log.d(TAG, "onCreate: starting");
        userLocalStore = new UserLocalStore(this);

        viewPager = findViewById(R.id.vpViewPager);
        tabLayout = findViewById(R.id.tlTabLayout);


        uploadsFragment = new UploadsFragment();
        likesFragment = new LikesFragment();
        bookmarksFragment = new BookmarksFragment();


    }

    @Override
    protected void onStart() {
        //a way to authenticate the user, make sure user is logged in
        super.onStart();

        if (authenticate() == true) {
            displayUserDetails();
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }


    }

    private boolean authenticate() {
        //tells us if user is logged in or logged out
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails() {
        //if authentification is correct, we display the user details like username, follow count, etc
        User user = userLocalStore.getLoggedInUser();

        tvUserFullName.setText(user.getFullName());
        tvUsernameView.setText(user.getUsername());


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibSettings:
                Log.d(TAG, "onClick: logging out of account");
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, MainActivity.class));

                break;
        }
    }


}
