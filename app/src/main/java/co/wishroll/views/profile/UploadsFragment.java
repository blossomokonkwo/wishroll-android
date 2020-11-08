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
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;


public class UploadsFragment extends Fragment {

    int userId;
    ArrayList<Post> uploadsList = new ArrayList<>();
    private RecyclerView recyclerView;
    FragmentUploadsBinding fragmentUploadsBinding;
    UploadsViewModel uploadsViewModel;

    View view;
    ProgressBar progressBar;
    private TextView noResults;

    GridRecyclerViewAdapter gridRecyclerViewAdapter;
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
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentUploadsBinding.setViewmodel(uploadsViewModel);
        progressBar = view.findViewById(R.id.progressBarUploads);
        noResults = view.findViewById(R.id.noUploadsText);

        observeUploadsList();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), uploadsList);
        recyclerView.setAdapter(gridRecyclerViewAdapter);

        gridRecyclerViewAdapter.setOnGridItemClickListener(new GridRecyclerViewAdapter.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                if(uploadsList.get(position).getMediaUrl().contains(".mp4") || uploadsList.get(position).getMediaUrl().contains(".mov") ){
                    Intent i = new Intent(getActivity(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", uploadsList.get(position).getMediaUrl());
                    i.putExtra("postId", uploadsList.get(position).getId());
                    i.putExtra("isBookmarked", uploadsList.get(position).getBookmarked());
                    i.putExtra("post", uploadsList.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", uploadsList.get(position).getMediaUrl());
                    i.putExtra("postId", uploadsList.get(position).getId());
                    i.putExtra("isBookmarked", uploadsList.get(position).getBookmarked());
                    i.putExtra("post", uploadsList.get(position));
                    startActivity(i);
                }
            }


        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(totalItemsCount % uploadsViewModel.getUploadsDataSetSize() == 0) {
                    uploadsViewModel.getMoreUploads(totalItemsCount);

                }
            }
        });




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
                            gridRecyclerViewAdapter.notifyDataSetChanged();


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