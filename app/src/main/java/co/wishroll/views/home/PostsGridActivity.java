package co.wishroll.views.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
import co.wishroll.viewmodel.TrendingViewModel;
import co.wishroll.viewmodel.media.MediaViewModelFactory;
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

    private static final String TAG = "PostsGridActivity";
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
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


        postGridRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems = gridLayoutManager.getChildCount();
                totalItems = gridLayoutManager.getItemCount();
                scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems) ) {

                    isScrolling = false;

                    if(totalItems % postsGridViewModel.getDataSetSize() == 0) {
                        TrendingViewModel.setOffset(totalItems);
                        postsGridViewModel.getMorePostGridPages();
                    }
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
                            Log.d(TAG, "onChanged: Loading Trending Tags");
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
                            Log.d(TAG, "onChanged: Size of List going into adapter " + postGridList.size());
                            gridRecyclerViewAdapter.notifyDataSetChanged();





                            Log.d(TAG, "onChanged: This has been authenticated.");
                            break;
                        }

                        case NOT_AUTHENTICATED:
                            Log.d(TAG, "onChanged: Refreshed Trending Live Data");
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