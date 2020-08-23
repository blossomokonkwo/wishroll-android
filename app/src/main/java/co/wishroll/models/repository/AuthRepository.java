package co.wishroll.models.repository;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.LoginResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.SignupResponse;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.local.SessionManagement;
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
    

    private int statusCode;

    private WishRollApi wishRollApi;

    LoginResponse loginResponse;
    SignupResponse signupResponse;

    @Inject
    public AuthRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);
        LoginResponse loginResponse = null;
        statusCode = 0;


    }

    public int getStatusCode() {
        return statusCode;
    }

    public LoginResponse loginUser(final LoginRequest loginRequest) {

      wishRollApi.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse( Call<LoginResponse> call, Response<LoginResponse> response) {
               switch (response.code()){
                   case 100:
                       statusCode = 100;
                       break;
                   case 200:
                       loginResponse = response.body();
                       Log.d(TAG, "onResponse: ACCESS TOKEN " + loginResponse.getAccessToken().getAccess());
                       welcomeUser(loginResponse.getUserModel(), loginResponse.getAccessToken());

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
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
               t.printStackTrace();
                Log.d(TAG, "onFailure: loginRequest failed.");

            }
        });

        return loginResponse;



    }




    public String validateEmail(String email){

        return email;
    }

    public SignupResponse signupUser(final SignupRequest signupRequest) {

        Log.d(TAG, "signupUser Method of AuthRepository");

        wishRollApi.signupUser(signupRequest).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse( Call<SignupResponse> call, Response<SignupResponse> response) {

                if(response.isSuccessful()) {

                    switch (response.code()) {
                        case 100:
                            statusCode = 100;
                            break;
                        case 200:
                            signupResponse = response.body();
                            Log.d(TAG, "onResponse: ACCESS TOKEN " + signupResponse.getAccessToken().getAccess());
                            welcomeUser(signupResponse.getUserModel(), signupResponse.getAccessToken());
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
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                Log.d(TAG, "onFailure: signupFailed.");
            }

        });

        return signupResponse;

    }

    public void welcomeUser(UserModel userModel, AccessToken accessToken) {
        sessionManagement.saveSession(userModel, accessToken);

    }






}
