package co.wishroll.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.models.domainmodels.TrendingTag;
import co.wishroll.models.repository.TrendingRepository;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class TrendingViewModel extends ViewModel {
    //ViewModel for the Trending Fragment, Main Activity
    //fetches and displays a list of posts that are trending


    public TrendingRepository trendingRepository = applicationGraph.trendingRepository();
    MediatorLiveData<StateData<ArrayList<TrendingTag>>> trendingTagLiveData = new MediatorLiveData<>();
    int START_OFFSET = 0;
    public static int offset = 0;

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset) {
        TrendingViewModel.offset = offset;
    }

    public TrendingViewModel() {
        getFirstTrendingTagPage();
    }

    public LiveData<StateData<ArrayList<TrendingTag>>> observeTrendingTags(){
        return trendingTagLiveData;
    }


    public void getFirstTrendingTagPage(){
        trendingTagLiveData.setValue(StateData.loading((ArrayList<TrendingTag>) null));
        final LiveData<StateData<ArrayList<TrendingTag>>> source = trendingRepository.getTrendingTags(START_OFFSET);
        trendingTagLiveData.addSource(source, new Observer<StateData<ArrayList<TrendingTag>>>() {
            @Override
            public void onChanged(StateData<ArrayList<TrendingTag>> trendingTagStateData) {
                trendingTagLiveData.setValue(trendingTagStateData);
                trendingTagLiveData.removeSource(source);
            }
        });
    }



    public void getMoreTrendingTagPages(){
        //trendingTagLiveData.setValue(StateData.loading((ArrayList<TrendingTag>) null));
        final LiveData<StateData<ArrayList<TrendingTag>>> source = trendingRepository.getTrendingTags(offset);
        trendingTagLiveData.addSource(source, new Observer<StateData<ArrayList<TrendingTag>>>() {
            @Override
            public void onChanged(StateData<ArrayList<TrendingTag>> trendingTagStateData) {
                trendingTagLiveData.setValue(trendingTagStateData);
                trendingTagLiveData.removeSource(source);
            }
        });
    }

    public void refreshTrendingTags(){
        trendingTagLiveData.setValue(StateData.notauthenticated((ArrayList<TrendingTag>) null));

    }






}
