package co.wishroll.models.repository;

import android.util.Log;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.LoginResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.SignupResponse;
import co.wishroll.models.repository.datamodels.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthRepository {


    private static final String TAG = "AuthRepository";

    private static AuthRepository authRepository;
    


    public static AuthRepository getInstance(){
        if (authRepository == null){
            authRepository = new AuthRepository();
        }
        return authRepository;
    }

    private WishRollApi wishRollApi;

    LoginResponse loginResponse;
    SignupResponse signupResponse;

    public AuthRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);
        LoginResponse loginResponse = null;



    }



  public LoginResponse loginUser(final LoginRequest loginRequest) {

      wishRollApi.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse( Call<LoginResponse> call, Response<LoginResponse> response) {

               if(response.isSuccessful()) {


                   loginResponse = response.body();
                   Log.d(TAG, "onResponse: just set the response body to equal a Login Response class, hopefully it isn't null: ACCESS TOKEN " + loginResponse.getAccessToken().getAccess());
                   UserModel userx = loginResponse.getUser();
                   AccessToken accessTokenx = loginResponse.getAccessToken();
               }

            }



            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
               t.printStackTrace();
                Log.d(TAG, "onFailure: this network request failed sis!!");


            }
        });


        return loginResponse;



    }




    public String validateEmail(String email){

        return email;
    }

    public SignupResponse signupUser(final SignupRequest signupRequest) {

        Log.d(TAG, "loginUser: we are now in the signup response method of the user repository");



        wishRollApi.signupUser(signupRequest).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse( Call<SignupResponse> call, Response<SignupResponse> response) {

                if(response.isSuccessful()) {
                    Log.d(TAG, "onSIGNUPResponse: in the on response thing, above us was the call to the API, this is if it was a success");

                    signupResponse = response.body();
                    Log.d(TAG, "onResSIGNUPponse: just set the response body to equal a Login Response class, hopefully it isn't null: " + signupResponse.getUser().getUsername());

                }

            }



            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                Log.d(TAG, "onFailure: this nSIGNUPetwork request failed sis!!");


            }
        });


        return signupResponse;



    }






}
