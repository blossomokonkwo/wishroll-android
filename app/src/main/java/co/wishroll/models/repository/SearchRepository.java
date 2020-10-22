package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.utilities.StateData;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class SearchRepository {
    private static final String TAG = "SearchRepository";

    public WishRollApi wishRollApi;




    @Inject
    public SearchRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }

    public LiveData<StateData<ArrayList<Post>>> getSearchResults(int offset, String query, String contentType){
        Log.d(TAG, "getSearchResults: offset: " + offset + "       query: " + query + "     Content-Type: " + contentType);
        final LiveData<StateData<ArrayList<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getSearchResults(query, offset, contentType)
                .onErrorReturn(new Function<Throwable, Post[]>() {
                    @Override
                    public Post[] apply(Throwable throwable) throws Exception {
                        Log.d(TAG, "apply: an error happened");
                        Post [] errorArray = new Post[0];
                        return errorArray;
                    }
                })
                .map(new Function<Post[], StateData<ArrayList<Post>>>() {
                    @Override
                    public StateData<ArrayList<Post>> apply(Post[] posts) throws Exception {
                        ArrayList<Post> searchResult = new ArrayList<>();
                        
                        if(posts == null || posts.length == 0){

                            searchResult.addAll(Arrays.asList(posts));
                            return StateData.error("Something went wrong", null);

                        }else{
                            searchResult.addAll(Arrays.asList(posts));

                                return StateData.authenticated(searchResult);

                        }

                    }
                }).subscribeOn(Schedulers.io()));

        return source;
    }


}
