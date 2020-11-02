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
import co.wishroll.databinding.FragmentImagesearchBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.search.SearchViewModel;
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ImageSearchFragment extends Fragment {

    FragmentImagesearchBinding fragmentImagesearchBinding;
    View view;
    SearchViewModel searchViewModel;




    private RecyclerView recyclerView;
    public ArrayList<Post> listOfImageSearchResults = new ArrayList<>();
    GridRecyclerViewAdapter gridRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    private TextView noResults;

    String query;



    public ImageSearchFragment() {
        // Required empty public constructor
    }

    public ImageSearchFragment(String query) {
        this.query = query;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentImagesearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_imagesearch, container, false);
        view = fragmentImagesearchBinding.getRoot();
        recyclerView = view.findViewById(R.id.imageRecyclerView);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentImagesearchBinding.setViewmodel(searchViewModel);
        progressBar = view.findViewById(R.id.progressBarImageSearch);


        observeImageSearchResults();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), listOfImageSearchResults);
        recyclerView.setAdapter(gridRecyclerViewAdapter);
        noResults = view.findViewById(R.id.noResultsImage);


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                if(SearchViewModel.getImageDataSetSize() > 0) {
                    if (totalItemsCount % SearchViewModel.getImageDataSetSize() == 0) {
                        SearchViewModel.getMoreSearchResults(totalItemsCount);

                    }
                }
            }
        });

        gridRecyclerViewAdapter.setOnGridItemClickListener(new GridRecyclerViewAdapter.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                if(listOfImageSearchResults.get(position).getMediaUrl().contains(".mp4") || listOfImageSearchResults.get(position).getMediaUrl().contains(".mov") ){
                    Intent i = new Intent(getActivity(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", listOfImageSearchResults.get(position).getMediaUrl());
                    i.putExtra("postId", listOfImageSearchResults.get(position).getId());
                    i.putExtra("isBookmarked", listOfImageSearchResults.get(position).getBookmarked());
                    i.putExtra("post", listOfImageSearchResults.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", listOfImageSearchResults.get(position).getMediaUrl());
                    i.putExtra("postId", listOfImageSearchResults.get(position).getId());
                    i.putExtra("isBookmarked", listOfImageSearchResults.get(position).getBookmarked());
                    i.putExtra("post", listOfImageSearchResults.get(position));
                    startActivity(i);
                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void observeImageSearchResults(){

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
                            listOfImageSearchResults.addAll(arrayListStateData.data);
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



                            break;

                        case NOT_AUTHENTICATED:
                            showProgress(false);
                            noResults.setVisibility(View.INVISIBLE);
                            listOfImageSearchResults.clear();
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