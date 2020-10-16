package co.wishroll.models.repository;

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

    public WishRollApi wishRollApi;




    @Inject
    public SearchRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }

    public LiveData<StateData<ArrayList<Post>>> getSearchResults(int offset, String query, String contentType){
        final LiveData<StateData<ArrayList<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getSearchResults(query, offset, contentType)
                .onErrorReturn(new Function<Throwable, Post[]>() {
                    @Override
                    public Post[] apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<Post[], StateData<ArrayList<Post>>>() {
                    @Override
                    public StateData<ArrayList<Post>> apply(Post[] posts) throws Exception {
                        ArrayList<Post> searchResult = new ArrayList<>();

                        if(posts == null){
                            return StateData.error("Something went wrong", null);
                        }else{
                            if(searchResult.addAll(Arrays.asList(posts))) {
                                return StateData.authenticated(searchResult);
                            }else{
                                return StateData.error("Something went wrong", null);
                            }
                        }

                    }
                }).subscribeOn(Schedulers.io()));

        return source;
    }


}
