package co.wishroll.views.upload;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import co.wishroll.R;
import co.wishroll.databinding.ActivityTaggingBinding;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.FilePath;
import co.wishroll.utilities.FileUtils;
import co.wishroll.viewmodel.TaggingViewModel;

public class TaggingActivity extends AppCompatActivity implements AuthListener {
    private static final String TAG = "TaggingActivity";

    ActivityTaggingBinding activityTaggingBinding;
    TaggingViewModel taggingViewModel;

    private TextView backButton, shareButton;
    private ImageView mediaThumbnail, videoIndicator;
    Bitmap thumbnailBitmap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityTaggingBinding = DataBindingUtil.setContentView(this, R.layout.activity_tagging);
        taggingViewModel = new ViewModelProvider(this).get(TaggingViewModel.class);
        activityTaggingBinding.setTaggingviewmodel(taggingViewModel);
        taggingViewModel.authListener = this;
        mediaThumbnail = findViewById(R.id.taggingThumbnail);

        backButton = findViewById(R.id.taggingBack);
        videoIndicator = findViewById(R.id.videoIndicator);
        shareButton = findViewById(R.id.shareButtonTag);

        

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Uri mediaUri = getIntent().getData();
        boolean isVideo = getIntent().getBooleanExtra("isVideo", false);
        String postPath = FilePath.getFilePath(TaggingActivity.this, mediaUri);


        //Sends post path to view model
        taggingViewModel.setPostPath(postPath);
        taggingViewModel.setVideo(isVideo);


        if(!isVideo) {
            videoIndicator.setVisibility(View.GONE);

            Log.d(TAG, "onCreate: this is not a video we got");
            try {
                thumbnailBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mediaUri);
            } catch (IOException e) {
                e.printStackTrace();
            }


            float aspectRatio = thumbnailBitmap.getWidth()/ (float) thumbnailBitmap.getHeight();
            int width = 100;
            int height = Math.round(width / aspectRatio);


        /* use height as base instead of width
        int height = 480;
        int width = Math.round(height * aspectRatio);*/

            thumbnailBitmap = Bitmap.createScaledBitmap(thumbnailBitmap, width, height, true);


        }else if(isVideo){

            thumbnailBitmap = FileUtils.getThumbnail(this, mediaUri);

            float aspectRatio = thumbnailBitmap.getWidth()/ (float) thumbnailBitmap.getHeight();
            int width = 100;
            int height = Math.round(width / aspectRatio);

            videoIndicator.setVisibility(View.VISIBLE);
            thumbnailBitmap = Bitmap.createScaledBitmap(thumbnailBitmap, width, height, true);


        }


        mediaThumbnail.setImageBitmap(thumbnailBitmap);

        observeUploadStatus();

        
        


    }
    
    public void observeUploadStatus(){


    }



    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String message) {

    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(TaggingActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void errorMessage(String message) {

    }

    @Override
    public void statusGetter(int statusCode) {

    }
}