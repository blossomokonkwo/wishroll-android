package co.wishroll.models.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Singleton;

import co.wishroll.models.repository.local.SessionManagement;
import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static co.wishroll.WishRollApplication.applicationGraph;

@Module
public class RetrofitInstance {

    private static final String TAG = "RetrofitInstance";
    //houses Retrofit Instance "http://10.0.2.2:3000/v2/";

    private static String API_BASE_URL = "http://10.0.2.2:3000/v2/";
    private static Retrofit retrofitInstance;
    private static Retrofit innerRetrofitInstance;
    public static SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static Gson gson;






 //ADD HEADERS TO EACH INNER REQUEST

    @Singleton
    @Provides
    public static Retrofit getRetrofitInstance() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        if (isUserLoggedIn()) {
                            ongoing.addHeader("Authorization", sessionManagement.getToken());
                        }
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();

        if(retrofitInstance == null){

            gson = new GsonBuilder().setLenient().create();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();

            return retrofitInstance;

        }

        return retrofitInstance;
    }



    /*public static WishRollApi wishRollApi = RetrofitInstance.getRetrofitInstance().create(WishRollApi.class);

    public static WishRollApi getWishRollApi() {
        return wishRollApi;
    }*/

    public static boolean isUserLoggedIn(){
        if(sessionManagement.getCurrentUserId() != 0 && sessionManagement.getToken() != null){
            Log.d(TAG, "isUserLoggedIn: the user id for this person is: " + sessionManagement.getCurrentUserId());
            return true;
        }else{
            return false;
        }
    }
}
