package co.WishRoll.Notifications;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import co.WishRoll.R;

public class NotificationActivity extends AppCompatActivity {
    //activity to view notifications that the user gets

    private static final String TAG = "NotificationActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);


        Log.d(TAG, "onCreate: starting");


    }


}
