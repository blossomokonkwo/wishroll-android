package co.wishroll.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.model.Post;
import co.wishroll.utilities.GridRecyclerViewAdapter;


public class MoreLikeThisFragment extends Fragment {

    View view;
    private RecyclerView myRecyclerView;
    private List<Post> postGrid;



    public MoreLikeThisFragment() {
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




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_morelikethis, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.moreLikeThisRecyclerView);

        GridRecyclerViewAdapter mltRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), postGrid);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myRecyclerView.setAdapter(mltRecyclerViewAdapter);
        return view;



    }
}