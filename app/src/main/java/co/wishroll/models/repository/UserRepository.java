package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import co.wishroll.models.networking.ApiService;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.data.LoginRequest;
import co.wishroll.models.repository.data.LoginResponse;
import co.wishroll.models.repository.data.SignupRequest;
import co.wishroll.models.repository.data.SignupResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserRepository {

    private static final String TAG = "UserRepository";

    private static UserRepository userRepository;

    public static UserRepository getInstance(){
        if (userRepository == null){
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    private WishRollApi wishRollApi;

    //public LoginRequest loginRequest = new LoginRequest("lol", "password"); //imitation code


    public UserRepository() {
        Retrofit retrofitInstance = ApiService.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);

    }


    public MutableLiveData<LoginResponse> loginUser(final LoginRequest loginRequest) {


        final MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();



        wishRollApi.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if(response.code() > 199 && response.code() < 300){
                    loginResponse.setValue(response.body());
                    //send something to the user, collect authorization token to store in token manager
                    Log.d(TAG, "onResponse: Successfully logged in, response value should not be null");
                    //this is also where you put stuff into the shared preferences and navigate to the main page boyyyy was this tiring
                }
                    //show something that shows that there is something wrong with your credentials

                //handle error

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                loginResponse.setValue(null);
            }
        });

        return loginResponse;



    }


    public MutableLiveData<SignupResponse> signUser(final SignupRequest signupRequest) {


        final MutableLiveData<SignupResponse> signupResponse = new MutableLiveData<>();


        wishRollApi.signupUser(signupRequest).enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {

                if(response.code() > 199 && response.code() < 300){
                    signupResponse.setValue(response.body());
                    //send something to the user, collect and store authorization token
                    //this is also where you put stuff into the shared preferences and navigate to the main page boyyyy was this tiring
                }
                //show something that shows that there is something wrong with your credentials

                //handle error

            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                t.printStackTrace();
                signupResponse.setValue(null);
            }
        });

        return signupResponse;



    }
}
