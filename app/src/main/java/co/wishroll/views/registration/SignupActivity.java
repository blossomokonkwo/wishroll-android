package co.wishroll.views.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import co.wishroll.R;
import co.wishroll.views.home.MainActivity;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SIGNUP ACTIVITY";




    EditText etEmail;
    EditText etFullName;
    EditText etAge;
    EditText etUsername;
    EditText etPasswordOne;
    EditText etPasswordTwo;
    ProgressBar progressBar;
    Button bSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail = findViewById(R.id.userEmail);
        etFullName = findViewById(R.id.userFullName);
        etAge = findViewById(R.id.userAge);
        etUsername = findViewById(R.id.userUsername);
        etPasswordOne = findViewById(R.id.userPasswordOne);
        etPasswordTwo = findViewById(R.id.userPasswordTwo);
        progressBar = findViewById(R.id.progressBar);


        bSignup = findViewById(R.id.bCreateAccount);

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etEmail.getText().toString().trim();
                final String name = etFullName.getText().toString();
                final int age = Integer.parseInt(etAge.getText().toString());
                final String username = formatUsername(etUsername);
                final String password = etPasswordOne.getText().toString();
                final String password0 = etPasswordTwo.getText().toString();


                if (!password.equals(password0)) {

                    Toast.makeText(SignupActivity.this, "Please enter the correct password", Toast.LENGTH_LONG).show();

                } else if (!emailIsVerified(email)) {

                    Toast.makeText(SignupActivity.this, "Please enter a valid email", Toast.LENGTH_LONG).show();

                } else if (!usernameIsValid(username)) {

                    Toast.makeText(SignupActivity.this, "Please enter a valid username", Toast.LENGTH_LONG).show();

                } else if (age < 12) {

                    Toast.makeText(SignupActivity.this, "You need to be 12 or older to use WishRoll", Toast.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {

                    Toast.makeText(SignupActivity.this, "You missed a spot", Toast.LENGTH_LONG).show();

                } else {
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));


                }


            }
        });
    }



            public boolean emailIsVerified(String emailInput) {
                //checks if email entry is in correct email form

                String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

                Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

                Matcher matcher = emailPat.matcher(emailInput);

                return matcher.find();
            }

            public boolean usernameIsValid(String usernameInput) {
                //Username Validation: no triple periods or underscores, no longer than 20 characters, no special characters

                String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";

                Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);

                Matcher matcher = usernamePat.matcher(usernameInput);

                return matcher.find();

            }

            public String formatUsername(EditText username) {

                return username.getText().toString().toLowerCase().replace(' ', '_');
            }


}