package co.wishroll.models.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.local.SessionManagement;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class DiscoverRepository {

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "DiscoverRepository";
    



    private static int statusCode;

    private WishRollApi wishRollApi;





    @Inject
    public DiscoverRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }









    }











