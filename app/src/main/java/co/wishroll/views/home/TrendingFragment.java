package co.wishroll.views.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.GridRecyclerViewAdapter;
import co.wishroll.viewmodel.TrendingViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class TrendingFragment extends Fragment {

    View view;
    TrendingViewModel trendingViewModel;
    private RecyclerView myRecyclerView;
    private List<Post> postGrid;



    public TrendingFragment() {
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

        trendingViewModel = new ViewModelProvider(this).get(TrendingViewModel.class);
        //trendingViewModel.


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