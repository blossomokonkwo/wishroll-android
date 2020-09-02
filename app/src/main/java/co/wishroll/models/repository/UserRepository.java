package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class UserRepository {

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "UserRepository";

    private static UserRepository mainRepository;


    private static int statusCode;

    private WishRollApi wishRollApi;




    @Inject
    public UserRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }



    public LiveData<StateData<User>> getUserById(int id){
        Log.d(TAG, "getUserById: in the get user by id method");
        final LiveData<StateData<User>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getUserById(id)
                .onErrorReturn(new Function<Throwable, User>() {
                    @Override
                    public User apply(Throwable throwable) throws Exception {
                        Log.d(TAG, "apply: this failed, making an error user rn");
                        return new User("", 0, false);
                    }
                })
                .map(new Function<User, StateData<User>>() {
                    @Override
                    public StateData<User> apply(User user) throws Exception {
                        
                        if(user.getId() == 0){
                            Log.d(TAG, "apply: user id is equal tp zero. returning the error message rn");
                            return StateData.error("User not Found", null);
                            
                        }else{
                            Log.d(TAG, "apply: succeeded and returning the user rn");
                            return StateData.authenticated(user);
                        }
                    }
                })
                .subscribeOn(Schedulers.io()));




        return source;
    }

    public LiveData<StateData<User>> getUserByUsername(String username){
        Log.d(TAG, "getUserById: in the get user by id method");

        final LiveData<StateData<User>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getUserByUsername(username)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                Log.d(TAG, "apply: this failed, making an error user rn");
                                return new User("", 0, false);
                            }
                        })
                        .onBackpressureLatest()
                        .map(new Function<User, StateData<User>>() {
                            @Override
                            public StateData<User> apply(User user) throws Exception {

                                if(user.getId() == 0){
                                    Log.d(TAG, "apply: user id is equal tp zero. returning the error message rn");
                                    return StateData.error("User not Found", null);

                                }else{
                                    Log.d(TAG, "apply: succeeded and returning the user rn");
                                    return StateData.authenticated(user);
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io()));




        return source;
    }










}











