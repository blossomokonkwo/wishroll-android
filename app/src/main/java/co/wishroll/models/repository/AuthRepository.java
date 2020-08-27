package co.wishroll.models.repository;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.EValidationRequest;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.UValidationRequest;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.datamodels.ValidationResponse;
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

    public int getStatusCode() {
        return statusCode;
    }

    public AuthResponse loginUser(final LoginRequest loginRequest) {

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



    }




    public AuthResponse signupUser(final SignupRequest signupRequest) {

        Log.d(TAG, "signupUser Method of AuthRepository");

        wishRollApi.signupUser(signupRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        switch (response.code()) {
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
                Log.d(TAG, "onFailure: signupFailed.");
            }

        });

        return authResponse;

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
