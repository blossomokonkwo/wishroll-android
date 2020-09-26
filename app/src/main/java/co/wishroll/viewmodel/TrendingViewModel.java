package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.TrendingRepository;

import static co.wishroll.WishRollApplication.applicationGraph;

public class TrendingViewModel extends ViewModel {
    //ViewModel for the Trending Fragment, Main Activity
    //fetches and displays a list of posts that are trending


    public TrendingRepository trendingRepository = applicationGraph.trendingRepository();

    public TrendingViewModel() {

    }







}
