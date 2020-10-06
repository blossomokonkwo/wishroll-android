package co.wishroll.views.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import co.wishroll.WishRollApplication;
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

        if(WishRollApplication.hasNetwork()) {
            observeTrendingTagsList();
            trendingRecyclerViewAdapter = new TrendingRecyclerViewAdapter(getContext(), listOfTrendingTags, this);
            myRecyclerView.setAdapter(trendingRecyclerViewAdapter);
        }else{
            
        }






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
                refreshTrendingTags();


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
                            break;
                        }
                        case ERROR: {
                            ToastUtils.showToast(getContext(), arrayListStateData.message);

                            break;

                        }

                        case AUTHENTICATED: {
                            listOfTrendingTags.addAll(arrayListStateData.data);
                            trendingRecyclerViewAdapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);




                            break;
                        }

                        case NOT_AUTHENTICATED:
                            listOfTrendingTags.clear();
                            trendingRecyclerViewAdapter.notifyDataSetChanged();

                            break;
                    }
                }


            }
        });




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
            i.putExtra("trendingTagId", listOfTrendingTags.get(position).getTrendingTagId());
            //i.putExtra("trendingTagId", position);

            startActivity(i);

        }else{
            if (listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl().contains(".mp4") ||
                    listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl().contains(".mov")) {

                Intent i = new Intent(getContext(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("postId", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getId());
                i.putExtra("mediaUrl", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl());
                i.putExtra("post", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition]);
                startActivity(i);


            }else{

                Intent i = new Intent(getContext(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                i.putExtra("postId", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getId());
                i.putExtra("mediaUrl", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition].getMediaUrl());
                i.putExtra("post", listOfTrendingTags.get(position).getTrendingTagThumbnails()[thumbnailPosition]);
                startActivity(i);


            }

        }


    }
}