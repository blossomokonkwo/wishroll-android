package co.wishroll.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //in the future
        //check sharedPreferences if the user is logged in => open home or open login/signup page


            startActivity(new Intent(this, WelcomeActivity.class));
            finish();


    }
}
