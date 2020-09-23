package co.wishroll.views.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.DiscoverViewModel;
import co.wishroll.views.tools.GridRecyclerViewAdapter;


public class DiscoverFragment extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener {

    private static final String TAG = "DiscoverFragment";

    View view;
    DiscoverViewModel trendingViewModel;
    private RecyclerView myRecyclerView;
    private List<Post> postGrid;
    SwipeRefreshLayout swipeRefreshLayout;
    GestureDetector gestureDetector;



    public DiscoverFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postGrid = new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        Post basicPost = new Post();


        for(int i = 0; i < 200; i++){
            postGrid.add(basicPost);
        }

        //trendingViewModel.



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_discover, container, false);
        myRecyclerView = view.findViewById(R.id.trendingRecyclerView);

        GridRecyclerViewAdapter gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), postGrid);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myRecyclerView.setAdapter(gridRecyclerViewAdapter);
        trendingViewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);

        swipeRefreshLayout = view.findViewById(R.id.swipeDiscover);


        gestureDetector = new GestureDetector(getContext(), this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showToast(getContext(), "You Called?");

                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 4000); // Delay in millis
            }
        });


        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        swipeRefreshLayout.setOnTouchListener(this);

















        return view;





    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return true;
    }


    //Gesture Detector
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.d(TAG, "onDown: user is scrolling down ");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.d(TAG, "onSingleTapUp: the user did a single tap up");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "onScroll: the user scrolled");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.d(TAG, "onLongPress: the user long pressed");

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.d(TAG, "onFling: the user flinged the screen");
        return false;
    }
}