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
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.EditedUser;
import co.wishroll.models.repository.datamodels.UValidationRequest;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class UserRepository {

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "UserRepository";

    private static UserRepository mainRepository;


    private static int statusCode;

    boolean usernameValid;

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
                                    Log.d(TAG, "apply: user id is equal to zero. returning the error message rn");
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

    public boolean usernameIsAvailable(String username) {

        Log.d(TAG, "usernameIsAvailable: checking if username is taken");
        UValidationRequest uValidationRequest = new UValidationRequest(username);
        
        
        wishRollApi.validateUsername(uValidationRequest).enqueue(new Callback<AuthResponse>() {
            
            
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.code() == 200) {
                    usernameValid = true;
                    Log.d(TAG, "onResponse: username is not taken");

                } else if (response.code() == 400) {
                    usernameValid = false;
                    Log.d(TAG, "onResponse: username is taken");

                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Log.d(TAG, "usernameIsAvailable: this username is available" + usernameValid);

        return usernameValid;

    }




    public LiveData<StateData<EditedUser>> updateUser(Map<String, String> changedAttributes){
        Log.d(TAG, "updateUser: we are in the update user method of the user repository.");

        final LiveData<StateData<EditedUser>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.updateUserDetails(changedAttributes)
                .onErrorReturn(new Function<Throwable, EditedUser>() {
                    @Override
                    public EditedUser apply(Throwable throwable) throws Exception {
                        Log.d(TAG, "apply: in the error clause of the update user method.");
                        return null;
                    }
                })
                .map(new Function<EditedUser, StateData<EditedUser>>() {
                    @Override
                    public StateData<EditedUser> apply(EditedUser editedUser) throws Exception {
                        Log.d(TAG, "apply: in the mapping clause of the update user method.");

                        if(editedUser == null){
                            return StateData.error("Something went went wrong", null);
                        }else{
                            Log.d(TAG, "apply: this user has been successfully updated");
                            //Log.d(TAG, "apply: edited user " + editedUser.toString());
                            return StateData.authenticated(editedUser);
                        }

                    }
                }).subscribeOn(Schedulers.io()));

        return source;

    }










}











