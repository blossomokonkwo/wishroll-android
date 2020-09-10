package co.wishroll.views.upload;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.wishroll.R;

public class UploadActivity extends AppCompatActivity {
    private static final int MEDIA_PICK_CODE = 1000;
    private static final String TAG = "UploadActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }

}