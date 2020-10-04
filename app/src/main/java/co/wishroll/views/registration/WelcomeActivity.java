package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import co.wishroll.R;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.views.home.MainActivity;

import static co.wishroll.WishRollApplication.applicationGraph;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "WELCOME ACTIVITY";

    @Inject
    SessionManagement sessionManagement = applicationGraph.sessionManagement();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button signup = findViewById(R.id.bSignUp);
        TextView login = findViewById(R.id.bLogin);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);






        if(loggedIn(sessionManagement.getCurrentUserId())){
            Log.d(TAG, "WelcomeActivity: CURRENT USER ID  " + sessionManagement.getCurrentUserId() );
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }else{

        }








    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.bSignUp:
                startActivity(new Intent(this, SignupActivity.class));
                break;

            case R.id.bLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }



    }

    public boolean loggedIn(int id){
        return id != 0;

    }
}