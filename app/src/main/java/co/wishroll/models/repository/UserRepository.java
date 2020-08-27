package co.wishroll.models.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.local.SessionManagement;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class UserRepository {

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "UserRepository";

    //displays things about users

    private static UserRepository mainRepository;


    private static int statusCode;

    private WishRollApi wishRollApi;




    @Inject
    public UserRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }





    }











