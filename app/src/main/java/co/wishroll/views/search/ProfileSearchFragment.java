package co.wishroll.views.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import co.wishroll.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSearchFragment extends Fragment {



    public ProfileSearchFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_search, container, false);
    }
}