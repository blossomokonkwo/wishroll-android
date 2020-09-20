package co.wishroll.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.DiscoverRepository;

import static co.wishroll.WishRollApplication.applicationGraph;

public class DiscoverViewModel extends ViewModel {
    //ViewModel for the Trending Fragment, Main Activity
    //fetches and displays a list of posts that are trending

    DiscoverRepository discoverRepository = applicationGraph.discoverRepository();

    public final LiveData<PagedList<Post>> discoverPostList = null;

    public static int offset;

    public DiscoverViewModel() {
        offset = 0;
    }

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset) {
        DiscoverViewModel.offset = offset;
    }
}
