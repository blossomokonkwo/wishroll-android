package co.wishroll.views.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.annotation.NonNull;
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
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.search.ImageSearchViewModel;
import co.wishroll.views.tools.GridRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ImageSearchFragment extends Fragment {


    FragmentImagesearchBinding fragmentImagesearchBinding;
    View view;
    ImageSearchViewModel imageViewModel;

    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;


    private RecyclerView recyclerView;
    public ArrayList<Post> listOfImageSearchResults = new ArrayList<>();
    GridRecyclerViewAdapter gridRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;

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

        imageViewModel = new ViewModelProvider(this).get(ImageSearchViewModel.class);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentImagesearchBinding.setViewmodel(imageViewModel);


        observeImageSearchResults();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), listOfImageSearchResults);
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
                    if(totalItems % imageViewModel.getDataSetSize() == 0) {
                        ImageSearchViewModel.setOffset(totalItems);
                        imageViewModel.getMoreImageSearchResults();
                    }
                }


            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public void observeImageSearchResults(){

        imageViewModel.observeImageSearchResults().observe(getViewLifecycleOwner(), new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                if (arrayListStateData != null) {

                    switch (arrayListStateData.status) {
                        case LOADING:
                            break;

                        case AUTHENTICATED:
                            listOfImageSearchResults.addAll(arrayListStateData.data);
                            gridRecyclerViewAdapter.notifyDataSetChanged();
                            break;

                        case ERROR:
                            ToastUtils.showToast(getContext(), arrayListStateData.message);
                            break;

                        case NOT_AUTHENTICATED:
                            listOfImageSearchResults.clear();
                            gridRecyclerViewAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });

    }
}