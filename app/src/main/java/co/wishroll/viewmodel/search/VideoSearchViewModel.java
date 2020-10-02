package co.wishroll.viewmodel.search;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class VideoSearchViewModel extends ViewModel {

    private static final String TAG = "VideoSearchViewModel";

    private String searchQuery;

    public VideoSearchViewModel() {
    }

    public VideoSearchViewModel(String searchQuery) {
        this.searchQuery = searchQuery;
        Log.d(TAG, "VideoSearchViewModel: this is what the user searched " + searchQuery);
    }
}
