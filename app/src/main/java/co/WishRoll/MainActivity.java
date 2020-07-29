package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CircularImageView profileThumbnail;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileThumbnail = findViewById(R.id.profileMain);
        Button bLogout = findViewById(R.id.bLogout);



        profileThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "before navigating to the profile activity, where the crashes happen");
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }



    public void logout (View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }



}