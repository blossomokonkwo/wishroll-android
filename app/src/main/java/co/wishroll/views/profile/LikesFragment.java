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
import co.wishroll.databinding.FragmentLikesBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.LikesViewModel;
import co.wishroll.viewmodel.ProfileViewModelFactory;
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;


public class LikesFragment extends Fragment {

        FragmentLikesBinding fragmentLikesBinding;
        int userId;
        ArrayList<Post> likesList = new ArrayList<>();
        private RecyclerView recyclerView;

        View view;
        LikesViewModel likesViewModel;

    ProgressBar progressBar;
    private TextView noResults;


    GridRecyclerViewAdapter gridRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;

    public LikesFragment() {
        // Required empty public constructor
    }

    public LikesFragment(int userId){
        this.userId = userId;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        fragmentLikesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_likes, container, false);
        view = fragmentLikesBinding.getRoot();
        recyclerView = view.findViewById(R.id.likesRecyclerView);
        likesViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(userId)).get(LikesViewModel.class);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);

        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentLikesBinding.setViewmodel(likesViewModel);
        progressBar = view.findViewById(R.id.progressBarLikedPosts);
        noResults = view.findViewById(R.id.noLikesText);

        observeLikesList();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), likesList);
        recyclerView.setAdapter(gridRecyclerViewAdapter);

        gridRecyclerViewAdapter.setOnGridItemClickListener(new GridRecyclerViewAdapter.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                if(likesList.get(position).getMediaUrl().contains(".mp4") || likesList.get(position).getMediaUrl().contains(".mov") ){
                    Intent i = new Intent(getActivity(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", likesList.get(position).getMediaUrl());
                    i.putExtra("postId", likesList.get(position).getId());
                    i.putExtra("isBookmarked", likesList.get(position).getBookmarked());
                    i.putExtra("post", likesList.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", likesList.get(position).getMediaUrl());
                    i.putExtra("postId", likesList.get(position).getId());
                    i.putExtra("isBookmarked", likesList.get(position).getBookmarked());
                    i.putExtra("post", likesList.get(position));
                    startActivity(i);
                }
            }


        });


        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(totalItemsCount % likesViewModel.getLikesDataSetSize() == 0) {
                    likesViewModel.getMoreLiked(totalItemsCount);

                }
            }
        });

        return view;
    }

    public void observeLikesList(){
        likesViewModel.observeLikes().observe(getActivity(), new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                if(arrayListStateData.status != null) {

                    switch (arrayListStateData.status) {
                        case LOADING:
                            showProgress(true);
                            break;

                        case AUTHENTICATED:
                            showProgress(false);
                            likesList.addAll(arrayListStateData.data);
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
                            likesList.clear();
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