package co.wishroll.models.networking;

import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.LoginResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.SignupResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WishRollApi {
    //all wishroll api methods and calls



    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignupResponse> signupUser(@Body SignupRequest signupRequest);

    @FormUrlEncoded
    @POST("signup/{username}")
    Call<String> validateUsername(@Path("username") String username );

    @FormUrlEncoded
    @POST("signup/{email}")
    Call<String> validateEmail(@Path("email") String email);












}
