package co.wishroll.viewmodel.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.SearchRepository;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class GifSearchViewModel extends ViewModel {

    private static final String TAG = "ImageSearchViewModel";

    public SearchRepository searchRepository = applicationGraph.searchRepository();
    MediatorLiveData<StateData<ArrayList<Post>>> gifSearchLiveData = new MediatorLiveData<>();

    final int START_OFFSET = 0;

    public static int offset = 0;
    public int dataSetSize = 0;

    private String searchQuery;

    public int getDataSetSize() {
        return dataSetSize;
    }

    public void setDataSetSize(int dataSetSize) {
        this.dataSetSize = dataSetSize;
    }

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset) {
        GifSearchViewModel.offset = offset;
    }


    public GifSearchViewModel() {
    }

    public GifSearchViewModel(String searchQuery) {
        this.searchQuery = searchQuery;
        Log.d(TAG, "ImageSearchViewModel: this is what the user searched " + searchQuery);
        getFirstPageGifSearchResults();

    }

    public LiveData<StateData<ArrayList<Post>>> observeGifSearchResults() {
        return gifSearchLiveData;
    }

    public void getFirstPageGifSearchResults() {
        gifSearchLiveData.setValue(StateData.loading((ArrayList<Post>) null));
        final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, searchQuery, "gif");
        gifSearchLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> gifSearchStateData) {

                if (gifSearchStateData.data != null) {
                    setDataSetSize(gifSearchStateData.data.size());
                } else {
                    setDataSetSize(15);
                }

                gifSearchLiveData.setValue(gifSearchStateData);
                gifSearchLiveData.removeSource(source);
            }
        });
    }

    public void getMoreImageSearchResults() {
        final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(offset, searchQuery, "gif");
        gifSearchLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> gifSearchStateData) {

                if (gifSearchStateData.data != null) {
                    setDataSetSize(gifSearchStateData.data.size());
                } else {
                    setDataSetSize(15);
                }

                gifSearchLiveData.setValue(gifSearchStateData);
                gifSearchLiveData.removeSource(source);
            }
        });
    }
}
