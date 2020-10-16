package co.wishroll.views.search;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.databinding.FragmentVideosearchBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.search.SearchViewModel;
import co.wishroll.views.tools.GridRecyclerViewAdapter;

public class VideoSearchFragment extends Fragment {



    String query;
    private static final String TAG = "TrendingFragment";

    FragmentVideosearchBinding fragmentVideosearchBinding;
    View view;
    SearchViewModel searchViewModel;

    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    ProgressBar progressBar;


    private RecyclerView recyclerView;
    public ArrayList<Post> listOfVideoSearchResults = new ArrayList<>();
    GridRecyclerViewAdapter gridRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;

    public VideoSearchFragment() {
        // Required empty public constructor
    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentVideosearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videosearch, container, false);
        view = fragmentVideosearchBinding.getRoot();
        recyclerView = view.findViewById(R.id.videoRecyclerView);


        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentVideosearchBinding.setViewmodel(searchViewModel);
        progressBar = view.findViewById(R.id.progressBarVideoSearch);


        observeVideoSearchResults();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), listOfVideoSearchResults);
        recyclerView.setAdapter(gridRecyclerViewAdapter);







        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                    if(totalItems % 15 == 0) {
                        SearchViewModel.setOffset(totalItems);
                        SearchViewModel.getMoreSearchResults();
                    }
                }


            }
        });












        // Inflate the layout for this fragment
        return view;
    }

    public void observeVideoSearchResults(){

            searchViewModel.observeSearchResults().observe(getViewLifecycleOwner(), new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                    if (arrayListStateData != null) {

                        switch (arrayListStateData.status) {
                            case LOADING:
                                showProgress(true);
                                Log.d(TAG, "onChanged: NULL LOADING HERE");
                                break;

                            case AUTHENTICATED:
                                showProgress(false);
                                Log.d(TAG, "onChanged: VIDEO SEARCH HAS BEEN AUTHENTICATED");
                                listOfVideoSearchResults.addAll(arrayListStateData.data);
                                gridRecyclerViewAdapter.notifyDataSetChanged();
                                break;

                            case ERROR:
                                ToastUtils.showToast(getContext(), arrayListStateData.message);
                                break;

                            case NOT_AUTHENTICATED:
                                listOfVideoSearchResults.clear();
                                gridRecyclerViewAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                }
            });

    }

    public void showProgress(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }
}