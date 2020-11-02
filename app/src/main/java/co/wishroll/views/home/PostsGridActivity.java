package co.wishroll.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.databinding.ActivityPostsgridBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.PostsGridViewModel;
import co.wishroll.viewmodel.media.MediaViewModelFactory;
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;

public class PostsGridActivity extends AppCompatActivity {

    ArrayList<Post> postGridList = new ArrayList<>();
    RecyclerView postGridRecyclerView;
    ActivityPostsgridBinding activityPostsgridBinding;
    GridLayoutManager gridLayoutManager;
    PostsGridViewModel postsGridViewModel;
    GridRecyclerViewAdapter gridRecyclerViewAdapter;

    TextView activityTitle;
    ImageButton backButton;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //literally going to be used for any mass grid viewing with a title
        setContentView(R.layout.activity_postsgrid);



        String query = getIntent().getStringExtra("query");
        boolean isBookmarkQuery = getIntent().getBooleanExtra("isBookmarkQuery", false);
        int trendingTagId = getIntent().getIntExtra("trendingTagId", -1);


        activityPostsgridBinding = DataBindingUtil.setContentView(this, R.layout.activity_postsgrid);
        postsGridViewModel = new ViewModelProvider(this, new MediaViewModelFactory(trendingTagId, isBookmarkQuery)).get(PostsGridViewModel.class);

        activityTitle = findViewById(R.id.postGridTitle);
        backButton = findViewById(R.id.backPostsGrid);
        progressBar = findViewById(R.id.postGridProgressBar);
        activityTitle.setText(query);
        postGridRecyclerView = findViewById(R.id.postsGridRecyclerView);
        gridLayoutManager = new GridLayoutManager(this, 3);
        postGridRecyclerView.setLayoutManager(gridLayoutManager);

        observePostGrid();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(this, postGridList);
        postGridRecyclerView.setAdapter(gridRecyclerViewAdapter);


        postGridRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                    if (totalItemsCount % 18 == 0) {
                        postsGridViewModel.getMorePostGridPages(totalItemsCount);

                    }

            }
        });

        gridRecyclerViewAdapter.setOnGridItemClickListener(new GridRecyclerViewAdapter.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                if(postGridList.get(position).getMediaUrl().contains(".mp4") || postGridList.get(position).getMediaUrl().contains(".mov") || postGridList.get(position).getMediaUrl().contains(".MP4")){
                    Intent i = new Intent(PostsGridActivity.this, VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", postGridList.get(position).getMediaUrl());
                    i.putExtra("postId", postGridList.get(position).getId());
                    i.putExtra("isBookmarked", postGridList.get(position).getBookmarked());
                    i.putExtra("post", postGridList.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(PostsGridActivity.this, ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", postGridList.get(position).getMediaUrl());
                    i.putExtra("postId", postGridList.get(position).getId());
                    i.putExtra("isBookmarked", postGridList.get(position).getBookmarked());
                    i.putExtra("post", postGridList.get(position));
                    startActivity(i);
                }
            }
        });



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void observePostGrid(){
        postsGridViewModel.observePostGrid().observe(this, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                if (arrayListStateData != null) {

                    switch (arrayListStateData.status) {

                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            ToastUtils.showToast(PostsGridActivity.this, arrayListStateData.message);

                            break;

                        }

                        case AUTHENTICATED: {
                            showProgressBar(false);
                            postGridList.addAll(arrayListStateData.data);
                            gridRecyclerViewAdapter.notifyDataSetChanged();





                            break;
                        }

                        case NOT_AUTHENTICATED:
                            postGridList.clear();
                            gridRecyclerViewAdapter.notifyDataSetChanged();

                            break;
                    }
                }
            }
        });

    }

    public void showProgressBar(boolean isVisible){
        if(isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
}