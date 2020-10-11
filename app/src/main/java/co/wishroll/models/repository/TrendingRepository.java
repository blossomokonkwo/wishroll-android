package co.wishroll.models.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.domainmodels.TrendingTag;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.utilities.StateData;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class TrendingRepository {
    private static final String TAG = "TrendingRepository";
    private WishRollApi wishRollApi;

    @Inject
    public TrendingRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);


    }






    public LiveData<StateData<ArrayList<TrendingTag>>> getTrendingTags(int offset) {
        Log.d(TAG, "getTrendingTags: this is the offset going into the api call " + offset);
        final LiveData<StateData<ArrayList<TrendingTag>>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.getTrendingTags(offset)
                            .onErrorReturn(new Function<Throwable, TrendingTag[]>() {
                                @Override
                                public TrendingTag[] apply(Throwable throwable) throws Exception {
                                    return null;
                                }
                            })
                            .map(new Function<TrendingTag[], StateData<ArrayList<TrendingTag>>>() {
                                @Override
                                public StateData<ArrayList<TrendingTag>> apply(TrendingTag[] trendingTags) throws Exception {
                                    ArrayList<TrendingTag> trendingTagArrayList = new ArrayList<>();
                                    if (trendingTags == null) {
                                        return StateData.error("Something went wrong, please try again", trendingTagArrayList);
                                    } else {
                                         trendingTagArrayList = new ArrayList<>(Arrays.asList(trendingTags));
                                        return StateData.authenticated(trendingTagArrayList);
                                    }

                                }
                            }).onBackpressureLatest()
                .subscribeOn(Schedulers.io()));




        return source;
    }




}
