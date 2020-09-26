package co.wishroll.localdb;

import androidx.paging.LoadType;
import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxRemoteMediator;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.wishroll.WishRollApplication;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class FeedRemoteMediator extends RxRemoteMediator<Integer, FeedPostEntity> {
    //loads paged data from a network data source and saves it to a local database

    private String query;
    private int offset;

    private WishRollApi wishRollApi;
    private FeedDatabase feedDatabase;
    private FeedPostDao feedPostDao;

    public FeedRemoteMediator(int offset) {
        this.offset = offset;
        this.wishRollApi = RetrofitInstance.getRetrofitInstance().create(WishRollApi.class);
        this.feedDatabase = FeedDatabase.getInstance(WishRollApplication.getContext());

    }


    @NotNull
    @Override
    public Single<MediatorResult> loadSingle(@NotNull LoadType loadType, @NotNull PagingState<Integer, FeedPostEntity> pagingState) {

        int offset = 0;
        switch (loadType) {
            case REFRESH:

                break;
            case APPEND:
                FeedPostEntity feedPostItem = pagingState.lastItemOrNull();

                // You must explicitly check if the last item is null when appending,
                // since passing null to networkService is only valid for initial load.
                // If lastItem is null it means no items were loaded after the initial
                // REFRESH and there are no more items to load.

                if (feedPostItem == null) {
                    return Single.just(new MediatorResult.Success(true));
                }

                offset = feedPostItem.getId();

                break;
            case PREPEND:
                // In this example, you never need to prepend, since REFRESH will always
                // load the first page in the list. Immediately return, reporting end of
                // pagination.
                return Single.just(new MediatorResult.Success(true));


        }


        return wishRollApi.getFeedPosts(offset)
                .subscribeOn(Schedulers.io())
                .map((Function<List<Post>, MediatorResult>) response -> {
                    feedDatabase.runInTransaction(() -> {
                        if (loadType == LoadType.REFRESH) {
                            feedPostDao.clearAll(); ///replaced deleteByQuery(query); dont know the significance of that  and whether or not it was important in this use case tbh
                        }


                        // Insert new users into database, which invalidates the current
                        // PagingData, allowing Paging to present the updates in the DB.
                        feedPostDao.insertAll(transformToEntity(response));
                    });

                    return new MediatorResult.Success(response.get(response.size() - 1).getId() % 15  == 0); //replaced response.getNextKey(), not sure if this is a correct switcheroo
                })
                .onErrorResumeNext(e -> {
                    if (e instanceof IOException || e instanceof HttpException) {
                        return Single.just(new MediatorResult.Error(e));
                    }

                    return Single.error(e);
                });

    }

        public List<FeedPostEntity> transformToEntity(List<Post> postsFromApi){
        //created because i did not want to cache everything from the api like cmon we're just viewing this on a grid it really doesn't matter
        //class to select fields from the API post to cache into local database, might work idk, might not be the place to do this but we'll see
        List<FeedPostEntity> feedPostEntityList = new ArrayList<>();
        for(int i = 0; i < postsFromApi.size(); i++){

            String mediaUrl = postsFromApi.get(i).getMediaUrl();
            String thumbnailUrl = postsFromApi.get(i).getThumbnailUrl();
            int postId = postsFromApi.get(i).getId();

            FeedPostEntity feedPostEntity = new FeedPostEntity(thumbnailUrl, mediaUrl, postId);
            feedPostEntityList.add(feedPostEntity);




        }

        return feedPostEntityList;
    }

}
