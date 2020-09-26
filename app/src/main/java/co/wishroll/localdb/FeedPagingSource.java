package co.wishroll.localdb;

import androidx.annotation.NonNull;
import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class FeedPagingSource extends RxPagingSource<Integer, FeedPostEntity> {
    //good news, this class may or may not be used
    //identifies the data source
    //includes the load() method, which you must override to indicate how to retrieve paged data from the corresponding data source.

    @NonNull
    private WishRollApi wishRollApi;

    public FeedPagingSource() {
        this.wishRollApi = RetrofitInstance.getRetrofitInstance().create(WishRollApi.class);
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, FeedPostEntity>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        // Start refresh at offset 0 if undefined.
        //gets first 15 posts from backend and returns it

        Integer nextOffsetNumber = loadParams.getKey();
        if(nextOffsetNumber == null){
            nextOffsetNumber = 0;
        }

        return wishRollApi.getFeedPosts(nextOffsetNumber)
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, FeedPostEntity> toLoadResult(@NonNull List<Post> response){
        //made this take in a list<post> because that's what the api returns
        //tbh idk what this method does, i think im using it in the wrong way
        return new LoadResult.Page<Integer, FeedPostEntity>(
               transformToEntity(response), null, response.size() //using size as next paging number //RESPONSE IS BARE CAN'T REALLY DO THAT, get all instead(?)
                , LoadResult.Page.COUNT_UNDEFINED, LoadResult.Page.COUNT_UNDEFINED);


    }

    private List<FeedPostEntity> transformToEntity(List<Post> postsFromApi){
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
