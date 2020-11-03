package co.wishroll.views.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;


public class UploadsFragment extends Fragment {
    int userId;
    ArrayList<Post> uploadsList = new ArrayList<>();
    private RecyclerView myRecyclerView;


    public UploadsFragment() {
        // Required empty public constructor
    }

    public UploadsFragment(int userId){
        this.userId = userId;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_uploads, container, false);
    }
}