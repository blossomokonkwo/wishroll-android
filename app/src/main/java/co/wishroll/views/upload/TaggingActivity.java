package co.wishroll.views.upload;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.io.IOException;

import co.wishroll.R;
import co.wishroll.databinding.ActivityTaggingBinding;
import co.wishroll.viewmodel.TaggingViewModel;

public class TaggingActivity extends AppCompatActivity {
    private static final String TAG = "TaggingActivity";

    ActivityTaggingBinding activityTaggingBinding;
    TaggingViewModel taggingViewModel;

    private TextView backButton;
    private ImageView mediaThumbnail;
    Bitmap thumbnailBitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tagging);


        activityTaggingBinding = DataBindingUtil.setContentView(this, R.layout.activity_tagging);
        taggingViewModel = new ViewModelProvider(this).get(TaggingViewModel.class);
        activityTaggingBinding.setTaggingviewmodel(taggingViewModel);
        mediaThumbnail = findViewById(R.id.taggingThumbnail);

        backButton = findViewById(R.id.taggingBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Uri mediaUri = getIntent().getData();
        boolean isVideo = getIntent().getBooleanExtra("isVideo", false);


        if(!isVideo) {
            Log.d(TAG, "onCreate: this is not a video we got");
            try {
                //thumbnailBitmap = ThumbnailUtils.createImageThumbnail(new File(mediaUri.toString()), Size., null);
                thumbnailBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mediaUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(isVideo){

            //thumbnailBitmap = ThumbnailUtils.createVideoThumbnail(new File(mediaUri.toString()), )
            //humbnailBitmap = FileUtils.getThumbnail(TaggingActivity.this, mediaUri);

            Log.d(TAG, "onCreate: this is a video we got");
        }

        /*thumbnailBitmap.setHeight(100);
        thumbnailBitmap.setWidth(100);*/

        mediaThumbnail.setImageBitmap(Bitmap.createScaledBitmap(thumbnailBitmap, 100, 100, false));


    }


}