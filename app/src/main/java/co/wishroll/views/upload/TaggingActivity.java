package co.wishroll.views.upload;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import co.wishroll.R;
import co.wishroll.databinding.ActivityTaggingBinding;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.FileUtils;
import co.wishroll.utilities.ThumbnailHandler;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.TaggingViewModel;

public class TaggingActivity extends AppCompatActivity implements AuthListener {

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
        boolean isVideo = getIntent().getBooleanExtra("isVideo", false);
        String postPath = FileUtils.getPath(TaggingActivity.this, mediaUri);


        //Sends post path to view model
        taggingViewModel.setPostPath(postPath);
        taggingViewModel.setVideo(isVideo);


        if(!isVideo) {
            videoIndicator.setVisibility(View.GONE);

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


            assert postPath !=null;
            ContentResolver crThumb = getContentResolver();
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 1;





                long id = 0;
                String tempId = mediaUri.toString();
                if(tempId.contains("%3A")){
                    id = Long.parseLong(tempId.substring(tempId.lastIndexOf("%3A") + 3));
                }

            thumbnailBitmap = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id, MediaStore.Video.Thumbnails.MICRO_KIND, options);


            //thumbnailBitmap = ThumbnailUtils.createVideoThumbnailpostPath, MediaStore.Images.Thumbnails.MINI_KIND);

            Uri omg = ThumbnailHandler.saveToCacheAndGetUri(thumbnailBitmap);
            String videoThumbnailPath = omg.getPath();
            taggingViewModel.setVideoThumbnailPath("/data/user/0/co.WishRoll" + videoThumbnailPath);


            videoIndicator.setVisibility(View.VISIBLE);

        }


        mediaThumbnail.setImageBitmap(thumbnailBitmap);


        


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