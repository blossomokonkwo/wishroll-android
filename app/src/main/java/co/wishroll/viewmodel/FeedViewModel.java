package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import co.wishroll.localdb.FeedDatabase;
import co.wishroll.localdb.FeedPostDao;
import co.wishroll.localdb.FeedPostEntity;
import co.wishroll.localdb.FeedRemoteMediator;
import co.wishroll.models.repository.FeedRepository;
import io.reactivex.Flowable;
import kotlinx.coroutines.CoroutineScope;

import static co.wishroll.WishRollApplication.applicationGraph;
import static co.wishroll.WishRollApplication.getContext;

public class FeedViewModel extends ViewModel {

    FeedRepository feedRepository = applicationGraph.feedRepository();

    public FeedViewModel() {

    }

    FeedPostDao feedPostDao = FeedDatabase.getInstance(getContext()).discoverPostDao();

    CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(viewModel);
    Pager<Integer, FeedPostEntity> pager =  new Pager(
            new PagingConfig(15),
            null, // initialKey,
            new FeedRemoteMediator(0), () -> feedPostDao.pagingSource());

    Flowable<PagingData<FeedPostEntity>> flowable = PagingRx.getFlowable(pager);
    PagingRx.cachedIn(flowable, viewModelScope);


    //ViewModel for the Feed Fragment, Main Activity
    //Fetches and displays posts for current user


}
