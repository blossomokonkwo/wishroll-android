package co.wishroll.views.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.wishroll.R;
import co.wishroll.databinding.ActivityOtherprofileviewBinding;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.viewmodel.OtherProfileViewModel;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.reusables.UserList;
import co.wishroll.views.tools.ProfileViewPagerAdapter;

import static co.wishroll.WishRollApplication.applicationGraph;


public class OtherProfileViewActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE ACTIVITY";
    private ActivityOtherprofileviewBinding activityOtherprofileviewBinding;
    OtherProfileViewModel otherProfileViewModel;


    private TextView usernameView, fullNameView;
    private FloatingActionButton fabHome;
    private ImageButton backProfileView, moreProfileView;
    private Button bMainButton;
    private TextView followingList, followersList;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        String username = intent.getStringExtra("username"); //use intent extra and viewmodel factory to send username to viewmodel and API
        activityOtherprofileviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_otherprofileview);
        otherProfileViewModel = new ViewModelProvider(this).get(OtherProfileViewModel.class);
        activityOtherprofileviewBinding.setOtherProfileViewModel(otherProfileViewModel);


        fabHome = findViewById(R.id.fabProfileView);
        backProfileView = findViewById(R.id.backProfileView);
        moreProfileView = findViewById(R.id.moreProfileView);
        bMainButton = findViewById(R.id.mainButtonProfileView);
        followersList = findViewById(R.id.followerCountProfileView);
        followingList = findViewById(R.id.followingCountProfileView);

        followingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherProfileViewActivity.this, UserList.class));
            }
        });

        followersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherProfileViewActivity.this, UserList.class));

            }
        });





        ViewPager2 viewPager2 = findViewById(R.id.viewPagerProfileView);
        viewPager2.setAdapter(new ProfileViewPagerAdapter(this, false));

        TabLayout tabLayout = findViewById(R.id.tabLayoutProfileView);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d(TAG, "inside the onConfigure tab, TabLayout mediator");

                switch (position){

                    case 0:{
                        //tab.setText("Uploads");
                        tab.setIcon(R.drawable.ic_uploads);
                        break;}

                    default:{
                        //tab.setText("Likes");
                        tab.setIcon(R.drawable.ic_rolls);
                        break;}

                }

            }
        });

        tabLayoutMediator.attach();




        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OtherProfileViewActivity.this, MainActivity.class));
                finish();
            }
        });



        backProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });






    }



}