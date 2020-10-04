package co.wishroll.views.reusables;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.wishroll.R;
import co.wishroll.databinding.ActivityImageBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.media.ImageViewModel;
import co.wishroll.viewmodel.media.MediaViewModelFactory;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.tools.MoreLikeThisPagerAdapter;

import static co.wishroll.WishRollApplication.getContext;

public class ImageActivity extends AppCompatActivity {

    ActivityImageBinding activityImageBinding;
    ImageViewModel imageViewModel;
    private FloatingActionButton fabHome;
    private ImageView backButton, mainImageView;
    private ImageView moreButton;
    String mediaUrl;
    int postId;
    Boolean isBookmarked;
    ImageButton downloadButton;
    ToggleButton bookmakButton;
    Post postItem;

    private static final String TAG = "ImageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_image);




        mediaUrl = getIntent().getStringExtra("mediaUrl");
        postId = getIntent().getIntExtra("postId", 0);
        //isBookmarked = getIntent().getBooleanExtra("isBookmarked", false);
        postItem = getIntent().getParcelableExtra("post");
        isBookmarked = postItem.getBookmarked();
        Log.d(TAG, "onCreate: lets see if the post came thru mate " + postItem.getMediaUrl());



        activityImageBinding = DataBindingUtil.setContentView(this, R.layout.activity_image);
        imageViewModel = new ViewModelProvider(this, new MediaViewModelFactory(postId)).get(ImageViewModel.class);
        activityImageBinding.setImageviewmodel(imageViewModel);
        activityImageBinding.setMediaPostUrl(mediaUrl);


        mainImageView = findViewById(R.id.mainImageView);
        fabHome = findViewById(R.id.fabImageView);
        downloadButton = findViewById(R.id.shareImageView);
        bookmakButton = findViewById(R.id.bookmarkImageView);


        Log.d(TAG, "onCreate: this post has been bookmarked already fam " + isBookmarked);

        /*if(isBookmarked){

            //bookmakButton.setImageResource(R.drawable.ic_bookmark_hollow);
            bookmakButton.setChecked(true);

        }else{

            bookmakButton.setChecked(false);
            //bookmakButton.setImageResource(R.drawable.ic_bookmark_filled);
        }
*/
        observeThisPost();




        ViewPager2 viewPager2 = findViewById(R.id.viewPagerMoreLikeThis);
        viewPager2.setAdapter(new MoreLikeThisPagerAdapter(this, postId));

        if(postId == 0){
            ToastUtils.showToast(this, "Post Not Found");
            mainImageView.setVisibility(View.GONE);
            downloadButton.setVisibility(View.GONE);
            bookmakButton.setVisibility(View.GONE);
            viewPager2.setVisibility(View.GONE);

        }
        TabLayout tabLayout = findViewById(R.id.tabLayoutMoreLikeThis);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

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
                onBackPressed();
            }
        });

        moreButton = findViewById(R.id.moreImageView);




    }


    public void observeThisPost(){
        imageViewModel.observeThisPost().observe(this, new Observer<StateData<Post>>() {
            @Override
            public void onChanged(StateData<Post> postStateData) {
                if(postStateData != null){
                    switch (postStateData.status) {

                        case LOADING: {
                            Log.d(TAG, "onChanged: Loading Trending Tags");
                            break;
                        }
                        case ERROR: {
                            ToastUtils.showToast(getContext(), postStateData.message);

                            break;

                        }

                        case AUTHENTICATED: {
                            if(postStateData.data.getBookmarked()){
                                bookmakButton.setChecked(true);
                            }else{
                                bookmakButton.setChecked(false);
                            }




                            break;
                        }

                        case NOT_AUTHENTICATED:


                            break;
                    }
                }
            }
        });
    }

    public void onBookmarkClicked(View view) {
        Log.d(TAG, "onBookmarkClicked: you are trying to bookmark this, true or false: " + bookmakButton.isChecked());
        imageViewModel.toggleBookmarking(bookmakButton.isChecked());

    }
}