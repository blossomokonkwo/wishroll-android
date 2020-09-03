package co.wishroll.models.networking;

import java.util.List;

import co.wishroll.models.domainmodels.Notification;
import co.wishroll.models.domainmodels.Post;
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
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WishRollApi {
    //all wishroll api methods and calls



    //Authentication and Signup
    @POST("login")
    Flowable<AuthResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("signup")
    Call<AuthResponse> signupUser(@Body SignupRequestMany signupRequestMany);

    @POST("signup/email")
    Call<AuthResponse> validateEmail(@Body EValidationRequest eValidationRequest);

    @POST("signup/username")
    Call<AuthResponse> validateUsername(@Body UValidationRequest uValidationRequest);





    //User Profiles

    @GET("users/{id}")
    Flowable<User> getUserById(@Path ("id") int id);

    @GET("users/{username}")
    Flowable<User> getUserByUsername(@Path ("username") String username);


    @PATCH("users/{id}")
    Flowable<User> updateUserDetails();  //(@Body UserModel userModel, @Path ("id") int id);
    //not sure what to send in the body to be honest

    @GET("")
    Flowable<List<User>> getListFollowers();
    //gets list of user's followers

    @GET("")
    Flowable<List<User>> getListFollowing();
    //gets list of users who user followed

    @GET("")
    Flowable<List<Post>> getUploadedPosts();
    //gets list of uploads by user

    @GET("")
    Flowable<List<Post>> getLikedPosts();
    //gets list of posts liked by user

    @GET("")
    Flowable<List<Post>> getBookmarkedPost();
    //gets list of posts bookmarked by user

    @GET("")
    Flowable<List<Notification>> getNotifications();

    


















}
