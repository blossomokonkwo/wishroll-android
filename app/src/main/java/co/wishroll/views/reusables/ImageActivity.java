package co.wishroll.views.reusables;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import co.wishroll.models.domainmodels.PostCreator;
import co.wishroll.utilities.FileUtils;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.media.ImageViewModel;
import co.wishroll.viewmodel.media.MediaViewModelFactory;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.tools.MoreLikeThisPagerAdapter;

import static co.wishroll.WishRollApplication.getContext;
import static co.wishroll.WishRollApplication.getInstance;

public class ImageActivity extends AppCompatActivity {

    ActivityImageBinding activityImageBinding;
    ImageViewModel imageViewModel;
    private FloatingActionButton fabHome;
    private ImageView backButton, mainImageView;
    private ImageView moreButton;
    String mediaUrl;
    int postId;
    Boolean isBookmarked;
    ImageButton downloadButton, shareButton;
    ToggleButton bookmarkButton;
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
        downloadButton = findViewById(R.id.downloadImageView);
        bookmarkButton = findViewById(R.id.bookmarkImageView);
        shareButton = findViewById(R.id.shareImageView);


        Log.d(TAG, "onCreate: this post has been bookmarked already fam " + isBookmarked);



        observeThisPost();




        ViewPager2 viewPager2 = findViewById(R.id.viewPagerMoreLikeThis);
        viewPager2.setAdapter(new MoreLikeThisPagerAdapter(this, postId));

        if(postId == 0){
            ToastUtils.showToast(this, "Post Not Found");
            mainImageView.setVisibility(View.GONE);
            downloadButton.setVisibility(View.GONE);
            bookmarkButton.setVisibility(View.GONE);
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



        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.setAction(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_STREAM,  Uri.parse(mediaUrl));

                intent.setType("image/*");

                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                Intent shareIntent = Intent.createChooser(intent, "Share to: ");

                Log.d(TAG, "onClick: UMMM??? " +  MediaStore.Images.Media.getContentUri(mediaUrl));
                startActivity(shareIntent);
            }
        });



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


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownloading();
            }
        });


    }


    public void observeThisPost(){
        imageViewModel.observeThisPost().observe(this, new Observer<StateData<Post>>() {
            @Override
            public void onChanged(StateData<Post> postStateData) {
                if(postStateData != null){
                    switch (postStateData.status) {

                        case LOADING: {
                            Log.d(TAG, "onChanged: BOOKMARK STATUS IS LOADING");
                            //bookmarkButton.drawa.setVisibility();
                            Log.d(TAG, "onChanged: Loading Trending Tags");
                            break;
                        }
                        case ERROR: {
                            ToastUtils.showToast(getContext(), postStateData.message);

                            break;

                        }

                        case AUTHENTICATED: {
                            Log.d(TAG, "onChanged: AUTHENTICATED GETTING POSTS BOOKMARK STATUS");
                            if(postStateData.data.getBookmarked()){
                                bookmarkButton.setChecked(true);
                            }else{
                                bookmarkButton.setChecked(false);
                            }

                           PostCreator postCreator = postStateData.data.getCreator();





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
        Log.d(TAG, "onBookmarkClicked: BOOKMARK HAS BEEN CLICKED");

        Log.d(TAG, "onBookmarkClicked: you are trying to bookmark this, true or false: " + bookmarkButton.isChecked());

        imageViewModel.toggleBookmarking(bookmarkButton.isChecked());

        if(bookmarkButton.isChecked()){

            ToastUtils.showToast(this, "Added to Bookmarks");

        }else{

            ToastUtils.showToast(this, "Removed from Bookmarks");

        }

    }

    private void startDownloading() {

        ToastUtils.showToast(ImageActivity.this, "Downloading...");

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mediaUrl));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download from WishRoll");
        request.setDescription("Downloading...");


        //FileUtils.getExtension(mediaUrl);
        Log.d(TAG, "startDownloading: EXTENSION " + FileUtils.getExtension(mediaUrl));


        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,""+System.currentTimeMillis() + FileUtils.getExtension(mediaUrl) );


        //get download service and enque file
        DownloadManager manager = (DownloadManager)getInstance().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
        ToastUtils.showToast(ImageActivity.this, "Downloaded");

    }
}