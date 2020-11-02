package co.wishroll.views.search;

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
import co.wishroll.databinding.FragmentGifsearchBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.search.SearchViewModel;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class GifSearchFragment extends Fragment {
    FragmentGifsearchBinding fragmentGifsearchBinding;
    View view;
    SearchViewModel searchViewModel;





    private RecyclerView recyclerView;
    public ArrayList<Post> listOfGifSearchResults = new ArrayList<>();
    GridRecyclerViewAdapter gridRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;
    private TextView noResults;

    ProgressBar progressBar;

    String query;



    public GifSearchFragment() {
        // Required empty public constructor
    }

    public GifSearchFragment(String query) {
        this.query = query;
        // Required empty public constructor
    }






    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentGifsearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gifsearch, container, false);
        view = fragmentGifsearchBinding.getRoot();
        recyclerView = view.findViewById(R.id.gifRecyclerView);


        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);


        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentGifsearchBinding.setViewmodel(searchViewModel);

        progressBar = view.findViewById(R.id.progressBarGifSearch);


        observeGifSearchResults();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), listOfGifSearchResults);
        recyclerView.setAdapter(gridRecyclerViewAdapter);
        noResults = view.findViewById(R.id.noResultsGif);


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(totalItemsCount % SearchViewModel.getGifDataSetSize() == 0) {
                    SearchViewModel.getMoreSearchResults(totalItemsCount);

                }

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void observeGifSearchResults(){

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
                            listOfGifSearchResults.addAll(arrayListStateData.data);
                            gridRecyclerViewAdapter.notifyDataSetChanged();


                            break;

                        case ERROR:
                            showProgress(false);

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
                            listOfGifSearchResults.clear();
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