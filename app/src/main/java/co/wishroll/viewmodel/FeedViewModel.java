package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.FeedRepository;

import static co.wishroll.WishRollApplication.applicationGraph;

public class FeedViewModel extends ViewModel {

    FeedRepository feedRepository = applicationGraph.feedRepository();

    public FeedViewModel() {

    }




    //ViewModel for the Feed Fragment, Main Activity
    //Fetches and displays posts for current user


}
