package co.wishroll.viewmodel.search;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class GifSearchViewModel extends ViewModel {

    private static final String TAG = "GifSearchViewModel";

    private String searchQuery;

    public GifSearchViewModel() {
    }

    public GifSearchViewModel(String searchQuery) {
        this.searchQuery = searchQuery;
        Log.d(TAG, "GifSearchViewModel: this is what the user searched " + searchQuery);
    }
}
