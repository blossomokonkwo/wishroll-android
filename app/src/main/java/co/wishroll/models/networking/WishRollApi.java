package co.wishroll.models.networking;

import co.wishroll.models.repository.data.LoginRequest;
import co.wishroll.models.repository.data.LoginResponse;
import co.wishroll.models.repository.data.SignupRequest;
import co.wishroll.models.repository.data.SignupResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WishRollApi {
    //all wishroll api methods and calls


    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);


    @POST("signup")
    Call<SignupResponse> signupUser(@Body SignupRequest signupRequest);












}
