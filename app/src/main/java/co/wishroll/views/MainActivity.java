package co.wishroll.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CircularImageView profileThumbnail;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileThumbnail = findViewById(R.id.profileMain);




        profileThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "before navigating to the profile activity");
                startActivity(new Intent(MainActivity.this, ProfileViewActivity.class));
            }
        });
    }







}