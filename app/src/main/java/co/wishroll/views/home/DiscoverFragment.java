package co.wishroll.views.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.DiscoverViewModel;
import co.wishroll.views.tools.GridRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DiscoverFragment extends Fragment {

    private static final String TAG = "DiscoverFragment";

    View view;
    DiscoverViewModel trendingViewModel;
    private RecyclerView myRecyclerView;
    private List<Post> postGrid;
    SwipeRefreshLayout swipeRefreshLayout;
    GestureDetector gestureDetector;
    Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
    WishRollApi wishRollApi = retrofitInstance.create(WishRollApi.class);




    public DiscoverFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //trendingViewModel.



    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_discover, container, false);
        myRecyclerView = view.findViewById(R.id.trendingRecyclerView);
        trendingViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);

        swipeRefreshLayout = view.findViewById(R.id.swipeDiscover);

        postGrid = new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        Post basicPost = new Post();


        wishRollApi.getDiscoverPostss(0).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> trendingPosts = response.body();

                    assert trendingPosts != null;
                    
                    for(int i = 0; i < trendingPosts.size(); i++) {
                        postGrid.add(trendingPosts.get(i));
                        Log.d(TAG, "onResponse: is this even happening");

                    GridRecyclerViewAdapter gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), postGrid);

                    myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    myRecyclerView.setAdapter(gridRecyclerViewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });






        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showToast(getContext(), "You Called?");

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 200); // Delay in millis
            }
        });


        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );


        
        myRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d(TAG, "onScrollStateChanged: scroll state changed");
                int lol = recyclerView.getLayoutManager().getItemCount();

                Log.d(TAG, "onScrollStateChanged: amt of items " + lol);


                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    Log.d("-----","end");

                }



            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);



            }
        });

        

















        return view;





    }


}