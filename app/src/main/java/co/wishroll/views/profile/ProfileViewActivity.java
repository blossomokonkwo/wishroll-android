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
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
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
import co.wishroll.views.home.ContactActivity;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.registration.LoginActivity;
import co.wishroll.views.reusables.TextBodyActivity;
import co.wishroll.views.tools.ProfileViewPagerAdapter;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ProfileViewActivity extends AppCompatActivity {
    private static final String TAG = "ProfileViewActivity";
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    ProfileViewModel profileViewModel;
    ActivityProfileviewBinding activityProfileviewBinding;


    private TextView usernameView, fullNameView, bioProfileView, numFollowers, numFollowing;
    private FloatingActionButton fabHome;
    private CircularImageView profilePicture;
    private ImageView bannerPicture;
    private ImageButton backProfileView, moreProfileView;
    private Button bMainButton;
    boolean isCurrentUser = false;
    User user = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileview);





        int userId = getIntent().getIntExtra("userId", sessionManagement.getCurrentUserId());


        if(userId == sessionManagement.getCurrentUserId() ){
            isCurrentUser = true;

        }else{
            isCurrentUser = false;
        }



        activityProfileviewBinding = DataBindingUtil.setContentView(this, R.layout.activity_profileview);
        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(userId)).get(ProfileViewModel.class);
        activityProfileviewBinding.setBannerImageUrl(sessionManagement.getBackgroundURL());
        activityProfileviewBinding.setProfileImageUrl(sessionManagement.getAvatarURL());

        usernameView = findViewById(R.id.usernameProfileView);
        fullNameView = findViewById(R.id.fullNameProfileView);
        bioProfileView = findViewById(R.id.bioProfileView);
        numFollowers = findViewById(R.id.followerCountProfileView);
        numFollowing = findViewById(R.id.followingCountProfileView);

        fabHome = findViewById(R.id.fabProfileView);
        backProfileView = findViewById(R.id.backProfileView);
        moreProfileView = findViewById(R.id.moreProfileView);
        bMainButton = findViewById(R.id.mainButtonProfileView);
        profilePicture = findViewById(R.id.profilePictureProfileView);
        bannerPicture = findViewById(R.id.bannerProfileView);



        ViewPager2 viewPager2 = findViewById(R.id.viewPagerProfileView);
        viewPager2.setAdapter(new ProfileViewPagerAdapter(this, isCurrentUser, userId));


        observeThisUser();

        TabLayout tabLayout = findViewById(R.id.tabLayoutProfileView);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){

                    case 0:{
                        //Uploads
                        tab.setIcon(R.drawable.ic_uploads);
                        break;}

                    case 1:{
                        //Likes
                        tab.setIcon(R.drawable.ic_likes);
                        break;}

                    case 2:{
                        //Bookmarks
                        tab.setIcon(R.drawable.ic_bookmark);
                        break;}

                    default: {
                        //Notifications
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


                bottomSheetView.findViewById(R.id.privacyPolicy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (ProfileViewActivity.this, TextBodyActivity.class);
                        viewText.putExtra("pageTitle", 2);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.faqButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (ProfileViewActivity.this, TextBodyActivity.class);
                        viewText.putExtra("pageTitle", 1);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();


                    }
                });


                bottomSheetView.findViewById(R.id.termsOfServiceButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewTerms = new Intent (ProfileViewActivity.this, TextBodyActivity.class);
                        viewTerms.putExtra("pageTitle", 3);
                        startActivity(viewTerms);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.contactUsButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (ProfileViewActivity.this, ContactActivity.class);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();


                    }
                });


                bottomSheetView.findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bottomSheetDialog.dismiss();
                        sessionManagement.clearSession();
                        sessionManagement.checkLogout();

                        Glide.get(ProfileViewActivity.this).clearMemory();
                        //Glide.get(getContext()).clearDiskCache();
                        startActivity(new Intent(ProfileViewActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
                if(isCurrentUser) {
                    startActivity(new Intent(ProfileViewActivity.this, MainActivity.class));
                    onBackPressed();
                }else{
                    finish();
                }
            }
        });


        bMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isCurrentUser) {
                    startActivity(new Intent(ProfileViewActivity.this, EditProfileActivity.class));
                    finish();
                }else{
                    //follow or unfollow this stranger, summon profile view model
                }
            }
        });












    }

    public void observeThisUser(){
        profileViewModel.observeThisUser().observe(this, new Observer<StateData<User>>() {
            @Override
            public void onChanged(StateData<User> userStateData) {
                if (userStateData != null) {

                    switch (userStateData.status) {

                        case LOADING: {
                            Log.d(TAG, "onChanged: SESSION ID CUZ THIS IS GETTING RIDONKULUOIS " + sessionManagement.getCurrentUserId());
                            bMainButton.setVisibility(View.INVISIBLE);
                            numFollowers.setVisibility(View.INVISIBLE);
                            numFollowing.setVisibility(View.INVISIBLE);
                            usernameView.setVisibility(View.INVISIBLE);
                            fullNameView.setVisibility(View.INVISIBLE);
                            profilePicture.setImageDrawable(ContextCompat.getDrawable(ProfileViewActivity.this, R.drawable.defaultprofile));
                            break;
                        }
                        case ERROR: {
                            bMainButton.setVisibility(View.GONE);
                            numFollowers.setVisibility(View.GONE);
                            numFollowing.setVisibility(View.GONE);
                            usernameView.setVisibility(View.GONE);
                            String errorMessage = "User Not Found";
                            fullNameView.setText(errorMessage);
                            profilePicture.setImageDrawable(ContextCompat.getDrawable(ProfileViewActivity.this, R.drawable.defaultprofile));



                        }

                        case AUTHENTICATED: {
                             user = userStateData.data;
                            bMainButton.setVisibility(View.VISIBLE);
                            numFollowers.setVisibility(View.VISIBLE);
                            numFollowing.setVisibility(View.VISIBLE);
                            usernameView.setVisibility(View.VISIBLE);
                            fullNameView.setVisibility(View.VISIBLE);

                            Log.d(TAG, "onChanged: SESSION ID CUZ THIS IS GETTING RIDONKULUOIS " + sessionManagement.getCurrentUserId());
                            String attedUsername = "@" + user.getUsername();
                            usernameView.setText(attedUsername);
                            fullNameView.setText(user.getName());
                            bioProfileView.setText(user.getBio());
                            String numFollowersString = user.getNumFollowers() + "";
                            String numFollowingString = user.getNumFollowing() + "";
                            numFollowers.setText(numFollowersString);
                            numFollowing.setText(numFollowingString);
                            Boolean isFollowing = user.isFollowing();


                            /*Glide.with(getContext())
                                    .load(user.getAvatar()).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.defaultprofile)
                                    .into(profilePicture);

                            Glide.with(getContext())
                                    .load(user.getProfileBackgroundUrl()).apply(new RequestOptions()).placeholder(R.color.wishroll_blue)
                                    .into(bannerPicture);*/


                            if(isFollowing == null){
                                bMainButton.setText(getString(R.string.edit_profile));
                                bMainButton.setBackground(ContextCompat.getDrawable(ProfileViewActivity.this, R.drawable.edit_white_button));
                                moreProfileView.setVisibility(View.VISIBLE);
                            }else if(isFollowing){
                                bMainButton.setText(getString(R.string.following));
                                bMainButton.setBackground(ContextCompat.getDrawable(ProfileViewActivity.this, R.drawable.following_white_button));
                                moreProfileView.setVisibility(View.GONE);


                            }else{ //is not following
                                bMainButton.setText(getString(R.string.follow));
                                bMainButton.setBackground(ContextCompat.getDrawable(ProfileViewActivity.this, R.drawable.follow_blue_button));
                                moreProfileView.setVisibility(View.GONE);



                            }

                        }

                    }
                }
            }
        });
    }





}