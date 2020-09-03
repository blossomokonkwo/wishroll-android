package co.wishroll;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;




public class WishRollApplication extends Application {
    //class that starts when application starts
    //retrieves whether or not the network is connected
    //holds ApplicationGraph that Dagger uses to provide dependencies

    private static Context mContext;
    private static WishRollApplication instance;
    public static ApplicationGraph applicationGraph = DaggerApplicationGraph.create();


    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }



    public static WishRollApplication getInstance(){ return instance; }

    public static boolean hasNetwork(){ return instance.isNetworkConnected();}

    private boolean isNetworkConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }



}
