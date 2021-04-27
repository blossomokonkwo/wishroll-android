package co.wishroll.views.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.databinding.FragmentProfileBinding;
import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.ProfileViewModel;
import co.wishroll.views.profile.EditProfileActivity;
import co.wishroll.views.registration.LoginActivity;
import co.wishroll.views.reusables.TextBodyActivity;
import co.wishroll.views.tools.ProfileViewPagerAdapter;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding fragmentProfileBinding;
    View view;
    ProfileViewModel profileViewModel;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "ProfileFragment";

    private TextView usernameView, fullNameView, bioProfileView, numFollowers, numFollowing;
    private CircularImageView profilePicture;
    private ImageView bannerPicture;
    private ImageButton backProfileView, moreProfileView;
    private Button bMainButton;
    boolean isCurrentUser = false;
    User user = null;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        view = fragmentProfileBinding.getRoot();


        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        fragmentProfileBinding.setProfileviewmodel(profileViewModel);
        fragmentProfileBinding.setProfileImageUrl(sessionManagement.getAvatarURL());
        fragmentProfileBinding.setBannerImageUrl(sessionManagement.getBackgroundURL());


        int userId = getActivity().getIntent().getIntExtra("userId", sessionManagement.getCurrentUserId());


        usernameView = view.findViewById(R.id.usernameProfileView);
        fullNameView = view.findViewById(R.id.fullNameProfileView);
        bioProfileView = view.findViewById(R.id.bioProfileView);
        numFollowers = view.findViewById(R.id.followerCountProfileView);
        numFollowing = view.findViewById(R.id.followingCountProfileView);


        backProfileView = view.findViewById(R.id.backProfileView);
        moreProfileView = view.findViewById(R.id.moreProfileView);
        bMainButton = view.findViewById(R.id.mainButtonProfileView);
        profilePicture = view.findViewById(R.id.profilePictureProfileView);
        bannerPicture = view.findViewById(R.id.bannerProfileView);


        if(userId == sessionManagement.getCurrentUserId() ){
            isCurrentUser = true;
            backProfileView.setVisibility(View.GONE);
        }else if(userId == 0){
            backProfileView.setVisibility(View.GONE);
        }else{
            backProfileView.setVisibility(View.VISIBLE);
            isCurrentUser = false;
        }

        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerProfileView);
        viewPager2.setAdapter(new ProfileViewPagerAdapter(getActivity(), isCurrentUser, userId));

        observeThisUser();

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutProfileView);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){

                    case 0:{
                        //Uploads
                        tab.setIcon(R.drawable.ic_uploads);
                        break;}

                    default:{
                        //Likes
                        tab.setIcon(R.drawable.ic_likes);
                        break;}




                }

            }
        });

        tabLayoutMediator.attach();

        moreProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getContext(), R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(getContext()).inflate(R.layout.layout_bottom_sheet,
                        bottomSheetDialog.findViewById(R.id.bottomSheetContainer));


                bottomSheetView.findViewById(R.id.privacyPolicy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (getContext(), TextBodyActivity.class);
                        viewText.putExtra("pageTitle", 2);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.faqButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (getContext(), TextBodyActivity.class);
                        viewText.putExtra("pageTitle", 1);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();


                    }
                });


                bottomSheetView.findViewById(R.id.termsOfServiceButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewTerms = new Intent (getContext(), TextBodyActivity.class);
                        viewTerms.putExtra("pageTitle", 3);
                        startActivity(viewTerms);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.contactUsButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (getContext(), ContactActivity.class);
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

                        Glide.get(getContext()).clearMemory();
                        //Glide.get(getContext()).clearDiskCache();
                        startActivity(new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        getActivity().finish();




                    }

                });



                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }

        });


        backProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        bMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(isCurrentUser) {
                    startActivity(new Intent(getContext(), EditProfileActivity.class));
                    getActivity().finish();
                }else{
                    //follow or unfollow this stranger, summon profile view model
                }
            }
        });







        // Inflate the layout for this fragment
        return view;
    }

    public void observeThisUser(){
        profileViewModel.observeThisUser().observe(getViewLifecycleOwner(), new Observer<StateData<User>>() {
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
                            profilePicture.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.defaultprofile));
                            break;
                        }
                        case ERROR: {
                            bMainButton.setVisibility(View.GONE);
                            numFollowers.setVisibility(View.GONE);
                            numFollowing.setVisibility(View.GONE);
                            usernameView.setVisibility(View.GONE);
                            String errorMessage = "User Not Found";
                            fullNameView.setText(errorMessage);
                            profilePicture.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.defaultprofile));



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




                            if(isFollowing == null){
                                bMainButton.setText(getString(R.string.edit_profile));
                                bMainButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_white_button));
                                moreProfileView.setVisibility(View.VISIBLE);
                            }else if(isFollowing){
                                bMainButton.setVisibility(View.GONE);
                                moreProfileView.setVisibility(View.GONE);

                            }else{ //is not following
                                bMainButton.setVisibility(View.GONE);
                                moreProfileView.setVisibility(View.GONE);



                            }

                        }

                    }
                }
            }
        });
    }

}