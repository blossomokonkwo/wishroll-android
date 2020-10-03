package co.wishroll.views.reusables;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.wishroll.R;
import co.wishroll.databinding.ActivityImageBinding;
import co.wishroll.viewmodel.media.ImageViewModel;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.tools.MoreLikeThisPagerAdapter;

public class ImageActivity extends AppCompatActivity {

    ActivityImageBinding activityImageBinding;
    ImageViewModel imageViewModel;
    private FloatingActionButton fabHome;
    private ImageView backButton, mainImageView;
    private ImageView moreButton;
    String mediaUrl;
    int postId;
    ImageButton downloadButton, bookmakButton;

    private static final String TAG = "ImageActivity";

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
        downloadButton = findViewById(R.id.shareImageView);
        bookmakButton = findViewById(R.id.bookmarkImageView);

        ViewPager2 viewPager2 = findViewById(R.id.viewPagerMoreLikeThis);
        viewPager2.setAdapter(new MoreLikeThisPagerAdapter(this, postId));

        TabLayout tabLayout = findViewById(R.id.tabLayoutMoreLikeThis);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d(TAG, "inside the onConfigure tab, TabLayout mediator");

                if(position > -1) {
                    tab.setText("More Like This");
                }
            }



        });

        tabLayoutMediator.attach();





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