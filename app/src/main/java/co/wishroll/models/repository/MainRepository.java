package co.wishroll.models.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.local.SessionManagement;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class MainRepository {

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "MainRepository";
    //loads the trending page

    private static MainRepository mainRepository;


    private static int statusCode;

    private WishRollApi wishRollApi;
    //loads profile picture of current user




    @Inject
    public MainRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }



    }











