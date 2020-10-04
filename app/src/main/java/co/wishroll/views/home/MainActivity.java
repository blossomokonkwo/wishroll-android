package co.wishroll.views.home;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import co.wishroll.R;
import co.wishroll.databinding.ActivityMainBinding;
import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.FileUtils;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.MainViewModel;
import co.wishroll.views.registration.LoginActivity;
import co.wishroll.views.reusables.TextBodyActivity;
import co.wishroll.views.search.SearchActivity;
import co.wishroll.views.upload.TaggingActivity;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN ACTIVITY";
    ActivityMainBinding activityMainBinding;
    MainViewModel mainViewModel;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    UserRepository userRepository = applicationGraph.userRepository();
    FloatingActionButton fabUpload;
    EditText searchBarFake;
    ImageButton moreButton;
    private static final int PERMISSION_CODE = 1001;
    private static final int MEDIA_PICK_CODE = 1000;
    private int IMAGE_CROP_CODE = 1002;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        activityMainBinding.setMainviewmodel(mainViewModel);

        fabUpload = findViewById(R.id.fabUpload);
        searchBarFake = findViewById(R.id.etSearchBarMain);
        moreButton = findViewById(R.id.moreButtonMainPage);

        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);

                    }else if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        goToGallery();

                    }

                }else{
                    goToGallery();

                }

            }
        });



        searchBarFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });



        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        MainActivity.this, R.style.BottomSheetDialogTheme
                );

                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottom_sheet,
                        findViewById(R.id.bottomSheetContainer));


               bottomSheetView.findViewById(R.id.privacyPolicy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (MainActivity.this, TextBodyActivity.class);
                        viewText.putExtra("isFAQ", false);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.faqButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (MainActivity.this, TextBodyActivity.class);
                        viewText.putExtra("isFAQ", true);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();


                    }
                });


                bottomSheetView.findViewById(R.id.viewBookmarks).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewBookmarks = new Intent (MainActivity.this, PostsGridActivity.class);
                        viewBookmarks.putExtra("query", "Bookmarks");
                        startActivity(viewBookmarks);
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetView.findViewById(R.id.contactUsButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent viewText = new Intent (MainActivity.this, ContactActivity.class);
                        startActivity(viewText);
                        bottomSheetDialog.dismiss();


                    }
                });

                bottomSheetView.findViewById(R.id.deleteAccount).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        bottomSheetDialog.dismiss();


                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("All your account data and posts will be deleted.");
                        builder.setTitle("Are You Sure?");
                        builder.setCancelable(true);

                        builder.setPositiveButton("Delete Account", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sessionManagement.clearSession();
                                sessionManagement.checkLogout();
                                if(userRepository.deleteThisUser() == 200) {
                                    Glide.get(MainActivity.this).clearMemory();
                                    //Glide.get(getContext()).clearDiskCache();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                }else{
                                    ToastUtils.showToast(MainActivity.this, "Something went wrong");
                                }
                            }
                        });

                        builder.setNegativeButton("Nevermind", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                onBackPressed();
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

                                    Glide.get(MainActivity.this).clearMemory();
                                    //Glide.get(getContext()).clearDiskCache();
                                    startActivity(new Intent(MainActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();




                    }

                });



                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }

        });

    }

    public void goToGallery(){
        Intent intentUpload = new Intent();
        intentUpload.setType("*/*");
        intentUpload.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
        intentUpload.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentUpload, MEDIA_PICK_CODE );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       
        if(requestCode == MEDIA_PICK_CODE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            String mimeType = getContentResolver().getType(path);
            String extension = FileUtils.getExtension(data.getData().toString());


            assert mimeType != null;
            if(extension.equals(".mp4") || extension.equals(".mov") || extension.equals(".m4a") || extension.equals(".avi")
                    || extension.equals(".wmv") || mimeType.equals("video/mp4") || mimeType.contains("video") ){

                Intent taggingVideoIntent = new Intent(MainActivity.this, TaggingActivity.class);






                taggingVideoIntent.setData(path);
                taggingVideoIntent.putExtra("isVideo", true);
                startActivity(taggingVideoIntent);


            }else if(extension.equals(".jpg") || extension.equals(".gif") || extension.equals(".png") || extension.equals(".jpeg")
                    || extension.equals(".PNG")|| mimeType.equals("image/jpeg") || mimeType.contains("image")){

                startCropImageActivity(path, IMAGE_CROP_CODE);


            }
            
        }
        
        if(requestCode == IMAGE_CROP_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(result != null) {
                Intent startTagging = new Intent(MainActivity.this, TaggingActivity.class);
                startTagging.setData(result.getUri());
                startTagging.putExtra("isVideo", false);
                startActivity(startTagging);
            }
        }






    }




    private void startCropImageActivity(Uri path, int requestCode) {

        Intent  cropIntent = CropImage.activity(path)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropMenuCropButtonTitle("Done")
                    .setFixAspectRatio(false)
                    .setActivityTitle("Crop")
                    .setMultiTouchEnabled(true)
                    .getIntent(this);

        startActivityForResult(cropIntent, requestCode);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    goToGallery();
                }else{

                }
                break;
        }
    }


}