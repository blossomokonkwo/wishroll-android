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

    private static final String TAG = "AuthRepository";

    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static AuthRepository authRepository;
    public static int statusCode;
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
        Log.d(TAG, "welcomeUser: saving user to sharedPreferences");
        sessionManagement.saveSession(userModel, accessToken);

    }

    public int checkUsername(String username){
        UValidationRequest uValidationRequest = new UValidationRequest(username);

        wishRollApi.validateUsername(uValidationRequest).enqueue(new Callback<ValidationResponse>() {
            @Override
            public void onResponse(Call<ValidationResponse> call, Response<ValidationResponse> response) {
                if(response.code() == 400){
                    statusCode = 400;
                    return;

                }else if(response.code() == 200){
                    statusCode = 200;
                    return;
                }
                Log.d(TAG, "onResponse: Status Code" + statusCode);


            }
            @Override
            public void onFailure(Call<ValidationResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                Log.d(TAG, "onFailure: check email failed");
            }
        });
        return statusCode;

    }


    public int checkEmail(String email){

        EValidationRequest eValidationRequest = new EValidationRequest();
        eValidationRequest.setEmail(email);
         wishRollApi.validateEmail(eValidationRequest).enqueue(new Callback<ValidationResponse>() {
            @Override
            public void onResponse(Call<ValidationResponse> call, Response<ValidationResponse> response) {
                if(response.code() == 400){
                    statusCode = 400;
                    Log.d(TAG, "onResponse: this failed this email is  taken " + statusCode);
                    //validationResponseEmail = new ValidationResponse();
                    //validationResponseEmail.setErrorMessage("400");
                    return;

                }else if(response.code() == 200){
                    statusCode = 200;
                    Log.d(TAG, "onResponse: this succeeded this email is not taken " + statusCode);
                    //validationResponseEmail = new ValidationResponse();
                    //validationResponseEmail.setErrorMessage("200");
                    return;
                }
                Log.d(TAG, "onResponse: Status Code after the if statement of the response code assignment " + statusCode);
            }
            @Override
            public void onFailure(Call<ValidationResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                Log.d(TAG, "onFailure: check email failed");
            }
        });

        Log.d(TAG, "checkEmail: this is the status of the call " + statusCode);
        return statusCode;
    }







}
