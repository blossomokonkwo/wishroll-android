package co.wishroll.views.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.databinding.ActivityProfileviewBinding;
import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.ProfileViewModel;
import co.wishroll.viewmodel.ProfileViewModelFactory;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.registration.LoginActivity;
import co.wishroll.views.tools.ProfileViewPagerAdapter;

import static co.wishroll.WishRollApplication.applicationGraph;


public class ProfileViewActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE ACTIVITY";
    private ActivityProfileviewBinding activityProfileViewBinding;
    ProfileViewModel profileViewModel;


    private TextView usernameView, fullNameView, bioProfileView;
    private FloatingActionButton fabHome;
    private CircularImageView profilePicture;
    private ImageView bannerPicture;
    private ImageButton backProfileView, moreProfileView;
    private Button bMainButton;
    private SessionManagement sessionManagement = applicationGraph.sessionManagement();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int currentId = sessionManagement.getCurrentUserId();
        activityProfileViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_profileview);
        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(currentId)).get(ProfileViewModel.class);
        activityProfileViewBinding.setProfileViewModel(profileViewModel);
        activityProfileViewBinding.setBannerImageUrl(sessionManagement.getBackgroundURL());
        activityProfileViewBinding.setProfileImageUrl(sessionManagement.getAvatarURL());


        usernameView = findViewById(R.id.usernameProfileView);
        fullNameView = findViewById(R.id.fullNameProfileView);
        bioProfileView = findViewById(R.id.bioProfileView);

        fabHome = findViewById(R.id.fabProfileView);
        backProfileView = findViewById(R.id.backProfileView);
        moreProfileView = findViewById(R.id.moreProfileView);
        bMainButton = findViewById(R.id.mainButtonProfileView);


        subscribeProfileObserver();







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
                        findViewById(R.id.bottomSheetContainer));

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
                        finish();

                    }
                });

                bottomSheetView.findViewById(R.id.logoutProfileView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sessionManagement.clearSession();
                        sessionManagement.checkLogout();
                        bottomSheetDialog.dismiss();
                        startActivity(new Intent(ProfileViewActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();

                    }

                });


                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }

        });

        backProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileViewActivity.this, MainActivity.class));
                finish();
            }
        });

        bMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileViewActivity.this, EditProfileActivity.class));
                finish();
            }
        });



    }


    public void subscribeProfileObserver(){
        profileViewModel.observeCurrentUserProfile().observe(this, new Observer<StateData<User>>() {
            @Override
            public void onChanged(StateData<User> userStateData) {
                if (userStateData != null) {

                    switch (userStateData.status) {

                        case LOADING: {
                            bMainButton.setVisibility(View.INVISIBLE);
                            usernameView.setVisibility(View.INVISIBLE);
                            fullNameView.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case ERROR: {
                           bMainButton.setVisibility(View.GONE);


                            usernameView.setVisibility(View.GONE);
                            String errorMessage = "User Not Found";
                            fullNameView.setText(errorMessage);

                            Log.d(TAG, "onChanged: error on showing profile.");

                            showProgressBar(false);
                            break;

                        }

                        case AUTHENTICATED: {

                            User user = userStateData.data;
                            bMainButton.setVisibility(View.VISIBLE);
                            usernameView.setVisibility(View.VISIBLE);
                            fullNameView.setVisibility(View.VISIBLE);

                            String attedUsername = "@" + user.getUsername();
                            usernameView.setText(attedUsername);
                            fullNameView.setText(user.getName());
                            bioProfileView.setText(user.getBio());




                            /*sessionManagement.setNameSession(user.getName());
                            sessionManagement.setUsernameSession(user.getUsername());
                            sessionManagement.setBioSession(user.getBio());
                            sessionManagement.setBackgroundSession(user.getProfileBackgroundUrl());
                            sessionManagement.setAvatar(user.getAvatar());*/




                            break;
                        }

                        /*case NOT_AUTHENTICATED: {


                            break;

                        }*/

                    }
                }
            }
        });
    }

    public void showProgressBar(boolean isVisible){
        if(isVisible){

        }
    }

    public void loadProfilePicture(String profilePictureURL){

    }

    public void loadBannerPicture(String bannerPictureURL){


    }



}