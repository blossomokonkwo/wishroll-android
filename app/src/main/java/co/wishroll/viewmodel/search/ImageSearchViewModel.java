package co.wishroll.viewmodel.search;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class ImageSearchViewModel extends ViewModel {
    private static final String TAG = "ImageSearchViewModel";

    private String searchQuery;

    public ImageSearchViewModel() {

    }

    public ImageSearchViewModel(String searchQuery) {
        this.searchQuery = searchQuery;
        Log.d(TAG, "ImageSearchViewModel: this is what the user searched " + searchQuery);
    }
}
