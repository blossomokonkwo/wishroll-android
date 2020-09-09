package co.wishroll.models.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

@Singleton
public class PostRepository {


    public WishRollApi wishRollApi;


    @Inject
    public PostRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }

    public int uploadPost(MultipartBody.Part post, RequestBody caption){
        //int returned from upload post request is used in a chain request to upload the tags and sort of accompny the tags with the post. when request is complete you get a 200 ok


    }
}
