package co.wishroll.views.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import co.wishroll.viewmodel.search.SearchViewModel;
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;

public class VideoSearchFragment extends Fragment {




    FragmentVideosearchBinding fragmentVideosearchBinding;
    View view;
    SearchViewModel searchViewModel;


    ProgressBar progressBar;
    private TextView noResults;


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
        noResults = view.findViewById(R.id.noResultsVideo);


        observeVideoSearchResults();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), listOfVideoSearchResults);
        recyclerView.setAdapter(gridRecyclerViewAdapter);




        gridRecyclerViewAdapter.setOnGridItemClickListener(new GridRecyclerViewAdapter.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                if(listOfVideoSearchResults.get(position).getMediaUrl().contains(".mp4") || listOfVideoSearchResults.get(position).getMediaUrl().contains(".mov") ){
                    Intent i = new Intent(getActivity(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", listOfVideoSearchResults.get(position).getMediaUrl());
                    i.putExtra("postId", listOfVideoSearchResults.get(position).getId());
                    i.putExtra("isBookmarked", listOfVideoSearchResults.get(position).getBookmarked());
                    i.putExtra("post", listOfVideoSearchResults.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", listOfVideoSearchResults.get(position).getMediaUrl());
                    i.putExtra("postId", listOfVideoSearchResults.get(position).getId());
                    i.putExtra("isBookmarked", listOfVideoSearchResults.get(position).getBookmarked());
                    i.putExtra("post", listOfVideoSearchResults.get(position));
                    startActivity(i);
                }
            }


        });



        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(totalItemsCount % SearchViewModel.getVideoDataSetSize() == 0) {
                    SearchViewModel.getMoreSearchResults(totalItemsCount);

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
                                break;

                            case AUTHENTICATED:
                                showProgress(false);
                                listOfVideoSearchResults.addAll(arrayListStateData.data);
                                gridRecyclerViewAdapter.notifyDataSetChanged();


                                break;

                            case ERROR:

                                if(arrayListStateData.data == null || arrayListStateData.data.size() == 0  ){
                                    noResults.setVisibility(View.VISIBLE);
                                    showProgress(false);
                                }else{
                                    noResults.setVisibility(View.INVISIBLE);
                                    showProgress(true);

                                }

                                //ToastUtils.showToast(getContext(), arrayListStateData.message);
                                break;

                            case NOT_AUTHENTICATED:
                                showProgress(false);
                                noResults.setVisibility(View.INVISIBLE);
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