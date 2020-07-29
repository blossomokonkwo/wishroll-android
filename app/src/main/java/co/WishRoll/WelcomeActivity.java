package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "WELCOME ACTIVITY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button signup = findViewById(R.id.bSignUp);
        TextView login = findViewById(R.id.bLogin);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            //checks if user us logged in
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
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
}