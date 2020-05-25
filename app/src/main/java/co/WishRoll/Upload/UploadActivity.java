package co.WishRoll.Upload;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import co.WishRoll.R;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "UploadActivity";
    private Context mContext = UploadActivity.this;
    private static final int ACTIVITY_NUM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);

        Log.d(TAG, "onCreate: starting");

        
    }


}
