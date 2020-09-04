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
import co.wishroll.views.reusables.Followers;
import co.wishroll.views.reusables.Following;
import co.wishroll.views.tools.ProfileViewPagerAdapter;

import static co.wishroll.WishRollApplication.applicationGraph;


public class ProfileViewActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE ACTIVITY";
    private ActivityProfileviewBinding activityProfileViewBinding;
    ProfileViewModel profileViewModel;


    TextView usernameView, fullNameView, wishrollScore, bioProfileView, numViews, numFollowers, numFollowing;
    private FloatingActionButton fabHome;
    CircularImageView profilePicture;
    ImageView bannerPicture, emojiView;
    ImageButton backProfileView, moreProfileView;
    Button bMainButton;
    private TextView followingList, followersList;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int currentId = sessionManagement.getCurrentUserId();
        activityProfileViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_profileview);
        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(currentId)).get(ProfileViewModel.class);
        activityProfileViewBinding.setProfileViewModel(profileViewModel);


        usernameView = findViewById(R.id.usernameProfileView);
        fullNameView = findViewById(R.id.fullNameProfileView);
        wishrollScore = findViewById(R.id.wishrollScoreProfileView);
        bioProfileView = findViewById(R.id.bioProfileView);
        numViews = findViewById(R.id.viewsProfileView);
        numFollowers = findViewById(R.id.followerCountProfileView);
        numFollowing = findViewById(R.id.followingCountProfileView);
        emojiView = findViewById(R.id.emojiProfileView);

        fabHome = findViewById(R.id.fabProfileView);
        backProfileView = findViewById(R.id.backProfileView);
        moreProfileView = findViewById(R.id.moreProfileView);
        bMainButton = findViewById(R.id.mainButtonProfileView);


        subscribeProfileObserver();

        numFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileViewActivity.this, Following.class));
            }
        });

        numFollowers.setOnClickListener(new View.OnClickListener() {
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

                    }
                });

                bottomSheetView.findViewById(R.id.logoutProfileView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sessionManagement.clearSession();
                        sessionManagement.checkLogout();
                        bottomSheetDialog.dismiss();
                        startActivity(new Intent(ProfileViewActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        //TODO(this crashes everytime it happens but we're going to leave it alone for now) --> added to this but we'll see how it works rn bruv
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


    public void subscribeProfileObserver(){
        profileViewModel.observeCurrentUserProfile().observe(this, new Observer<StateData<User>>() {
            @Override
            public void onChanged(StateData<User> userStateData) {
                if (userStateData != null) {

                    switch (userStateData.status) {

                        case LOADING: {
                            bMainButton.setVisibility(View.INVISIBLE);
                            numViews.setVisibility(View.INVISIBLE);
                            numFollowers.setVisibility(View.INVISIBLE);
                            numFollowing.setVisibility(View.INVISIBLE);
                            usernameView.setVisibility(View.INVISIBLE);
                            wishrollScore.setVisibility(View.INVISIBLE);
                            emojiView.setVisibility(View.INVISIBLE);
                            fullNameView.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case ERROR: {
                           bMainButton.setVisibility(View.GONE);
                            numViews.setVisibility(View.GONE);
                            numFollowers.setVisibility(View.GONE);
                            numFollowing.setVisibility(View.GONE);
                            usernameView.setVisibility(View.GONE);
                            wishrollScore.setVisibility(View.GONE);
                            emojiView.setVisibility(View.GONE);
                            String errorMessage = "User Not Found";
                            fullNameView.setText(errorMessage);

                            Log.d(TAG, "onChanged: error on showing profile.");

                            showProgressBar(false);
                            break;

                        }

                        case AUTHENTICATED: {
                            User user = userStateData.data;

                            bMainButton.setVisibility(View.VISIBLE);
                            numViews.setVisibility(View.VISIBLE);
                            numFollowers.setVisibility(View.VISIBLE);
                            numFollowing.setVisibility(View.VISIBLE);
                            usernameView.setVisibility(View.VISIBLE);
                            wishrollScore.setVisibility(View.VISIBLE);
                            emojiView.setVisibility(View.VISIBLE);
                            fullNameView.setVisibility(View.VISIBLE);

                            usernameView.setText(user.getUsername());
                            fullNameView.setText(user.getName());
                            String wishrollScoreString = user.getWishrollScore() + "";
                            wishrollScore.setText(wishrollScoreString);
                            bioProfileView.setText(user.getBio());
                            String numFollowersString = user.getNumFollowers() + "";
                            String numFollowingString = user.getNumFollowing() + "";
                            numFollowers.setText(numFollowersString);
                            numFollowing.setText(numFollowingString);
                            String numViewsString = user.getViewCount() + "";
                            numViews.setText(numViewsString);




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