package co.wishroll.models.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class UserRepository {



    private static int statusCode;

    public WishRollApi wishRollApi;
    //LiveData<StateData<MAYBEDELETERESPONSE>> source;


    @Inject
    public UserRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }


    //deleting users
    public int deleteThisUser(){

        wishRollApi.deleteThisAccount().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    statusCode = 200;
                }else{
                    statusCode = 400;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return statusCode;
    }

    /*

    REFERENCE FOR LIVEDATA/RXJAVA RELATIONS


    public LiveData<StateData<User>> getUserById(int id) {
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

                                if (user.getId() == 0) {
                                    Log.d(TAG, "apply: user id is equal tp zero. returning the error message rn");
                                    return StateData.error("User not Found", null);

                                } else {
                                    Log.d(TAG, "apply: succeeded and returning the user rn");
                                    return StateData.authenticated(user);
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io()));


        return source;
    }*/










}











