package co.wishroll.views.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import co.wishroll.R;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.TrendingViewModel;


public class TrendingFragment extends Fragment {

    private static final String TAG = "TrendingFragment";

    View view;
    TrendingViewModel trendingViewModel;
    private RecyclerView myRecyclerView;

    SwipeRefreshLayout swipeRefreshLayout;





    public TrendingFragment() {
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
        view = inflater.inflate(R.layout.fragment_trending, container, false);
        myRecyclerView = view.findViewById(R.id.trendingRecyclerView);
        trendingViewModel = new ViewModelProvider(this).get(TrendingViewModel.class);
        swipeRefreshLayout = view.findViewById(R.id.swipeDiscover);



        /*GridRecyclerViewAdapter gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), postGrid);

        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myRecyclerView.setAdapter(gridRecyclerViewAdapter);*/


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