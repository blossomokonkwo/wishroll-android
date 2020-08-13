package co.wishroll.models.networking;

import co.wishroll.models.repository.data.LoginRequest;
import co.wishroll.models.repository.data.LoginResponse;
import co.wishroll.models.repository.data.SignupRequest;
import co.wishroll.models.repository.data.SignupResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WishRollApi {
    //all wishroll api methods and calls


    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    //was changed into mutable live data, not sure if this is desired or not, we shall see brethren


    @POST("signup")
    Call<SignupResponse> signupUser(@Body SignupRequest signupRequest);

    @FormUrlEncoded
    @POST("signup/username")
    Call<String> validateUsername(@Field("username") String username );

    @FormUrlEncoded
    @POST("signup/email")
    Call<String> validateEmail(@Field("email") String email);












}
