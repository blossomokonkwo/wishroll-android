package co.wishroll.views.upload;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import co.wishroll.R;
import co.wishroll.databinding.ActivityTaggingBinding;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.FileUtils;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.TaggingViewModel;

public class TaggingActivity extends AppCompatActivity implements AuthListener {
    private static final String TAG = "TaggingActivity";

    ActivityTaggingBinding activityTaggingBinding;
    TaggingViewModel taggingViewModel;

    private TextView backButton, shareButton;
    private ImageView mediaThumbnail, videoIndicator;
    Bitmap thumbnailBitmap;
    private ProgressBar progressBar;




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
        progressBar = findViewById(R.id.taggingProgressBar);

        

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        Uri mediaUri = getIntent().getData();
        Log.d(TAG, "onCreate: this is the mediaUri path " + mediaUri);
        boolean isVideo = getIntent().getBooleanExtra("isVideo", false);
        String postPath = FileUtils.getPath(TaggingActivity.this, mediaUri);
        Log.d(TAG, "onCreate: this is the path for both processed pictures and videos " + postPath);


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

            thumbnailBitmap = Bitmap.createScaledBitmap(thumbnailBitmap, width, height, true);


        }else if(isVideo){

            Log.d(TAG, "onCreate: getting video thumbnail");
            assert postPath != null;
            thumbnailBitmap = ThumbnailUtils.createVideoThumbnail(postPath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND );

            //loadVideoThumbnail(FileUtils.getPath(this, mediaUri));



            videoIndicator.setVisibility(View.VISIBLE);

        }


        mediaThumbnail.setImageBitmap(thumbnailBitmap);
        observeUploadStatus();

        
        


    }



    private void loadVideoThumbnail(String filePath){
        Glide
                .with(TaggingActivity.this)
                .load(filePath)
                .error(R.drawable.defaultprofile)
                .into(mediaThumbnail);
    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
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
        if(statusCode == 200){
            progressBar.setVisibility(View.GONE);
            ToastUtils.showToast(TaggingActivity.this, getResources().getString(R.string.post_uploaded));
            shareButton.setVisibility(View.VISIBLE);
            finish();
        }else if(statusCode == 400){
            progressBar.setVisibility(View.INVISIBLE);
            shareButton.setVisibility(View.VISIBLE);
            ToastUtils.showToast(TaggingActivity.this, getResources().getString(R.string.something_went_wrong));
        }else if(statusCode == 100){
            progressBar.setVisibility(View.VISIBLE);
            shareButton.setVisibility(View.GONE);

        }

    }
}