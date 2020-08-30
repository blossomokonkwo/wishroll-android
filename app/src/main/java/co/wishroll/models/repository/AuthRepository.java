package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthResource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class AuthRepository {
    //all the network calls related to registration

    private static final String TAG = "AuthRepository";

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    public static int statusCode;
    public WishRollApi wishRollApi;




    @Inject
    public AuthRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);

    }

    public WishRollApi getWishRollApi() {
        return wishRollApi;
    }

    public int getStatusCode() {
        return statusCode;
    }



    public LiveData<AuthResource<AuthResponse>> loginUser(final LoginRequest loginRequest){
        final LiveData<AuthResource<AuthResponse>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.loginUser(loginRequest)
                        .onErrorReturn(new Function<Throwable, AuthResponse>() {
                            @Override
                            public AuthResponse apply(Throwable throwable) throws Exception {
                                AuthResponse errorResponse = new AuthResponse();
                                UserModel userModel = new UserModel();
                                userModel.setId(0);
                                errorResponse.setUserModel(userModel);
                                return errorResponse;
                            }
                        })
                        .onBackpressureLatest()
                        .map(new Function<AuthResponse, AuthResource<AuthResponse>>() {
                            @Override
                            public AuthResource<AuthResponse> apply(AuthResponse authResponse) throws Exception {
                                if(authResponse.getUserModel().getId() == 0){

                                    return AuthResource.error("Please enter the correct credentials", null);
                                }else {
                                    welcomeUser(authResponse.getUserModel(), authResponse.getAccessToken());
                                    return AuthResource.authenticated(authResponse);
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io()));


        return source;
    }

    public void welcomeUser(UserModel userModel, AccessToken accessToken) {
        Log.d(TAG, "welcomeUser: saving user to sharedPreferences");
        sessionManagement.saveSession(userModel, accessToken);

    }
}
