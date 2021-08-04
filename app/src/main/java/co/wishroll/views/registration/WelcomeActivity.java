package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.BuildConfig;

import javax.inject.Inject;

import co.wishroll.R;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.views.home.MainActivity;

import static co.wishroll.WishRollApplication.applicationGraph;


public class WelcomeActivity extends AppCompatActivity  {

    @Inject
    SessionManagement sessionManagement = applicationGraph.sessionManagement();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button signup = findViewById(R.id.welcomeSignupB);
        TextView login = findViewById(R.id.welcomeLoginB);

        signup.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             startActivity(new Intent(WelcomeActivity.this, EmailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
         });


         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(WelcomeActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));


             }
         });


        if (loggedIn(sessionManagement.getCurrentUserId())) {
            if (true) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            } else {

            }


        }
    }




        public boolean loggedIn ( int id){
        return false;
        //defaulted to false for testing, meaning not signed in, goes to log in and/or sign up page
            //return id != 0;

        }


}
