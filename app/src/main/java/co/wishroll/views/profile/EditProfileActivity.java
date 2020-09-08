package co.wishroll.views.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    private  int IMAGE_REQUEST_CODE = 21;
    private int BACKGROUND_IMAGE_REQUEST_CODE = 30;



    String profileURL = "";
    String backgroundURL = "";


    private ImageButton backButton, editBackground;
    CircularImageView profilePicture;
    RelativeLayout editProfilePictureButton;
    private Bitmap bitmap;
    ImageView backgroundProfile;
    private Bitmap backgroundBitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);



        editProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        editProfileBinding.setEditProfileViewModel(editProfileViewModel);
        editProfileViewModel.authListener = this;

        backButton = findViewById(R.id.backEditProfileView);
        profilePicture =  findViewById(R.id.editPictureProfileView); //the only profile picture
        editProfilePictureButton =  findViewById(R.id.editProfilePictureButton); //the full layout that encapsulates the profile picture

        editBackground = findViewById(R.id.editBanneButtonrProfileView);
        backgroundProfile = findViewById(R.id.bannerEditProfileView);

        progressBar = findViewById(R.id.editProfileProgressBar);

        editProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        goToProfileGallery();


            }
        });


        editBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToBackgroundGallery();
            }
        });




        observeChangedUser();


















        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
                finish();
            }
        });








    }

    public void getProfileURLString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageInByte, Base64.DEFAULT);
    }



    private void ImageUpload(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    public void goToBackgroundGallery(){
        Intent intentBackground = new Intent();
        intentBackground.setType("image/*");
        intentBackground.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentBackground, BACKGROUND_IMAGE_REQUEST_CODE );
    }

    public void goToProfileGallery(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, IMAGE_REQUEST_CODE );
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();
            Log.d(TAG, "onActivityResult: PROFILE URL: " + path);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                profilePicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if(requestCode == BACKGROUND_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null){ //
            Uri paths = data.getData();
            Log.d(TAG, "onActivityResult: BACKGROUND URL: " + paths);

            try {
                backgroundBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), paths);
                backgroundProfile.setImageBitmap(backgroundBitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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
                                    startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
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