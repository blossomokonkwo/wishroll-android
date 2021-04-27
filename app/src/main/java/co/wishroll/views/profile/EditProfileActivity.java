package co.wishroll.views.profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

import co.wishroll.R;
import co.wishroll.databinding.ActivityEditProfileBinding;
import co.wishroll.models.repository.datamodels.UpdateResponse;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.FilePath;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.EditProfileViewModel;

import static co.wishroll.WishRollApplication.applicationGraph;

public class EditProfileActivity extends AppCompatActivity implements AuthListener {
    private static final String TAG = "EditProfileActivity";

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private ActivityEditProfileBinding editProfileBinding;
    EditProfileViewModel editProfileViewModel;
    private ProgressBar progressBar;
    private final int IMAGE_REQUEST_CODE = 20;
    private final int BACKGROUND_IMAGE_REQUEST_CODE = 30;
    private final int BACKGROUND_CROP_CODE = 40;
    private final int PROFILE_CROP_CODE = 50;


    String profileURL = "";
    String backgroundURL = "";


    private ImageButton backButton, editBackground;
    private CircularImageView profilePicture;
    private RelativeLayout editProfilePictureButton;
    private Bitmap bitmap;
    private ImageView backgroundProfile;
    private Bitmap backgroundBitmap;
    private final int MY_PERMISSIONS_REQUEST = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit_profile);


        editProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        editProfileBinding.setEditProfileViewModel(editProfileViewModel);
        editProfileViewModel.authListener = this;

        editProfileBinding.setBannerImageUrl(sessionManagement.getBackgroundURL());
        editProfileBinding.setProfileImageUrl(sessionManagement.getAvatarURL());

        backButton = findViewById(R.id.backEditProfileView);
        profilePicture = findViewById(R.id.editPictureProfileView); //the only profile picture
        editProfilePictureButton = findViewById(R.id.editProfilePictureButton); //the full layout that encapsulates the profile picture

        editBackground = findViewById(R.id.editBanneButtonrProfileView);
        backgroundProfile = findViewById(R.id.bannerEditProfileView);

        progressBar = findViewById(R.id.editProfileProgressBar);


        editProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        goToProfileGallery();
                    } else {
                        requestPermission();
                    }
                } else {
                    goToProfileGallery();

                }


            }
        });

        editBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        goToBackgroundGallery();
                    } else {
                        requestPermission();

                    }
                } else {
                    goToBackgroundGallery();
                }


            }
        });

        observeChangedUser();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(EditProfileActivity.this, ProfileViewActivity.class);
//                intent.putExtra("userId", sessionManagement.getCurrentUserId());
//                startActivity(intent);
//                finish();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();
            Log.d(TAG, "onActivityResult: PROFILE URL: " + path);
            startCropImageActivity(path, PROFILE_CROP_CODE);


        }

        if (requestCode == BACKGROUND_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            Log.d(TAG, "onActivityResult: BACKGROUND URL: " + path);
            startCropImageActivity(path, BACKGROUND_CROP_CODE);


        }

        if (requestCode == BACKGROUND_CROP_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (result != null) {
                try {
                    backgroundBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                backgroundProfile.setImageBitmap(backgroundBitmap);
                backgroundURL = FilePath.getFilePath(EditProfileActivity.this, result.getUri());
                editProfileViewModel.setEditBackgroundURLNow(backgroundURL);
            }
        }

        if (requestCode == PROFILE_CROP_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (result != null) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profilePicture.setImageBitmap(bitmap);
                profileURL = FilePath.getFilePath(EditProfileActivity.this, result.getUri());
                editProfileViewModel.setEditProfileURLNow(profileURL);
            }
        }


        if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Exception error = result.getError();
        }


    }


    private void startCropImageActivity(Uri path, int requestCode) {
        Intent cropIntent = null;
        if (requestCode == PROFILE_CROP_CODE) {
            cropIntent = CropImage.activity(path)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setCropMenuCropButtonTitle("Done")
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setFixAspectRatio(true)
                    .setActivityTitle("Crop")
                    .setMultiTouchEnabled(true)
                    .getIntent(this);

        } else if (requestCode == BACKGROUND_CROP_CODE) {
            cropIntent = CropImage.activity(path)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropMenuCropButtonTitle("Done")
                    .setFixAspectRatio(true)
                    .setActivityTitle("Crop")
                    .setMultiTouchEnabled(true)
                    .getIntent(this);
        }


        startActivityForResult(cropIntent, requestCode);
    }


    public void observeChangedUser() {

        Log.d(TAG, "observeChangedUser: starting to observe the edited user");
        editProfileViewModel.observeEditsMade().observe(this, new Observer<StateData<UpdateResponse>>() {
            @Override
            public void onChanged(StateData<UpdateResponse> updateResponseStateData) {
                if (updateResponseStateData != null) {

                    switch (updateResponseStateData.status) {

                        case LOADING: {
                            Log.d(TAG, "onChanged: this is loading");
                            showProgressBar(true);
                            break;
                        }

                        case ERROR: {
                            Log.d(TAG, "onChanged: we have an error with the edited user.");
                            showProgressBar(false);
                            ToastUtils.showToast(EditProfileActivity.this, "That username is taken");
                           // startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
                            finish();

                            break;

                        }

                        case AUTHENTICATED: {

                            Log.d(TAG, "onChanged: this user's changes are getting saved into the shared preferences");
                            showProgressBar(false);

                            if (updateResponseStateData.data.currentUser != null) {

                                Log.d(TAG, "onChanged:THIS IS ANNOYING ME SESSION: " + updateResponseStateData.data.currentUser.getBio());

                                sessionManagement.editUserDetails(updateResponseStateData.data.currentUser);

                                sessionManagement.printEverything("AFTER USER HAD PRESSED SAVE AND IT HAS UPDATED SUCCESSFULLY");
                                ToastUtils.showToast(EditProfileActivity.this, "Profile Updated");
                                //startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
                                finish();


                            }


                            break;
                        }

                        /*case NOT_AUTHENTICATED: {
                            break;
                        }*/


                    }
                } else {
                    ToastUtils.showToast(EditProfileActivity.this, "That username is taken");
                    //startActivity(new Intent(EditProfileActivity.this, ProfileViewActivity.class));
                    finish();
                }
            }
        });
    }







    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            ToastUtils.showToast(EditProfileActivity.this, "Please grant permission to allow Image Loading");
        }
        else{
            ActivityCompat.requestPermissions(EditProfileActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        }
    }

    private boolean checkPermission(){
        int result= ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: permission was granted");
            } else {
                ToastUtils.showToast(EditProfileActivity.this, "Please grant permission to allow Image Loading");
            }
        }

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
        ToastUtils.showToast(this, message);    }

    @Override
    public void errorMessage(String message) {
        ToastUtils.showToast(this, message);


    }

    @Override
    public void statusGetter(int statusCode) {

    }





}