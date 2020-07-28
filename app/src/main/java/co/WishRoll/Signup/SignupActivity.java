package co.WishRoll.Signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import co.WishRoll.MainActivity;
import co.WishRoll.R;
import co.WishRoll.RegisterRequest;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText etEmail = findViewById(R.id.userEmail);
        final EditText etFullName = findViewById(R.id.userFullName);
        final EditText etAge = findViewById(R.id.userAge);
        final EditText etUsername = findViewById(R.id.userUsername);
        final EditText etPasswordOne = findViewById(R.id.userPasswordOne);
        final EditText etPasswordTwo = findViewById(R.id.userPasswordTwo);
        final ProgressBar progressBar =  findViewById(R.id.progressBar);

        final FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

       /* if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(SignupActivity.this, MainActivity.class));
            finish();
        }*/












        final Button bSignup = findViewById(R.id.bCreateAccount);
        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etEmail.getText().toString().trim();
                final String name = etFullName.getText().toString();
                final int age = Integer.parseInt(etAge.getText().toString());
                final String username = etUsername.getText().toString().toLowerCase().replace(' ', '_');
                final String password = etPasswordOne.getText().toString();
                final String password0 = etPasswordTwo.getText().toString();






                if(!password.equals(password0)){
                    Toast.makeText(SignupActivity.this, "Please enter the correct password", Toast.LENGTH_LONG).show();
                }else if(!emailIsVerified(email)){
                    Toast.makeText(SignupActivity.this, "Please enter a valid email", Toast.LENGTH_LONG).show();
                }else if(!usernameIsValid(username)){
                    Toast.makeText(SignupActivity.this, "Please enter a valid username", Toast.LENGTH_LONG).show();
                }else if(age < 12 ) {
                    Toast.makeText(SignupActivity.this, "You need to be 12 or older to use WishRoll", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)){
                    Toast.makeText(SignupActivity.this, "You missed a spot", Toast.LENGTH_LONG).show();
                }else{
                    //send values to the view model
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            }else{
                                Toast.makeText(SignupActivity.this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }






            }
        });

    }

    public static boolean emailIsVerified(String emailInput){


        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }

    public boolean usernameIsValid(String usernameInput){


        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";
        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = usernamePat.matcher(usernameInput);

        return matcher.find();

    }
}