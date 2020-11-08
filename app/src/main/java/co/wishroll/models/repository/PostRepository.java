package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.domainmodels.TrendingTag;
import co.wishroll.models.domainmodels.UserNotification;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class PostRepository {

    private static final String TAG = "PostRepository";
    public WishRollApi wishRollApi;
    Completable uploadStatus;
    Completable status;
    private enum ContentType {
        VIDEO, IMAGES, GIF
    };
    LiveData<StateData<ArrayList<Post>>> uploadsSource;
    LiveData<StateData<ArrayList<Post>>> likedSource;

    SessionManagement sessionManagement = applicationGraph.sessionManagement();


    @Inject
    public PostRepository() {

        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }

    public void trackView(HashMap<String, Object> data){
        wishRollApi.trackView(data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                Log.d(TAG, "onResponse: tracked the view of this item");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: this failed");
            }
        });
    }

    public void trackShare(int postId, String sharedService){
        wishRollApi.trackShare(postId, sharedService).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                Log.d(TAG, "onResponse: successfully logged this items share");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: did not successfully log this items share");
            }
        });
    }



    public Completable uploadPost(MultipartBody.Part post, MultipartBody.Part videoThumbnail, String[] tags, boolean isVideo){

       if(isVideo) {
           uploadStatus = wishRollApi.uploadVideo(post, videoThumbnail)
                   .flatMapCompletable(postResponse -> {
                       return wishRollApi.sendTags(postResponse.getPostId(), tags);
                   })
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribeOn(Schedulers.io());


       }else {
            uploadStatus = wishRollApi.uploadPost(post)
                    .flatMapCompletable(postResponse -> {
                        return wishRollApi.sendTags(postResponse.getPostId(), tags);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io());

       }


        //return if its a success or failure, observe the success or failure in view

        return uploadStatus;

    }

    public LiveData<StateData<ArrayList<Post>>> getMoreLikeThis(int offset, int postId) {

        final LiveData<StateData<ArrayList<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getMoreLikeThis(postId, offset)
                        .onErrorReturn(new Function<Throwable, Post[]>() {
                            @Override
                            public Post[] apply(Throwable throwable) throws Exception {
                                return null;
                            }
                        })
                        .map(new Function<Post[], StateData<ArrayList<Post>>>() {
                            @Override
                            public StateData<ArrayList<Post>> apply(Post[] moreLikeThis) throws Exception {
                                ArrayList<Post> moreLikeThisArrayList = new ArrayList<>();
                                if (moreLikeThis == null) {
                                    return StateData.error("Something went wrong, please try again", moreLikeThisArrayList);
                                } else {
                                    moreLikeThisArrayList = new ArrayList<>(Arrays.asList(moreLikeThis));
                                    return StateData.authenticated(moreLikeThisArrayList);
                                }

                            }
                        })
                        .subscribeOn(Schedulers.io()));




        return source;
    }

    public LiveData<StateData<Post>> getPost(int postId){

        LiveData<StateData<Post>>source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getPost(postId)
                        .onErrorReturn(new Function<Throwable, Post>() {
                            @Override
                            public Post apply(Throwable throwable) throws Exception {
                                return null;
                            }
                        })
                .map(new Function<Post, StateData<Post>>() {
                    @Override
                    public StateData<Post> apply(Post post) throws Exception {
                        if(post == null){
                            return StateData.error("Something went wrong", null);
                        }else{
                            return StateData.authenticated(post);
                        }

                    }
                })
                .subscribeOn(Schedulers.io()));
        return source;
    }

    public  LiveData<StateData<ArrayList<Post>>> getTaggedPosts(int trendingTagId, int offset){
        final LiveData<StateData<ArrayList<Post>>> source = LiveDataReactiveStreams.fromPublisher(

                wishRollApi.getSeeMoreTrendingTag(trendingTagId, offset)
                        .onErrorReturn(new Function<Throwable, TrendingTag>() {
                            @Override
                            public TrendingTag apply(Throwable throwable) throws Exception {
                                return null;
                            }
                        }).map(new Function<TrendingTag, StateData<ArrayList<Post>>>() {
                    @Override
                    public StateData<ArrayList<Post>> apply(TrendingTag trendingTag) throws Exception {
                        ArrayList<Post> trendingTagPosts = new ArrayList<>();

                        if(trendingTag == null){
                            return StateData.error("Something went wrong", trendingTagPosts);
                        }else{
                            trendingTagPosts.addAll(Arrays.asList(trendingTag.getTrendingTagThumbnails()));
                            return StateData.authenticated(trendingTagPosts);
                        }

                    }
                }).subscribeOn(Schedulers.io()));

        return source;
    }

    public  LiveData<StateData<ArrayList<Post>>> getVideosListPosts(int postId, int offset){
        final LiveData<StateData<ArrayList<Post>>> source = LiveDataReactiveStreams.fromPublisher(

                wishRollApi.getVideoList(postId, offset, "video")
                            .onErrorReturn(new Function<Throwable, Post[]>() {
                                @Override
                                public Post[] apply(Throwable throwable) throws Exception {
                                    return null;
                                }
                            })
                            .map(new Function<Post[], StateData<ArrayList<Post>>>() {
                                @Override
                                public StateData<ArrayList<Post>> apply(Post[] posts) throws Exception {
                                    ArrayList<Post> videosList = new ArrayList<>();

                                    if(posts == null){
                                        return StateData.error("Something went wrong, please try again", videosList);
                                    }else{
                                        videosList.addAll(Arrays.asList(posts));
                                        return StateData.authenticated(videosList);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));

        return source;

    }




    public LiveData<StateData<ArrayList<Post>>> getUploadedPosts(int userId, int offset){

            uploadsSource = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.getUploads(userId, offset)
                            .onErrorReturn(new Function<Throwable, Post[]>() {
                                @Override
                                public Post[] apply(Throwable throwable) throws Exception {
                                    return null;
                                }
                            })
                            .map(new Function<Post[], StateData<ArrayList<Post>>>() {
                                @Override
                                public StateData<ArrayList<Post>> apply(Post[] posts) throws Exception {
                                    ArrayList<Post> uploadsList = new ArrayList<>();

                                    if(posts == null){
                                        return StateData.error("Something went wrong, please try again", uploadsList);
                                    }else{
                                        uploadsList.addAll(Arrays.asList(posts));
                                        return StateData.authenticated(uploadsList);
                                    }

                                }
                            }).subscribeOn(Schedulers.io()));




        return uploadsSource;


    }



    public LiveData<StateData<ArrayList<Post>>> getLikedPosts(int userId, int offset){


            likedSource = LiveDataReactiveStreams.fromPublisher(
                    wishRollApi.getLikes(userId, offset)
                            .onErrorReturn(new Function<Throwable, Post[]>() {
                                @Override
                                public Post[] apply(Throwable throwable) throws Exception {
                                    return null;
                                }
                            })
                            .map(new Function<Post[], StateData<ArrayList<Post>>>() {
                                @Override
                                public StateData<ArrayList<Post>> apply(Post[] posts) throws Exception {
                                    ArrayList<Post> likedList = new ArrayList<>();

                                    if(posts == null){
                                        return StateData.error("Something went wrong, please try again", likedList);
                                    }else{
                                        likedList.addAll(Arrays.asList(posts));
                                        return StateData.authenticated(likedList);
                                    }

                                }
                            }).subscribeOn(Schedulers.io())
            );




        return likedSource;
    }



    public LiveData<StateData<ArrayList<Post>>> getBookmarkedPosts(int userId, int offset){
        return LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getBookmarks(userId, offset)
                .onErrorReturn(new Function<Throwable, Post[]>() {
                    @Override
                    public Post[] apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<Post[], StateData<ArrayList<Post>>>() {
                    @Override
                    public StateData<ArrayList<Post>> apply(Post[] posts) throws Exception {
                        ArrayList<Post> bookmarkedList = new ArrayList<>();

                        if(posts == null){
                            return StateData.error("Something went wrong, please try again", bookmarkedList);
                        }else{
                            bookmarkedList.addAll(Arrays.asList(posts));
                            return StateData.authenticated(bookmarkedList);
                        }

                    }
                }).subscribeOn(Schedulers.io())
        );
    }

    public LiveData<StateData<ArrayList<UserNotification>>> getUserNotifications(int offset){
        return LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getNotifications(offset)
                .onErrorReturn(new Function<Throwable, UserNotification[]>() {
                    @Override
                    public UserNotification[] apply(Throwable throwable) throws Exception {
                        return null;
                    }
                })
                .map(new Function<UserNotification[], StateData<ArrayList<UserNotification>>>() {
                    @Override
                    public StateData<ArrayList<UserNotification>> apply(UserNotification[] userNotifications) throws Exception {
                        ArrayList<UserNotification> userNotificationList = new ArrayList<>();

                        if(userNotifications == null){
                            return StateData.error("Something went wrong, please try again", userNotificationList);
                        }else{
                            userNotificationList.addAll(Arrays.asList(userNotifications));
                            return StateData.authenticated(userNotificationList);
                        }
                    }
                }).subscribeOn(Schedulers.io())
        );
    }
    }
