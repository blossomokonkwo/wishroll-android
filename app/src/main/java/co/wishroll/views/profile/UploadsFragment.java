package co.wishroll.views.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.GridRecyclerViewAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class UploadsFragment extends Fragment {

    View view;
    private RecyclerView myRecyclerView;
    private List<Post> postGrid;



    public UploadsFragment() {
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


        view = inflater.inflate(R.layout.fragment_uploads, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.uploadsRecyclerView);

        GridRecyclerViewAdapter uploadsRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), postGrid);
        myRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myRecyclerView.setAdapter(uploadsRecyclerViewAdapter);
        return view;



    }
}