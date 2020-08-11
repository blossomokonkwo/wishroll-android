package co.wishroll.views.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;

import co.wishroll.R;
import co.wishroll.views.home.MainActivity;
import co.wishroll.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailUsername = findViewById(R.id.etEmailUsername);
        final EditText password = findViewById(R.id.etPasswordEntry);

        Button bLogin = findViewById(R.id.bLogin1);
        ProgressBar progressBarLogin = findViewById(R.id.progressBarLogin);
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
                //authenticates data

                String emailEntry = emailUsername.getText().toString().trim();
                String passwordEntry = password.getText().toString();

                //progressBarLogin.setVisibility(View.VISIBLE);
                // if(task.isSuccessful()){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                //Toast.makeText(LoginActivity.this, "Error" + task.getException(), Toast.LENGTH_LONG).show();


            }
        });



    }

}