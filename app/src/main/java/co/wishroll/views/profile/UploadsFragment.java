package co.wishroll.views.profile;

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
import co.wishroll.databinding.FragmentUploadsBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.ProfileViewModelFactory;
import co.wishroll.viewmodel.UploadsViewModel;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;


public class UploadsFragment extends Fragment {

    int userId;
    ArrayList<Post> uploadsList = new ArrayList<>();
    private RecyclerView recyclerView;
    FragmentUploadsBinding fragmentUploadsBinding;
    UploadsViewModel uploadsViewModel;

    View view;
    ProgressBar progressBar;
    private TextView noResults;

    GridLayoutManager gridLayoutManager;



    public UploadsFragment() {
        // Required empty public constructor
    }

    public UploadsFragment(int userId){
        this.userId = userId;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentUploadsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_uploads, container, false);
        view = fragmentUploadsBinding.getRoot();
        recyclerView = view.findViewById(R.id.uploadsRecyclerView);
        uploadsViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(userId)).get(UploadsViewModel.class);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentUploadsBinding.setViewmodel(uploadsViewModel);
        progressBar = view.findViewById(R.id.progressBarUploads);
        noResults = view.findViewById(R.id.noUploadsText);

        observeUploadsList();




//        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                if(totalItemsCount % uploadsViewModel.getUploadsDataSetSize() == 0) {
//                    uploadsViewModel.getMoreUploads(totalItemsCount);
//
//                }
//            }
//        });




        // Inflate the layout for this fragment
        return view;
    }

    public void observeUploadsList(){
        uploadsViewModel.observeUploads().observe(getActivity(), new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {

                if(arrayListStateData != null) {

                    switch (arrayListStateData.status) {
                        case LOADING:
                            showProgress(true);
                            break;

                        case AUTHENTICATED:
                            showProgress(false);
                            uploadsList.addAll(arrayListStateData.data);


                            break;

                        case ERROR:

                            if (arrayListStateData.data == null || arrayListStateData.data.size() == 0) {
                                noResults.setVisibility(View.VISIBLE);
                                showProgress(false);
                            } else {
                                noResults.setVisibility(View.INVISIBLE);
                                showProgress(true);

                            }

                            break;

                        case NOT_AUTHENTICATED:
                            showProgress(false);
                            noResults.setVisibility(View.INVISIBLE);
                            uploadsList.clear();
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