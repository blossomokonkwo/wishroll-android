package co.wishroll.views.reusables;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.databinding.ActivityVideoBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.media.MediaViewModelFactory;
import co.wishroll.viewmodel.media.VideosViewModel;
import co.wishroll.views.tools.video.VerticalSpacingItemDecorator;
import co.wishroll.views.tools.video.VideoPlayerRecyclerAdapter;
import co.wishroll.views.tools.video.VideoPlayerRecyclerView;

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = "VideoActivity";

    ActivityVideoBinding activityVideoBinding;
    private ArrayList<Post> videosList = new ArrayList<>();
    private VideoPlayerRecyclerView recyclerView;
    private ImageButton backButton;
    VideoPlayerRecyclerAdapter adapter;
    VideosViewModel videosViewModel;
    String mediaUrl;
    ProgressBar videoMainProgressBar;
    int postId;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_video);

        postId = getIntent().getIntExtra("postId", 0);
        mediaUrl = getIntent().getStringExtra("mediaUrl");

        activityVideoBinding = DataBindingUtil.setContentView(this, R.layout.activity_video);
        backButton = findViewById(R.id.backVideoView);


        videosViewModel = new ViewModelProvider(this, new MediaViewModelFactory(postId)).get(VideosViewModel.class);
        activityVideoBinding.setVideosviewmodel(videosViewModel);
        //videoMainProgressBar = findViewById(R.id.videoProgressBar);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerView = new VideoPlayerRecyclerView(VideoActivity.this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(0);
        recyclerView.addItemDecoration(itemDecorator);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(recyclerView);

        observeVideosList();
        adapter = new VideoPlayerRecyclerAdapter(videosList, VideoActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setKeepScreenOn(true);
        recyclerView.smoothScrollToPosition(videosList.size()+1);











    }

    public void observeVideosList(){
            videosViewModel.observeVideosList().observe(this, new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                    if(arrayListStateData != null){
                        switch (arrayListStateData.status){
                            case LOADING:
                                //videoMainProgressBar.setVisibility(View.VISIBLE);
                                Log.d(TAG, "onChanged: Loading Trending Tags");
                                break;

                            case ERROR:
                                //videoMainProgressBar.setVisibility(View.GONE);
                                ToastUtils.showToast(VideoActivity.this, arrayListStateData.message);
                                finish();

                                break;



                            case AUTHENTICATED:
                                //videoMainProgressBar.setVisibility(View.GONE);
                                videosList.addAll(arrayListStateData.data);
                                /*recyclerView.setVideosList(videosList);
                                Log.d(TAG, "onChanged: Size of List going into adapter " + videosList.size());
                                adapter.notifyDataSetChanged();*/

                                recyclerView.setVideosList(videosList);
                               adapter.notifyDataSetChanged();

                                for(int i = 0; i< videosList.size(); i++){
                                    Log.d(TAG, "onChanged: video id " + videosList.get(i).getId());
                                    Log.d(TAG, "onChanged: video url " + videosList.get(i).getMediaUrl());
                                    Log.d(TAG, "onChanged: video creator " + videosList.get(i).getCreator().getUsername());

                                }




                                Log.d(TAG, "onChanged: This has been authenticated.");
                                break;


                            case NOT_AUTHENTICATED:
                                Log.d(TAG, "onChanged: Refreshed");
                                videosList.clear();
                                adapter.notifyDataSetChanged();

                                break;
                        }
                        }
                    }

            });
    }

   /* public void onBookmarkClicked(View view) {
        Log.d(TAG, "onBookmarkClicked: you are trying to bookmark this, true or false: " + bookmakButton.isChecked());
        imageViewModel.toggleBookmarking(bookmakButton.isChecked());
        if(bookmakButton.isChecked()){
            ToastUtils.showToast(this, "Added to Bookmarks");
        }else{
            ToastUtils.showToast(this, "Removed from Bookmarks");
        }

    }*/




}