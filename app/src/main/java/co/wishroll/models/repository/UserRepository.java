package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.UpdateResponse;
import co.wishroll.utilities.StateData;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

@Singleton
public class UserRepository {
    private static final String TAG = "UserRepository";



    public WishRollApi wishRollApi;
    public LiveData<StateData<UpdateResponse>> source = null;




    @Inject
    public UserRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


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


    public LiveData<StateData<UpdateResponse>> updateUser(Map<String, RequestBody> changedAttributes, MultipartBody.Part profilePicture, MultipartBody.Part bannerPicture) {
        Log.d(TAG, "updateUser: we are in the update user method of the user repository.");


        if (changedAttributes != null && profilePicture != null && bannerPicture != null) { //user changed everything
            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.updateUserDetails(changedAttributes, profilePicture, bannerPicture)
                            .onErrorReturn(new Function<Throwable, UpdateResponse>() {
                                @Override
                                public UpdateResponse apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: in the error clause of the update user method.");
                                    return null;
                                }
                            })
                            .map(new Function<UpdateResponse, StateData<UpdateResponse>>() {
                                @Override
                                public StateData<UpdateResponse> apply(UpdateResponse updateResponse) throws Exception {
                                    Log.d(TAG, "apply: in the mapping clause of the update user method.");

                                    if (updateResponse == null) {
                                        return null;
                                    } else {
                                        Log.d(TAG, "apply: this user has been successfully updated");
                                        //Log.d(TAG, "apply: edited user " + editedUser.toString());
                                        return StateData.authenticated(updateResponse);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));


        } else if (changedAttributes != null && profilePicture != null && bannerPicture == null) { //user changed profile and some text fields
            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.updateUserDetails(changedAttributes, profilePicture)
                            .onErrorReturn(new Function<Throwable, UpdateResponse>() {
                                @Override
                                public UpdateResponse apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: in the error clause of the update user method.");

                                    return null;
                                }
                            })
                            .map(new Function<UpdateResponse, StateData<UpdateResponse>>() {
                                @Override
                                public StateData<UpdateResponse> apply(UpdateResponse updateResponse) throws Exception {
                                    Log.d(TAG, "apply: in the mapping clause of the update user method.");

                                    if (updateResponse == null) {
                                        return null;
                                    } else {
                                        Log.d(TAG, "apply: this user has been successfully updated");
                                        //Log.d(TAG, "apply: edited user " + editedUser.toString());
                                        return StateData.authenticated(updateResponse);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));


        } else if (changedAttributes == null && profilePicture != null && bannerPicture == null) { //user only changed profile
            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.updateUserDetails(profilePicture)
                            .onErrorReturn(new Function<Throwable, UpdateResponse>() {
                                @Override
                                public UpdateResponse apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: in the error clause of the update user method.");

                                    return null;
                                }
                            })
                            .map(new Function<UpdateResponse, StateData<UpdateResponse>>() {
                                @Override
                                public StateData<UpdateResponse> apply(UpdateResponse updateResponse) throws Exception {
                                    Log.d(TAG, "apply: in the mapping clause of the update user method.");

                                    if (updateResponse == null) {
                                        return null;
                                    } else {
                                        Log.d(TAG, "apply: this user has been successfully updated");
                                        //Log.d(TAG, "apply: edited user " + editedUser.toString());
                                        return StateData.authenticated(updateResponse);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));


        } else if (changedAttributes == null && profilePicture == null && bannerPicture != null) { //user only changed banner
            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.updateUserDetails(bannerPicture)
                            .onErrorReturn(new Function<Throwable, UpdateResponse>() {
                                @Override
                                public UpdateResponse apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: in the error clause of the update user method.");

                                    return null;
                                }
                            })
                            .map(new Function<UpdateResponse, StateData<UpdateResponse>>() {
                                @Override
                                public StateData<UpdateResponse> apply(UpdateResponse updateResponse) throws Exception {
                                    Log.d(TAG, "apply: in the mapping clause of the update user method.");

                                    if (updateResponse == null) {
                                        return null;
                                    } else {
                                        Log.d(TAG, "apply: this user has been successfully updated");
                                        //Log.d(TAG, "apply: edited user " + editedUser.toString());
                                        return StateData.authenticated(updateResponse);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));

        } else if (changedAttributes != null && profilePicture == null && bannerPicture != null) { //user changed banner and some text fields
            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.updateUserDetails(changedAttributes, bannerPicture)
                            .onErrorReturn(new Function<Throwable, UpdateResponse>() {
                                @Override
                                public UpdateResponse apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: in the error clause of the update user method.");

                                    return null;
                                }
                            })
                            .map(new Function<UpdateResponse, StateData<UpdateResponse>>() {
                                @Override
                                public StateData<UpdateResponse> apply(UpdateResponse updateResponse) throws Exception {
                                    Log.d(TAG, "apply: in the mapping clause of the update user method.");

                                    if (updateResponse == null) {
                                        return null;
                                    } else {
                                        Log.d(TAG, "apply: this user has been successfully updated");
                                        //Log.d(TAG, "apply: edited user " + editedUser.toString());
                                        return StateData.authenticated(updateResponse);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));

        } else if (changedAttributes == null && profilePicture != null && bannerPicture != null) { //user changed both picture fields
            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.updateUserDetails(profilePicture, bannerPicture)
                            .onErrorReturn(new Function<Throwable, UpdateResponse>() {
                                @Override
                                public UpdateResponse apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: in the error clause of the update user method.");

                                    return null;
                                }
                            })
                            .map(new Function<UpdateResponse, StateData<UpdateResponse>>() {
                                @Override
                                public StateData<UpdateResponse> apply(UpdateResponse updateResponse) throws Exception {
                                    Log.d(TAG, "apply: in the mapping clause of the update user method.");

                                    if (updateResponse == null) {
                                        return null;
                                    } else {
                                        Log.d(TAG, "apply: this user has been successfully updated");
                                        //Log.d(TAG, "apply: edited user " + editedUser.toString());
                                        return StateData.authenticated(updateResponse);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));

        }else if (changedAttributes != null && profilePicture == null && bannerPicture == null) { //user changed only text fields
            source = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.updateUserDetails(changedAttributes)
                            .onErrorReturn(new Function<Throwable, UpdateResponse>() {
                                @Override
                                public UpdateResponse apply(Throwable throwable) throws Exception {
                                    Log.d(TAG, "apply: in the error clause of the update user method.");

                                    return null;
                                }
                            })
                            .map(new Function<UpdateResponse, StateData<UpdateResponse>>() {
                                @Override
                                public StateData<UpdateResponse> apply(UpdateResponse updateResponse) throws Exception {
                                    Log.d(TAG, "apply: in the mapping clause of the update user method.");

                                    if (updateResponse == null) {
                                        return null;
                                    } else {
                                        Log.d(TAG, "apply: this user has been successfully updated");
                                        //Log.d(TAG, "apply: edited user " + editedUser.toString());
                                        return StateData.authenticated(updateResponse);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));
        }

        return source;

    }


    public LiveData<StateData<User>> getUserByUsername(String username) {
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

                                if (user.getId() == 0) {
                                    Log.d(TAG, "apply: user id is equal to zero. returning the error message rn");
                                    return StateData.error("User not Found", null);

                                } else {
                                    Log.d(TAG, "apply: succeeded and returning the user rn");
                                    return StateData.authenticated(user);
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io()));


        return source;
    }














}











