package co.wishroll.views.reusables;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.databinding.FragmentMorelikethisBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.media.MediaViewModelFactory;
import co.wishroll.viewmodel.media.MoreLikeThisViewModel;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;


public class MoreLikeThisFragment extends Fragment {

    View view;
    private RecyclerView myRecyclerView;
    private ArrayList<Post> postGrid = new ArrayList<>();
    MoreLikeThisViewModel moreLikeThisViewModel;
    FragmentMorelikethisBinding fragmentMorelikethisBinding;
    GridRecyclerViewAdapter mltRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;

    public int postId;





    public MoreLikeThisFragment() {
        // Required empty public constructor
    }

    public MoreLikeThisFragment(int postId) {
        this.postId = postId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMorelikethisBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_morelikethis, container, false);
        view = fragmentMorelikethisBinding.getRoot();
        moreLikeThisViewModel = new ViewModelProvider(this, new MediaViewModelFactory(postId)).get(MoreLikeThisViewModel.class);
        fragmentMorelikethisBinding.setMorelikethisviewmodel(moreLikeThisViewModel);

        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        myRecyclerView = view.findViewById(R.id.moreLikeThisRecyclerView);
        myRecyclerView.setLayoutManager(gridLayoutManager);


        observeMoreLikeThis();
        mltRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), postGrid);
        myRecyclerView.setAdapter(mltRecyclerViewAdapter);

        mltRecyclerViewAdapter.setOnGridItemClickListener(new GridRecyclerViewAdapter.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                if(postGrid.get(position).getMediaUrl().contains(".mp4") || postGrid.get(position).getMediaUrl().contains(".mov") ){
                    Intent i = new Intent(getActivity(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", postGrid.get(position).getMediaUrl());
                    i.putExtra("postId", postGrid.get(position).getId());
                    i.putExtra("isBookmarked", postGrid.get(position).getBookmarked());
                    i.putExtra("post", postGrid.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", postGrid.get(position).getMediaUrl());
                    i.putExtra("postId", postGrid.get(position).getId());
                    i.putExtra("isBookmarked", postGrid.get(position).getBookmarked());
                    i.putExtra("post", postGrid.get(position));
                    startActivity(i);
                }
            }
        });





        myRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                if(totalItemsCount % moreLikeThisViewModel.getDataSetSize() == 0) {
                    moreLikeThisViewModel.getMoreLikeThese(totalItemsCount);

                }
                //moreLikeThisViewModel.getMoreLikeThese(view.getAdapter().getItemCount());
            }
        });































        return view;



    }

    public void observeMoreLikeThis(){
        moreLikeThisViewModel.observeMoreLikeThis().observe(getViewLifecycleOwner(), new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {

                if (arrayListStateData != null) {

                    switch (arrayListStateData.status) {
                        case LOADING: {
                            postGrid.clear();
                            break;
                        }
                        case ERROR: {
                            //ToastUtils.showToast(getContext(), arrayListStateData.message);
                            //Log.d(TAG, "onChanged: THERE WAS AN ERROR");

                            break;

                        }

                        case AUTHENTICATED: {
                            postGrid.addAll(arrayListStateData.data);
                            mltRecyclerViewAdapter.notifyDataSetChanged();




                            break;
                        }

                        case NOT_AUTHENTICATED:
                            postGrid.clear();
                            mltRecyclerViewAdapter.notifyDataSetChanged();
                            //swipeRefreshLayout.setRefreshing(false);

                            break;
                    }
                    }


                }


        });
    }
}