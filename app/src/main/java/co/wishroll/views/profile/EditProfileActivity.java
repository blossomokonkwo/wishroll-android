package co.wishroll.views.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mikhaellopez.circularimageview.CircularImageView;

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


    private ImageButton backButton, editBannerButton;
    private CircularImageView editProfilePicture;
    private Button saveButton;
    EditText editEmail, editUsername, editName, editBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);



        editProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        editProfileBinding.setEditProfileViewModel(editProfileViewModel);


        backButton = findViewById(R.id.backEditProfileView);
        editBannerButton = findViewById(R.id.editBanneButtonrProfileView);
        editProfilePicture = findViewById(R.id.editPictureProfileView);
        saveButton = findViewById(R.id.bsaveChanges);
        editEmail = findViewById(R.id.emailEdit);
        editUsername = findViewById(R.id.usernameEdit);
        editName = findViewById(R.id.nameEdit);
        editBio = findViewById(R.id.bioEdit);
        progressBar = findViewById(R.id.editProfileProgressBar);

        editEmail.setText(sessionManagement.getEmail());
        editUsername.setText(sessionManagement.getUsername());
        editName.setText(sessionManagement.getName());
        editBio.setText(sessionManagement.getBio());









        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }



    public void observeChangedUser(){
        editProfileViewModel.observeEditsMade().observe(this, new Observer<StateData<EditedUser>>() {
            @Override
            public void onChanged(StateData<EditedUser> editedUserStateData) {
                if(editedUserStateData != null){
                    switch (editedUserStateData.status){

                        case LOADING: {
                                showProgressBar(true);
                            break;
                        }
                        case ERROR: {
                                showProgressBar(false);
                                sendMessage("Something went wrong.");

                            break;

                        }

                        case AUTHENTICATED: {
                            showProgressBar(false);
                            if(editedUserStateData.data != null) {
                                if(sessionManagement.editUserDetails(editedUserStateData.data)){
                                    sendMessage("Profile Updated");
                                    finish();
                                }

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
        ToastUtils.showToast(this, message);
    }

    @Override
    public void errorMessage(String message) {
        ToastUtils.showToast(this, message);


    }

    @Override
    public void statusGetter(int statusCode) {

    }
}