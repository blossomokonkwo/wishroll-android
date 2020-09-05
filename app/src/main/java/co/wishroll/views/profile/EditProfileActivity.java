package co.wishroll.views.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.R;
import co.wishroll.databinding.ActivityEditProfileBinding;
import co.wishroll.models.repository.datamodels.EditedUser;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.EditProfileViewModel;

import static co.wishroll.WishRollApplication.applicationGraph;

public class EditProfileActivity extends AppCompatActivity implements AuthListener {
    private static final String TAG = "EditProfileActivity";

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private ActivityEditProfileBinding editProfileBinding;
    EditProfileViewModel editProfileViewModel;
    ProgressBar progressBar;


    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);



        editProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        editProfileBinding.setEditProfileViewModel(editProfileViewModel);


        backButton = findViewById(R.id.backEditProfileView);


        progressBar = findViewById(R.id.editProfileProgressBar);








        observeChangedUser();







        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });








    }



    public void observeChangedUser(){

        editProfileViewModel.editBio = sessionManagement.getBio();
        Log.d(TAG, "observeChangedUser: starting to observe the edited user");
        editProfileViewModel.observeEditsMade().observe(this, new Observer<StateData<EditedUser>>() {
            @Override
            public void onChanged(StateData<EditedUser> editedUserStateData) {
                if(editedUserStateData != null){
                    switch (editedUserStateData.status){

                        case LOADING: {
                            Log.d(TAG, "onChanged: this is loading");
                                showProgressBar(true);
                            break;
                        }
                        case ERROR: {
                            Log.d(TAG, "onChanged: we have an error with the edited user.");
                                showProgressBar(false);
                                sendMessage("Something went wrong.");

                            break;

                        }

                        case AUTHENTICATED: {

                            Log.d(TAG, "onChanged: this user's changes are getting saved into the shared preferences");
                            showProgressBar(false);

                            if(editedUserStateData.data != null) {


                                sessionManagement.setNameSession(editedUserStateData.data.name);
                                sessionManagement.setUsernameSession(editedUserStateData.data.username);
                                sessionManagement.setBioSession(editedUserStateData.data.bio);
                                sessionManagement.setEmailSession(editedUserStateData.data.email);
                                sessionManagement.setBackgroundSession(editedUserStateData.data.backgroundUrl);
                                sessionManagement.setAvatar(editedUserStateData.data.avatarUrl);

                                sendMessage("Profile Updated");
                                finish();



                            }



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

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String message) {
        ToastUtils.showToast(this, message);


    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorMessage(String message) {
        ToastUtils.showToast(this, message);


    }

    @Override
    public void statusGetter(int statusCode) {

    }






}