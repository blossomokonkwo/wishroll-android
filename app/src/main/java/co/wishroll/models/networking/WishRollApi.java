package co.wishroll.models.networking;

import co.wishroll.models.repository.datamodels.EValidationRequest;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.LoginResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.SignupResponse;
import co.wishroll.models.repository.datamodels.UValidationRequest;
import co.wishroll.models.repository.datamodels.ValidationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WishRollApi {
    //all wishroll api methods and calls



    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<SignupResponse> signupUser(@Body SignupRequest signupRequest);


    @POST("signup/username")
    Call<ValidationResponse> validateUsername(@Body UValidationRequest uValidationRequest);


    @POST("signup/email")
    Call<ValidationResponse> validateEmail(@Body EValidationRequest eValidationRequest);












}
