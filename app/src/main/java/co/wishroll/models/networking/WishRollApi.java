package co.wishroll.models.networking;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.EValidationRequest;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.SignupRequestMany;
import co.wishroll.models.repository.datamodels.UValidationRequest;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WishRollApi {
    //all wishroll api methods and calls



    @POST("login")
    Flowable<AuthResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<AuthResponse> signupUser(@Body SignupRequestMany signupRequestMany);

    @POST("signup/email")
    Call<AuthResponse> validateEmail(@Body EValidationRequest eValidationRequest);

    @POST("signup/username")
    Call<AuthResponse> validateUsername(@Body UValidationRequest uValidationRequest);

    @GET("users/{id}")
    Flowable<User> getUserById(@Path ("id") int id);

    @GET("users/{username}")
    Flowable<User> getUserByUsername(@Path ("username") String username);


    //@PATCH("users/{id}")
    //Call<AuthResponse> updateUserDetails(@Body UserModel userModel, @Path ("id") int id);
    //not sure what to send in the body to be honest

















}
