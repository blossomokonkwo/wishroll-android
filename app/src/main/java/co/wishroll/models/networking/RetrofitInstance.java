package co.wishroll.models.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitInstance {
    //houses Retrofit Instance "http://10.0.2.2:3000/v2/";

    private static String API_BASE_URL = "http://10.0.2.2:3000/v2/";
    private static Retrofit retrofitInstance;
    private static Gson gson;



    @Singleton
    @Provides
    public static Retrofit getRetrofitInstance() {

        if(retrofitInstance == null){

            gson = new GsonBuilder().setLenient().create();

            retrofitInstance = new Retrofit.Builder().baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofitInstance;

        }

        return retrofitInstance;
    }

    /*public static WishRollApi wishRollApi = RetrofitInstance.getRetrofitInstance().create(WishRollApi.class);

    public WishRollApi getWishRollApi() {
        return wishRollApi;
    }*/
}
