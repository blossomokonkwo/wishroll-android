package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.DiscoverRepository;

import static co.wishroll.WishRollApplication.applicationGraph;

public class DiscoverViewModel extends ViewModel {
    //ViewModel for the Trending Fragment, Main Activity
    //fetches and displays a list of posts that are trending



    DiscoverRepository discoverRepository = applicationGraph.discoverRepository();

}
