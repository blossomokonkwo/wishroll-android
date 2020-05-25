package co.WishRoll.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import co.WishRoll.R;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private Context mContext = HomeActivity.this;
    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);

        Log.d(TAG, "onCreate: starting");

    //Homepage and Feed pulled up when sliding onSlideListener
        //DO NOT PAY ATTENTION TO THIS ACTIVITY IT WILL MOST LIKELY BECOME A FRAGMENT
    }


}
