package co.wishroll.views.reusables;

import android.os.Bundle;
import android.util.Log;
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
import co.wishroll.databinding.FragmentMorelikethisBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.media.MediaViewModelFactory;
import co.wishroll.viewmodel.media.MoreLikeThisViewModel;
import co.wishroll.views.tools.GridRecyclerViewAdapter;


public class MoreLikeThisFragment extends Fragment {

    private static final String TAG = "MoreLikeThisFragment";
    View view;
    private RecyclerView myRecyclerView;
    private ArrayList<Post> postGrid = new ArrayList<>();
    MoreLikeThisViewModel moreLikeThisViewModel;
    FragmentMorelikethisBinding fragmentMorelikethisBinding;
    GridRecyclerViewAdapter mltRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;

    public int postId;

    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;



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
        //trendingRecyclerViewAdapter = new TrendingRecyclerViewAdapter(getContext(), listOfTrendingTags, this);
        myRecyclerView.setAdapter(mltRecyclerViewAdapter);





        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true;
                    Log.d(TAG, "onScrollStateChanged: it did");
                }


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);



                    currentItems = gridLayoutManager.getChildCount();
                    totalItems = gridLayoutManager.getItemCount();
                    scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();

                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        Log.d(TAG, "onScrolled: TOTAL NUMBER OF ITEMS ALL TOGETHER " + totalItems);
                        isScrolling = false;
                        if (totalItems % moreLikeThisViewModel.getDataSetSize() == 0) {
                            MoreLikeThisViewModel.setOffset(totalItems);
                            moreLikeThisViewModel.getMoreLikeThese();
                        }
                    }

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
                            Log.d(TAG, "onChanged: THIS IS LOADING");
                            break;
                        }
                        case ERROR: {
                            ToastUtils.showToast(getContext(), arrayListStateData.message);
                            Log.d(TAG, "onChanged: THERE WAS AN ERROR");

                            break;

                        }

                        case AUTHENTICATED: {
                            postGrid.addAll(arrayListStateData.data);
                            Log.d(TAG, "onChanged: SIZE OF LIST GOING INTO ADAPTER " + postGrid.size());
                            mltRecyclerViewAdapter.notifyDataSetChanged();




                            Log.d(TAG, "onChanged: THIS HAS BEEN AUTHENTICATED");
                            break;
                        }

                        case NOT_AUTHENTICATED:
                            Log.d(TAG, "onChanged: REFRESHED TRENDINGLIVEDATA");
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