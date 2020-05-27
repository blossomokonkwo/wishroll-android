package co.WishRoll.Upload;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import co.WishRoll.R;

public class UploadActivity extends AppCompatActivity {
    //user uploading pictures and videosto their profile
    private static final String TAG = "UploadActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);

        Log.d(TAG, "onCreate: starting");

        
    }


}
