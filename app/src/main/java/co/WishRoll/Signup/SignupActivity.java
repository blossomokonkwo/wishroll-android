package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

import co.WishRoll.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText email = findViewById(R.id.userEmail);
        final EditText fullName = findViewById(R.id.userFullName);
        final EditText username = findViewById(R.id.userUsername);
        final EditText passwordOne = findViewById(R.id.userPasswordOne);
        final EditText passwordTwo = findViewById(R.id.userPasswordTwo);

        final Button bSignup = findViewById(R.id.bCreateAccount);

    }
}