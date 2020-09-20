package co.wishroll.models.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.WishRollApplication;
import co.wishroll.localdb.DiscoverDB;
import co.wishroll.localdb.DiscoverPostDao;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.local.SessionManagement;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class DiscoverRepository {

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "DiscoverRepository";
    //loads the trending page


    private DiscoverPostDao discoverPostDao;



    private static int statusCode;

    private WishRollApi wishRollApi;


    public int offset;


    @Inject
    public DiscoverRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


        DiscoverDB discoverDB = DiscoverDB.getInstance(WishRollApplication.getContext());
        discoverPostDao = discoverDB.discoverPostDao();

    }


    public void getDiscoverPostsFromAPI(int offset){
        LiveData<List<Post>> roomSource = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getDiscoverPosts(offset)
                .onErrorReturn(new Function<Throwable, List<Post>>() {
                    @Override
                    public List<Post> apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<List<Post>, List<Post>>() {
                    @Override
                    public List<Post> apply(List<Post> posts) throws Exception {
                        if(posts == null) {
                            return null;
                        }else{
                            return posts;
                        }
                    }
                })
                .subscribeOn(Schedulers.io()));




    }







    }











