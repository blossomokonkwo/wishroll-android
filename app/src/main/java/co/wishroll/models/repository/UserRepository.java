package co.wishroll.models.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.utilities.StateData;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class UserRepository {

    private static final String TAG = "UserRepository";


    public WishRollApi wishRollApi;




    @Inject
    public UserRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }


    //deleting users
    public Completable deleteThisUser() {

        return wishRollApi.deleteThisAccount().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }




    public LiveData<StateData<User>> getUserById(int id) {

        final LiveData<StateData<User>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getUserById(id)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                return new User("", 0, false);
                            }
                        })
                        .map(new Function<User, StateData<User>>() {
                            @Override
                            public StateData<User> apply(User user) throws Exception {

                                if (user.getId() == 0) {
                                    return StateData.error("User not Found", null);

                                } else {
                                    return StateData.authenticated(user);
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io()));


        return source;
    }











}











