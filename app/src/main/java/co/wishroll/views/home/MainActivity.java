package co.wishroll.views.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import co.wishroll.R;
import co.wishroll.databinding.ActivityMainBinding;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.FileUtils;
import co.wishroll.viewmodel.MainViewModel;
import co.wishroll.views.profile.ProfileViewActivity;
import co.wishroll.views.tools.MainViewPagerAdapter;
import co.wishroll.views.upload.TaggingActivity;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN ACTIVITY";
    ActivityMainBinding activityMainBinding;
    MainViewModel mainViewModel;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    CircularImageView profileThumbnail;
    FloatingActionButton fabUpload;
    EditText searchBarFake;
    private static final int PERMISSION_CODE = 1001;
    private static final int MEDIA_PICK_CODE = 1000;
    private int IMAGE_CROP_CODE = 1002;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        activityMainBinding.setMainviewmodel(mainViewModel);
        activityMainBinding.setImageUrl(sessionManagement.getAvatarURL());

        fabUpload = findViewById(R.id.fabUpload);
        searchBarFake = findViewById(R.id.etSearchBarMain);
        profileThumbnail = findViewById(R.id.profileMain);

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

        profileThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "before navigating to the profile activity");
                Intent intent = new Intent(MainActivity.this, ProfileViewActivity.class);
                startActivity(intent);
                finish();
            }
        });

        searchBarFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });



        ViewPager2 viewPager2 = findViewById(R.id.viewPagerMainView);
        viewPager2.setAdapter(new MainViewPagerAdapter(this));
        TabLayout tabLayout = findViewById(R.id.tabLayoutMainView);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d(TAG, "inside the onConfigure tab, TabLayout mediator");

                switch (position){

                    case 0:{

                        tab.setText("Discover");

                        break;}

                   //TODO(saving until messaging for android comes out)
                   /* case 1:{

                        tab.setText("Messages");

                        break;}*/

                    default: {

                        tab.setText("Feed");

                        break;
                    }
                }

            }
        });
        tabLayoutMediator.attach();
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
            Log.d(TAG, "onActivityResult: mime type of chose media: " + mimeType) ;


            assert mimeType != null;
            if(extension.equals(".mp4") || extension.equals(".mov") || extension.equals(".m4a") || extension.equals(".avi")
                    || extension.equals(".wmv") || mimeType.equals("video/mp4") || mimeType.contains("video") ){

                Log.d(TAG, "onActivityResult: this is a video file: " + extension);
                Intent taggingVideoIntent = new Intent(MainActivity.this, TaggingActivity.class);
                Log.d(TAG, "onActivityResult: Structure of video path: " + path);






                taggingVideoIntent.setData(path);
                taggingVideoIntent.putExtra("isVideo", true);
                startActivity(taggingVideoIntent);


            }else if(extension.equals(".jpg") || extension.equals(".gif") || extension.equals(".png") || extension.equals(".jpeg")
                    || extension.equals(".PNG")|| mimeType.equals("image/jpeg") || mimeType.contains("image")){

                Log.d(TAG, "onActivityResult: this is an image file: " + extension);
                Log.d(TAG, "onActivityResult: Structure of image: " + path);
                startCropImageActivity(path, IMAGE_CROP_CODE);


            }
            
        }
        
        if(requestCode == IMAGE_CROP_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(result != null) {
                Log.d(TAG, "onActivityResult: getting cropped result");
                Intent startTagging = new Intent(MainActivity.this, TaggingActivity.class);
                startTagging.setData(result.getUri());
                startTagging.putExtra("isVideo", false);
                Log.d(TAG, "onActivityResult: cropped image uri " + result.getUri());
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