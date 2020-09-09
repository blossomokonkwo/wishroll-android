package co.wishroll.models.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.utilities.StateData;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

@Singleton
public class PostRepository {

    private static final String TAG = "PostRepository";

    public WishRollApi wishRollApi;

    public boolean wasSuccessful = false;
    LiveData<StateData<Completable>> source;


    @Inject
    public PostRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }

    public LiveData<StateData<Completable>> uploadPost(MultipartBody.Part post, RequestBody caption, String tags){

        if(post != null && caption != null){

            source = LiveDataReactiveStreams.fromPublisher(wishRollApi.uploadPost(post, caption)
                    .flatMapCompletable(
                            postResponse -> wishRollApi.sendTags(postResponse.getPostId(), tags))
                    .subscribeOn(Schedulers.io()).toFlowable());




        }else if(post != null && caption == null && tags != null){

            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.uploadPost(post)
                    .flatMapCompletable(postResponse -> wishRollApi.sendTags(postResponse.getPostId(), tags))
                    .subscribeOn(Schedulers.io()).toFlowable());


        }






        return source; //source is a livedata, statedata, completable

    }


}
