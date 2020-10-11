package co.wishroll.views.profile;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import co.wishroll.databinding.ActivityProfileBinding;
import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.ProfileViewModel;
import co.wishroll.viewmodel.ProfileViewModelFactory;
import co.wishroll.views.home.ContactActivity;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.home.PostsGridActivity;
import co.wishroll.views.registration.LoginActivity;
import co.wishroll.views.reusables.TextBodyActivity;
import co.wishroll.views.tools.ProfileViewPagerAdapter;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding activityProfileViewBinding;
    private static final String TAG = "ProfileActivity";

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    ProfileViewModel profileViewModel;
    ImageButton moreButton;
    private TextView usernameView, fullNameView, bioProfileView, numViews, numFollowers, numFollowing;
    private FloatingActionButton fabHome;
    private CircularImageView profilePicture;
    private ImageView bannerPicture;
    private ImageButton backProfileView;
    private ImageView  verifiedButton;
    private Button bMainButton;
    private TextView followingList, followersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        int currentId = sessionManagement.getCurrentUserId();
        activityProfileViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(currentId)).get(ProfileViewModel.class);
        activityProfileViewBinding.setProfileViewModel(profileViewModel);
        activityProfileViewBinding.setBannerImageUrl(sessionManagement.getBackgroundURL());
        activityProfileViewBinding.setProfileImageUrl(sessionManagement.getAvatarURL());

        moreButton = findViewById(R.id.moreProfileView);

        usernameView = findViewById(R.id.usernameProfileView);
        fullNameView = findViewById(R.id.fullNameProfileView);
        bioProfileView = findViewById(R.id.bioProfileView);
        numFollowers = findViewById(R.id.followerCountProfileView);
        numFollowing = findViewById(R.id.followingCountProfileView);
        verifiedButton = findViewById(R.id.verifiedProfileView);

        fabHome = findViewById(R.id.fabProfileView);
        backProfileView = findViewById(R.id.backProfileView);
        bMainButton = findViewById(R.id.mainButtonProfileView);

        subscribeProfileObserver();

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });


        backProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            }
        });

        bMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
                finish();
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












        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        ProfileActivity.this, R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet,
                        findViewById(R.id.bottomSheetContainer));


                bottomSheetView.findViewById(R.id.privacyPolicy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (ProfileActivity.this, TextBodyActivity.class);
                        viewText.putExtra("pageTitle", 2);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.faqButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (ProfileActivity.this, TextBodyActivity.class);
                        viewText.putExtra("pageTitle", 1);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();


                    }
                });


                bottomSheetView.findViewById(R.id.viewBookmarks).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewBookmarks = new Intent (ProfileActivity.this, PostsGridActivity.class);
                        viewBookmarks.putExtra("query", "Bookmarks");
                        viewBookmarks.putExtra("isBookmarkQuery", true);
                        startActivity(viewBookmarks);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.termsOfServiceButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewTerms = new Intent (ProfileActivity.this, TextBodyActivity.class);
                        viewTerms.putExtra("pageTitle", 3);
                        startActivity(viewTerms);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.contactUsButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (ProfileActivity.this, ContactActivity.class);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();


                    }
                });

                bottomSheetView.findViewById(R.id.deleteAccount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bottomSheetDialog.dismiss();


                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                        builder.setMessage("All your account data and posts will be deleted.");
                        builder.setTitle("Are You Sure?");
                        builder.setCancelable(true);

                        builder.setPositiveButton("Delete Account", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                //LMAOOOO IDEK IF THIS IS RIGHT TBH
                                if(profileViewModel.deleteAccount()) {
                                    Glide.get(ProfileActivity.this).clearMemory();
                                    //Glide.get(getContext()).clearDiskCache();
                                    sessionManagement.clearSession();
                                    sessionManagement.checkLogout();
                                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                }else{
                                    ToastUtils.showToast(ProfileActivity.this, "Something went wrong");
                                }
                            }
                        });

                        builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();


                    }

                });

                bottomSheetView.findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bottomSheetDialog.dismiss();
                        sessionManagement.printEverything("before clearing session");
                        sessionManagement.clearSession();
                        sessionManagement.checkLogout();
                        sessionManagement.printEverything("after clearing session");

                        Glide.get(ProfileActivity.this).clearMemory();
                        //Glide.get(getContext()).clearDiskCache();
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();




                    }

                });



                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

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
                            numFollowers.setVisibility(View.INVISIBLE);
                            numFollowing.setVisibility(View.INVISIBLE);
                            usernameView.setVisibility(View.INVISIBLE);
                            verifiedButton.setVisibility(View.INVISIBLE);
                            fullNameView.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case ERROR: {
                            bMainButton.setVisibility(View.GONE);

                            numFollowers.setVisibility(View.GONE);
                            numFollowing.setVisibility(View.GONE);
                            usernameView.setVisibility(View.GONE);
                            String errorMessage = "User Not Found";
                            fullNameView.setText(errorMessage);

                            Log.d(TAG, "onChanged: error on showing profile.");


                            break;

                        }

                        case AUTHENTICATED: {

                            User user = userStateData.data;
                            bMainButton.setVisibility(View.VISIBLE);
                            numFollowers.setVisibility(View.VISIBLE);
                            numFollowing.setVisibility(View.VISIBLE);
                            usernameView.setVisibility(View.VISIBLE);

                            if(user.isVerified()){
                                verifiedButton.setVisibility(View.VISIBLE);
                            }else{
                                verifiedButton.setVisibility(View.GONE);
                            }
                            fullNameView.setVisibility(View.VISIBLE);

                            String attedUsername = "@" + user.getUsername();
                            usernameView.setText(attedUsername);
                            fullNameView.setText(user.getName());
                            bioProfileView.setText(user.getBio());
                            String numFollowersString = user.getNumFollowers() + "";
                            String numFollowingString = user.getNumFollowing() + "";
                            numFollowers.setText(numFollowersString);
                            numFollowing.setText(numFollowingString);



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

}