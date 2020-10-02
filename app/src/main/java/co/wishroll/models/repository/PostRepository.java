package co.wishroll.models.repository;

import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;

@Singleton
public class PostRepository {

    private static final String TAG = "PostRepository";

    public WishRollApi wishRollApi;
    Completable uploadStatus;



    @Inject
    public PostRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }

    public Completable uploadPost(MultipartBody.Part post, MultipartBody.Part videoThumbnail, String[] tags, boolean isVideo){

       if(isVideo) {
           Log.d(TAG, "uploadPost: this is a video with no caption, repository-wise");//single to completable then to livedata but what does a completable return???
           uploadStatus = wishRollApi.uploadVideo(post, videoThumbnail)
                   .flatMapCompletable(postResponse -> {
                       return wishRollApi.sendTags(postResponse.getPostId(), tags);
                   })
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribeOn(Schedulers.io());


       }else {
           Log.d(TAG, "uploadPost: this is an image with no caption, repository-wise");
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




}
