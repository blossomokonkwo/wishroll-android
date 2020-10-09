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

public class ImageSearchViewModel extends ViewModel {
    private static final String TAG = "ImageSearchViewModel";

    public SearchRepository searchRepository = applicationGraph.searchRepository();
    MediatorLiveData<StateData<ArrayList<Post>>> imageSearchLiveData = new MediatorLiveData<>();

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
        ImageSearchViewModel.offset = offset;
    }


    public ImageSearchViewModel() {
    }

    public ImageSearchViewModel(String searchQuery) {
        this.searchQuery = searchQuery;
        Log.d(TAG, "ImageSearchViewModel: this is what the user searched " + searchQuery);
        getFirstPageVideoSearchResults();

    }

    public LiveData<StateData<ArrayList<Post>>> observeImageSearchResults(){
        return imageSearchLiveData;
    }

    public void getFirstPageVideoSearchResults(){
        imageSearchLiveData.setValue(StateData.loading((ArrayList<Post>) null));
        final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, searchQuery, "image");
        imageSearchLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> videoSearchStateData) {

                if(videoSearchStateData.data != null){
                    setDataSetSize(videoSearchStateData.data.size());
                }else{
                    setDataSetSize(15);
                }

                imageSearchLiveData.setValue(videoSearchStateData);
                imageSearchLiveData.removeSource(source);
            }
        });
    }

    public void getMoreImageSearchResults(){
        final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(offset, searchQuery, "image");
        imageSearchLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> videoSearchStateData) {

                if(videoSearchStateData.data != null){
                    setDataSetSize(videoSearchStateData.data.size());
                }else{
                    setDataSetSize(15);
                }

                imageSearchLiveData.setValue(videoSearchStateData);
                imageSearchLiveData.removeSource(source);
            }
        });
    }
}
