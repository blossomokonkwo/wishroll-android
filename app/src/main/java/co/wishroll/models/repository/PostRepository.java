package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.PostResponse;
import io.reactivex.Completable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class PostRepository {

    private static final String TAG = "PostRepository";

    public WishRollApi wishRollApi;
    boolean succeeded = false;
    int postId;
    LiveData<Completable> source;


    @Inject
    public PostRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }

    public boolean uploadPost(MultipartBody.Part post, MultipartBody.Part videoThumbnail, RequestBody caption, String tags, boolean isVideo){

        if(post != null && caption != null){
            wishRollApi.uploadPost(post, caption).enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if(response.isSuccessful()){
                        postId = response.body().getPostId();

                        wishRollApi.sendTags(postId, tags).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.isSuccessful()) {
                                    succeeded = true;
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.d(TAG, "onFailure: this failed ew");
                }
            });













        }else if(post != null && caption == null && tags != null){

            wishRollApi.uploadPost(post).enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if(response.isSuccessful()){
                        try {
                            if(wishRollApi.sendTags(response.body().getPostId(), tags).execute().isSuccessful()){
                                succeeded = true;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.d(TAG, "onFailure: this failed ew");
                }
            });




        }




        return succeeded; //source is a livedata, statedata, completable

    }




}
