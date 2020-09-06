package co.wishroll.views.profile;

import android.content.Intent;
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
import co.wishroll.models.repository.datamodels.UpdateResponse;
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
        editProfileViewModel.authListener = this;

        backButton = findViewById(R.id.backEditProfileView);


        progressBar = findViewById(R.id.editProfileProgressBar);




        observeChangedUser();















        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
                finish();
            }
        });








    }



    public void observeChangedUser(){



        Log.d(TAG, "observeChangedUser: starting to observe the edited user");
        editProfileViewModel.observeEditsMade().observe(this, new Observer<StateData<UpdateResponse>>() {
            @Override
            public void onChanged(StateData<UpdateResponse> updateResponseStateData) {
                if(updateResponseStateData != null){

                    switch (updateResponseStateData.status){

                        case LOADING: {
                            Log.d(TAG, "onChanged: this is loading");
                                showProgressBar(true);
                            break;
                        }

                        case ERROR: {
                            Log.d(TAG, "onChanged: we have an error with the edited user.");
                                showProgressBar(false);
                                sendMessage("This username is taken");
                                startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
                                finish();

                            break;

                        }

                        case AUTHENTICATED: {

                            Log.d(TAG, "onChanged: this user's changes are getting saved into the shared preferences");
                            showProgressBar(false);

                            if(updateResponseStateData.data != null) {

                                Log.d(TAG, "onChanged:THIS IS ANNOYING ME SESSION: " + updateResponseStateData.data.currentUser.getBio());
                                sendMessage("Profile Updated");

                                    sessionManagement.editUserDetails(updateResponseStateData.data.currentUser);

                                    sessionManagement.printEverything("AFTER USER HAD PRESSED SAVE AND IT HAS UPDATED SUCCESSFULLY");
                                    finish();



                            }



                            break;
                        }

                        /*case NOT_AUTHENTICATED: {


                            break;

                        }*/


                    }
                }else{
                    sendMessage("This username is taken");
                    startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
                    finish();
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