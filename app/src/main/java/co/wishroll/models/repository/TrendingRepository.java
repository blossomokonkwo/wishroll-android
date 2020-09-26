package co.wishroll.models.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import retrofit2.Retrofit;

@Singleton
public class TrendingRepository {

    private WishRollApi wishRollApi;

    @Inject
    public TrendingRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }






}
