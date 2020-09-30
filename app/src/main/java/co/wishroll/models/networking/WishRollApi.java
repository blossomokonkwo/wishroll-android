package co.wishroll.models.networking;

import java.util.List;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.EValidationRequest;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.SignupRequestMany;
import co.wishroll.models.repository.datamodels.UValidationRequest;
import co.wishroll.models.repository.datamodels.UploadPostResponse;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WishRollApi {
    //all wishroll api methods and calls
    // v3/ -> discover and content-type searches
    // v2/ -> everything else




    //Authentication and Signup
    @POST("v2/login")
    Flowable<AuthResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("v2/signup")
    Call<AuthResponse> signupUser(@Body SignupRequestMany signupRequestMany);

    @POST("v2/signup/email")
    Call<AuthResponse> validateEmail(@Body EValidationRequest eValidationRequest);

    @POST("v2/signup/username")
    Call<AuthResponse> validateUsername(@Body UValidationRequest uValidationRequest);





    //User Profiles & Current User Profile
    /*@GET("v2/users/{id}")
    Flowable<User> getUserById(@Path ("id") int id);

    @GET("v2/users/{username}")
    Flowable<User> getUserByUsername(@Path ("username") String username);*/

    /*@Multipart
    @PUT("v2/user/update")
    Flowable<UpdateResponse> updateUserDetails(@PartMap Map<String, RequestBody> parameters,
                                               @Part MultipartBody.Part profile,
                                               @Part MultipartBody.Part banner);*/
   /* @Multipart
    @PUT("v2/user/update")
    Flowable<UpdateResponse> updateUserDetails(@PartMap Map<String, RequestBody> parameters,
                                               @Part MultipartBody.Part profile);
    @Multipart
    @PUT("v2/user/update")
    Flowable<UpdateResponse> updateUserDetails(@PartMap Map<String, RequestBody> parameters);

    @Multipart
    @PUT("v2/user/update")
    Flowable<UpdateResponse> updateUserDetails( @Part MultipartBody.Part profile,
                                                @Part MultipartBody.Part banner);
    @Multipart
    @PUT("v2/user/update")
    Flowable<UpdateResponse> updateUserDetails( @Part MultipartBody.Part profile);*/




    //Uploading Posts
    @Multipart
    @POST("v2/posts")
    Single<UploadPostResponse> uploadPost(@Part MultipartBody.Part post, @Part("caption") RequestBody caption);

    @Multipart
    @POST("v2/posts")
    Single<UploadPostResponse> uploadPost(@Part MultipartBody.Part post);

    @Multipart
    @POST("v2/posts")
    Single<UploadPostResponse> uploadVideo(@Part MultipartBody.Part post, @Part MultipartBody.Part videoThumbnail);

    //@Multipart
    //@POST("v2/posts")
    //Single<UploadPostResponse> uploadVideo(@Part MultipartBody.Part post, @Part MultipartBody.Part videoThumbnail, @Part("caption") RequestBody caption );

    @FormUrlEncoded
    @POST("v2/posts/{post_id}/tags")
    Completable sendTags(@Path("post_id") int postID,  @Field("tags[]") String[] tags);


    //Uploads Fragment
    @GET("v2/uploads{offset}")
    Single<List<Post>> getUploadedPosts(@Query("offset") int offset);








}
