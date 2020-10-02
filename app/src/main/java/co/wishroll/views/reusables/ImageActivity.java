package co.wishroll.views.reusables;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import co.wishroll.R;
import co.wishroll.databinding.ActivityImageBinding;
import co.wishroll.viewmodel.media.ImageViewModel;
import co.wishroll.views.home.MainActivity;

public class ImageActivity extends AppCompatActivity {

    ActivityImageBinding activityImageBinding;
    ImageViewModel imageViewModel;
    private FloatingActionButton fabHome;
    private ImageView backButton, mainImageView;
    private ImageView moreButton;
    String mediaUrl;
    int postId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_image);



        mediaUrl = getIntent().getStringExtra("mediaUrl");
        postId = getIntent().getIntExtra("postId", 0);

        activityImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        activityImageBinding.setImageviewmodel(imageViewModel);
        activityImageBinding.setMediaPostUrl(mediaUrl);

        mainImageView = findViewById(R.id.mainImageView);
        fabHome = findViewById(R.id.fabImageView);




        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageActivity.this, MainActivity.class));
                finish();
            }
        });
        backButton = findViewById(R.id.backImageView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        moreButton = findViewById(R.id.moreImageView);




            }




}