package co.wishroll.models.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import co.wishroll.WishRollApplication;
import co.wishroll.models.repository.local.SessionManagement;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
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
    public static SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private static Gson gson;

    private static final long cacheSize = 175 * 1024 * 1024; // 5 MB
    private static String HEADER_CACHE_CONTROL = "Cache-Control";
    private static String HEADER_PRAGMA = "Pragma";




    @Singleton
    @Provides
    public static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);



        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache())
                .addNetworkInterceptor(networkInterceptor())
                .addInterceptor(offlineInterceptor())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing = chain.request().newBuilder();
                        if (isUserLoggedIn()) {

                            ongoing.addHeader("Authorization", sessionManagement.getToken());
                            ongoing.addHeader("Content-Type", "application/json");
                            ongoing.addHeader("Accept", "application/json");
                        }
                        return chain.proceed(ongoing.build());
                    }
                })
                .build();





        if(retrofitInstance == null){
            gson = new GsonBuilder().create();
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

    private static Cache cache(){
        return new Cache(new File(WishRollApplication.getInstance().getCacheDir(), "wishrollCache"), cacheSize);
    }

    public static boolean isUserLoggedIn(){
        if(sessionManagement.getCurrentUserId() != 0 && sessionManagement.getToken() != null){
            Log.d(TAG, "isUserLoggedIn: the user id for this person is: " + sessionManagement.getCurrentUserId());
            return true;
        }else{
            return false;
        }
    }

    private static Interceptor offlineInterceptor(){
        return new Interceptor() {
            
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "intercept: offline interceptor called.");

                Request request = chain.request();

                if(!WishRollApplication.hasNetwork()){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(1, TimeUnit.MINUTES) //time before request information is invalid in low internet spaces
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                            .build();

                }


                return chain.proceed(request);
            }
        };
    }

   /* public static WishRollApi wishRollApi = RetrofitInstance.getRetrofitInstance().create(WishRollApi.class);

    public WishRollApi getWishRollApi() {
        return wishRollApi;
    }*/
    private static Interceptor networkInterceptor(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Log.d(TAG, "intercept: network interceptor called");

                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(10, TimeUnit.SECONDS) //time before a new request is made
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }
}
