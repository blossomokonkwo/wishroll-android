package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import co.WishRoll.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText etEmail = findViewById(R.id.userEmail);
        final EditText etFullName = findViewById(R.id.userFullName);
        final EditText etUsername = findViewById(R.id.userUsername);
        final EditText etPasswordOne = findViewById(R.id.userPasswordOne);
        final EditText etPasswordTwo = findViewById(R.id.userPasswordTwo);

        final Button bSignup = findViewById(R.id.bCreateAccount);

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = 
            }
        });

    }
}