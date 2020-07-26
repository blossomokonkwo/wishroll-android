package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;
import co.WishRoll.Signup.SignupActivity;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         EditText emailUsername = findViewById(R.id.etEmailUsername);
         EditText password = findViewById(R.id.etPasswordEntry);
        Button bLogin = findViewById(R.id.bLogin1);


        TextView tvSignUp = (TextView) findViewById(R.id.newSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSignUp = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(openSignUp);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

    }






}