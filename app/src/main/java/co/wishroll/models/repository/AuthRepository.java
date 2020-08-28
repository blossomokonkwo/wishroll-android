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
import co.wishroll.models.repository.datamodels.EValidationRequest;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.UValidationRequest;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.datamodels.ValidationResponse;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthResource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class AuthRepository {

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static final String TAG = "AuthRepository";

    private static AuthRepository authRepository;
    

    private static int statusCode;

    private WishRollApi wishRollApi;

    AuthResponse authResponse;


    ValidationResponse validationResponseUsername;
    ValidationResponse validationResponseEmail;


    @Inject
    public AuthRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);
        AuthResponse authResponse = null;



    }

    public WishRollApi getWishRollApi() {
        return wishRollApi;
    }

    public int getStatusCode() {
        return statusCode;
    }

    /*public AuthResponse loginUser(final LoginRequest loginRequest) {

      wishRollApi.loginUser(loginRequest).enqueue(new Callback<AuthResponse>() {
           @Override
           public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
               switch (response.code()){
                   case 100:
                       statusCode = 100;
                       break;
                   case 200:
                       authResponse = response.body();

                       Log.d(TAG, "onResponse: ACCESS TOKEN " + authResponse.getAccessToken().getAccess());
                       welcomeUser(authResponse.getUserModel(), authResponse.getAccessToken());

                       statusCode = 200;
                       break;
                   case 300:
                       statusCode = 300;
                       break;
                   case 400:
                       statusCode = 400;
                       break;
                   case 401:
                       statusCode = 401;
                       break;
                   case 404:
                       statusCode = 404;
                       break;
                   case 500:
                       statusCode = 500;
                   default:
                       statusCode = response.code();
               }

           }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
               t.printStackTrace();
                Log.d(TAG, "onFailure: loginRequest FAILED");

            }
        });

        return authResponse;



    }*/

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
                                    Log.d(TAG, "apply: this failed");
                                    return AuthResource.error("Please enter the correct credentials bae", null);
                                }else {
                                    Log.d(TAG, "apply:  this succeeded " + authResponse.getUserModel().getUsername());
                                    Log.d(TAG, "onChanged: babe this really shouldn't be null hun " + authResponse.getUserModel().getUsername() );
                                    welcomeUser(authResponse.getUserModel(), authResponse.getAccessToken());
                                    return AuthResource.authenticated(authResponse);
                                }
                            }
                        })
                .subscribeOn(Schedulers.io()));


        return source;
    }







    public LiveData<AuthResource<AuthResponse>> signupUser(final SignupRequest signupRequest) {
        final LiveData<AuthResource<AuthResponse>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.signupUser(signupRequest)
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
                                if (authResponse.getUserModel().getId() == 0) {
                                    Log.d(TAG, "apply: this failed");
                                    return AuthResource.error("Please enter the correct credentials bae", null);
                                } else {
                                    Log.d(TAG, "apply:  this succeeded " + authResponse.getUserModel().getUsername());
                                    Log.d(TAG, "onChanged: babe this really shouldn't be null hun " + authResponse.getUserModel().getUsername());
                                    welcomeUser(authResponse.getUserModel(), authResponse.getAccessToken());
                                    return AuthResource.authenticated(authResponse);
                                }
                            }
                        })
                        .subscribeOn(Schedulers.io()));


        return source;

    }





    public void welcomeUser(UserModel userModel, AccessToken accessToken) {
        Log.d(TAG, "welcomeUser: yeooo is this thing on?");
        sessionManagement.saveSession(userModel, accessToken);

    }


    public ValidationResponse checkUsername(final UValidationRequest uValidationRequest){

        wishRollApi.validateUsername(uValidationRequest).enqueue(new Callback<ValidationResponse>() {
            @Override
            public void onResponse(Call<ValidationResponse> call, Response<ValidationResponse> response) {

                    statusCode = response.code();


            }

            @Override
            public void onFailure(Call<ValidationResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                Log.d(TAG, "onFailure: check username failed");
            }
        });

        return validationResponseUsername;



    }


    public ValidationResponse checkEmail(EValidationRequest eValidationRequest){
        wishRollApi.validateEmail(eValidationRequest).enqueue(new Callback<ValidationResponse>() {
            @Override
            public void onResponse(Call<ValidationResponse> call, Response<ValidationResponse> response) {
                statusCode = response.code();
                Log.d(TAG, "onResponse: Status Code" + statusCode);
            }
            @Override
            public void onFailure(Call<ValidationResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                Log.d(TAG, "onFailure: check email failed");
            }
        });
        return validationResponseEmail;
    }







}
