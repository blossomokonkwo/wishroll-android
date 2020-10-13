package co.wishroll.models.networking;

import java.util.List;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.domainmodels.TrendingTag;
import co.wishroll.models.domainmodels.User;
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
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("v3/signup")
    Call<AuthResponse> signupUser(@Body SignupRequestMany signupRequestMany);


    @POST("signup/email")
    Call<AuthResponse> validateEmail(@Body EValidationRequest eValidationRequest);

    @POST("signup/username")
    Call<AuthResponse> validateUsername(@Body UValidationRequest uValidationRequest);






    //User Profiles & Current User Profile

    @GET("v2/users/{id}")
    Flowable<User> getUserById(@Path ("id") int id);


    @GET("v2/users/{username}")
    Flowable<User> getUserByUsername(@Path ("username") String username);







    //Uploading Posts
    @Multipart
    @POST("v2/posts")
    Single<UploadPostResponse> uploadPost(@Part MultipartBody.Part post);

    @Multipart
    @POST("v2/posts")
    Single<UploadPostResponse> uploadVideo(@Part MultipartBody.Part post, @Part MultipartBody.Part videoThumbnail);

    @FormUrlEncoded
    @POST("v2/posts/{post_id}/tags")
    Completable sendTags(@Path("post_id") int postID,  @Field("tags[]") String[] tags);






    //Search
    @GET("v3/posts")
    Flowable<Post[]> getSearchResults(@Query("q") String query, @Query("offset") int offset, @Query("content-type") String contentType); //content type = video, image, gif

    @GET("v3/posts")
    Flowable<List<Post>> getSearchResults(@Query("q") int query, @Query("offset") int offset);





    //Trending
    @GET("v3/trending/trending_tags/")
    Flowable<TrendingTag[]> getTrendingTags(@Query("offset") int offset);

    @GET("v3/trending/trending_tags/{id}")
    Flowable<TrendingTag> getSeeMoreTrendingTag(@Path("id") int trendingTagId, @Query("offset") int offset);




    //More Like This and Video Recommendation
    @GET("v3/recommendation/posts")
    Flowable<Post[]> getVideoList(@Query("post_id") int postId, @Query("offset") int offset, @Query("content-type") String contentType);

    @GET("v3/recommendation/posts")
    Flowable<Post[]> getMoreLikeThis( @Query("post_id") int postId, @Query("offset") int offset);




    //Bookmarked Posts
    @POST("v2/posts/{post_id}/bookmarks")
    Completable createBookmark(@Path("post_id") int postId);

    @DELETE("v2/posts/{post_id}/bookmarks")
    Completable deleteBookmark(@Path("post_id") int postId);

    @GET("v2/users/{user_id}/bookmarks")
    Flowable<Post[]> getBookmarkedPosts(@Path("user_id") int userId, @Query("offset") int offset);





    //Posts
    @GET("v2/posts/{post_id}")
    Flowable<Post> getPost(@Path("post_id") int postId);





    //Delete Account
    @DELETE("v2/users")
    Completable deleteThisAccount();





    //Feed
    @GET("v2/feed")
    Flowable<Post[]> getUsersFeed(@Query("offset") int offset);







}
