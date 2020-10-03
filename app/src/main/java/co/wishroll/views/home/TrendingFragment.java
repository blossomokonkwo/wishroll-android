package co.wishroll.views.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.databinding.FragmentTrendingBinding;
import co.wishroll.models.domainmodels.TrendingTag;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.TrendingViewModel;
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;
import co.wishroll.views.tools.TrendingRecyclerViewAdapter;


public class TrendingFragment extends Fragment implements TrendingRecyclerViewAdapter.OnTrendingTagListener {

    private static final String TAG = "TrendingFragment";

    FragmentTrendingBinding fragmentTrendingBinding;
    View view;
    TrendingViewModel trendingViewModel;


    private RecyclerView myRecyclerView;
    public ArrayList<TrendingTag> listOfTrendingTags = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    TrendingRecyclerViewAdapter trendingRecyclerViewAdapter;
    LinearLayoutManager linearLayoutManager;

    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    ProgressBar progressBar;










    public TrendingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentTrendingBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trending, container, false);
        view = fragmentTrendingBinding.getRoot();
        myRecyclerView = view.findViewById(R.id.trendingRecyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeDiscover);
        progressBar = view.findViewById(R.id.progressBarTrending);

        trendingViewModel = new ViewModelProvider(this).get(TrendingViewModel.class);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(linearLayoutManager);
        fragmentTrendingBinding.setTrendingviewmodel(trendingViewModel);

        observeTrendingTagsList();
        trendingRecyclerViewAdapter = new TrendingRecyclerViewAdapter(getContext(), listOfTrendingTags, this);
        myRecyclerView.setAdapter(trendingRecyclerViewAdapter);






        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems) ) {
                    Log.d(TAG, "onScrolled: TOTAL NUMBER OF ITEMS ALL TOGETHER " + totalItems);
                    Log.d(TAG, "onScrolled: DX VALUE  " + dx + " THIS IS DY " + dy);
                    isScrolling = false;

                    if(totalItems % trendingViewModel.getDataSetSize() == 0) {
                        TrendingViewModel.setOffset(totalItems);
                        trendingViewModel.getMoreTrendingTagPages();
                    }
                }


            }
        });










        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //ToastUtils.showToast(getContext(), "You Called?");
                Log.d(TAG, "onRefresh: refreshing this list of trending tags");
                refreshTrendingTags();
                //trendingRecyclerViewAdapter.notifyDataSetChanged();


            }
        });


        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );



        return view;

    }
    
    

    public void observeTrendingTagsList(){

        trendingViewModel.observeTrendingTags().observe(getViewLifecycleOwner(), new Observer<StateData<ArrayList<TrendingTag>>>() {
            @Override
            public void onChanged(StateData<ArrayList<TrendingTag>> arrayListStateData) {
                if (arrayListStateData != null) {

                    switch (arrayListStateData.status) {

                        case LOADING: {
                            Log.d(TAG, "onChanged: THIS IS LOADING");
                            break;
                        }
                        case ERROR: {
                            ToastUtils.showToast(getContext(), arrayListStateData.message);
                            Log.d(TAG, "onChanged: THERE WAS AN ERROR");

                            break;

                        }

                        case AUTHENTICATED: {
                            listOfTrendingTags.addAll(arrayListStateData.data);
                            Log.d(TAG, "onChanged: SIZE OF LIST GOING INTO ADAPTER " + listOfTrendingTags.size());
                            trendingRecyclerViewAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);




                            Log.d(TAG, "onChanged: THIS HAS BEEN AUTHENTICATED");
                            break;
                        }

                        case NOT_AUTHENTICATED:
                            Log.d(TAG, "onChanged: REFRESHED TRENDINGLIVEDATA");
                            listOfTrendingTags.clear();
                            trendingRecyclerViewAdapter.notifyDataSetChanged();
                            //swipeRefreshLayout.setRefreshing(false);

                            break;
                    }
                }


            }
        });



        /*wishRollApi.getTrendingTags(START_OFFSET).enqueue(new Callback<TrendingTag[]>() {
            @Override
            public void onResponse(Call<TrendingTag[]> call, Response<TrendingTag[]> response) {
                Log.d(TAG, "onResponse: THIS IS THE TRENDING TAG LENGTH " + response.body().length);
                for(int i = 0; i < response.body().length; i++){
                    listOfTrendingTags.add(response.body()[i]);
                    Log.d(TAG, "onResponse: ADDING THESE IDK MAN " + i);
                }

                Log.d(TAG, "onCreateView: FINISHED LENGTH " + listOfTrendingTags.size());
                trendingRecyclerViewAdapter = new TrendingRecyclerViewAdapter(getContext(), listOfTrendingTags);
                Log.d(TAG, "onCreateView: IS THIS EVEN COMING HERE OMG");
                myRecyclerView.setAdapter(trendingRecyclerViewAdapter);


            }

            @Override
            public void onFailure(Call<TrendingTag[]> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    public void refreshTrendingTags(){
        trendingViewModel.refreshTrendingTags();
        trendingViewModel.getFirstTrendingTagPage();
    }


    @Override
    public void onTrendingTagClicked(int position, int thumbnailPosition) {
        if(thumbnailPosition == -1){
            Intent i = new Intent(getContext(), PostsGridActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            i.putExtra("query", listOfTrendingTags.get(position).getTrendingTag());
            startActivity(i);

        }else{
            if (listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl().contains(".mp4") ||
                    listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl().contains(".mov")) {

                Intent i = new Intent(getContext(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("postId", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getId());
                i.putExtra("mediaUrl", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl());
                startActivity(i);

            }else{

                Intent i = new Intent(getContext(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("postId", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getId());
                i.putExtra("mediaUrl", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl());
                startActivity(i);
            }

        }
        Log.d(TAG, "onTrendingTagClicked: you clicked on trending tag number " + position + " with the thumbnail in position " + thumbnailPosition);


    }
}