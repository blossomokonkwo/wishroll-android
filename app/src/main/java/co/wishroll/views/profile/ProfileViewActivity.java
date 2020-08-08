package co.wishroll.views.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.wishroll.R;
import co.wishroll.databinding.ActivityProfileviewBinding;
import co.wishroll.entities.User;
import co.wishroll.utilities.ProfileViewPagerAdapter;
import co.wishroll.viewmodel.UserViewModel;
import co.wishroll.views.reusables.Followers;
import co.wishroll.views.reusables.Following;
import co.wishroll.views.home.MainActivity;


public class ProfileViewActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE ACTIVITY";
    private ActivityProfileviewBinding activityProfileViewBinding;


    TextView usernameView, fullNameView;
    FloatingActionButton fabHome;
    ImageButton backProfileView, moreProfileView;
    Button bMainButton;
    TextView followingList, followersList;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileview);


        User user = new User("blossomokonkwo1@gmail.com" , "Blossom", 19, "blossomok", "okonkwo1");
        UserViewModel userViewModel = new UserViewModel(user);
        activityProfileViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_profileview);
        activityProfileViewBinding.setUserViewModel(userViewModel);

        fabHome = findViewById(R.id.fabProfileView);
        backProfileView = findViewById(R.id.backProfileView);
        moreProfileView = findViewById(R.id.moreProfileView);
        bMainButton = findViewById(R.id.mainButtonProfileView);
        followersList = findViewById(R.id.followerCountProfileView);
        followingList = findViewById(R.id.followingCountProfileView);

        followingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileViewActivity.this, Following.class));
            }
        });

        followersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileViewActivity.this, Followers.class));

            }
        });





        ViewPager2 viewPager2 = findViewById(R.id.viewPagerProfileView);
        viewPager2.setAdapter(new ProfileViewPagerAdapter(this));

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

                    case 1:{
                        //tab.setText("Likes");
                        tab.setIcon(R.drawable.ic_likes);
                        break;}

                    case 2:{
                        //tab.setText("Bookmarks");
                        tab.setIcon(R.drawable.ic_bookmark);
                        break;}

                    default: {
                        //tab.setText("Notifications");
                        tab.setIcon(R.drawable.ic_notifications);
                        break;
                    }
                }

            }
        });

        tabLayoutMediator.attach();




        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileViewActivity.this, MainActivity.class));
                finish();
            }
        });

        moreProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        ProfileViewActivity.this, R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet,
                        (LinearLayout)findViewById(R.id.bottomSheetContainer));

                bottomSheetView.findViewById(R.id.contactProfileView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ProfileViewActivity.this, ContactActivity.class));
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.privacyProfileView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO(show user privacy policy)
                        //bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.yourAccountProfileView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(ProfileViewActivity.this, EditProfileActivity.class));
                        bottomSheetDialog.dismiss();

                    }
                });

                bottomSheetView.findViewById(R.id.logoutProfileView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //bottomSheetDialog.dismiss();
                        //startActivity(new Intent(ProfileViewActivity.this, LoginActivity.class));
                        //TODO(this crashes everytime it happens but we're going to leave it alone for now)
                        //finish();

                    }

                });


                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

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
                startActivity(new Intent(ProfileViewActivity.this, EditProfileActivity.class));
            }
        });






    }



}