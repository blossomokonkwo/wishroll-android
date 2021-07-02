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


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

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
             startActivity(new Intent(WelcomeActivity.this, EmailActivity.class));
            }
         });


         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));

             }
         });


        if (loggedIn(sessionManagement.getCurrentUserId())) {
            if (true) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            } else {

            }


        }
    }




        public boolean loggedIn ( int id){
        return false;
            //return id != 0;

        }

    @Override
    public void onClick(View v) {

    }
}
