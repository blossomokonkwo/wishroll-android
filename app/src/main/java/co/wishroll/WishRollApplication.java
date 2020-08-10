package co.wishroll;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WishRollApplication extends Application {
    //class that starts when application starts
    //retrieves whether or not the network is connected
    

    private static WishRollApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        if(instance == null){
            instance = this;
        }
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
