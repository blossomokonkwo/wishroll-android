package co.wishroll.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import co.wishroll.R;
import co.wishroll.model.Notification;
import co.wishroll.model.Post;
import co.wishroll.utilities.GridRecyclerViewAdapter;
import co.wishroll.utilities.NotificationRecyclerViewAdapter;
import co.wishroll.viewmodel.NotificationViewModel;
import co.wishroll.viewmodel.PostViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TrendingFragment extends Fragment {

    View view;

    private RecyclerView myRecyclerView;
    private List<PostViewModel> postGrid;



    public TrendingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        postGrid = new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        Post basicPost = new Post();
        PostViewModel basicPostVM = new PostViewModel(basicPost);

        for(int i = 0; i < 200; i++){
            postGrid.add(basicPostVM);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_trending, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.trendingRecyclerView);

        GridRecyclerViewAdapter gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), postGrid);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myRecyclerView.setAdapter(gridRecyclerViewAdapter);
        return view;



    }
}