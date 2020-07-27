package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

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



        final Button bSignup = findViewById(R.id.bCreateAccount);

        bSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = etEmail.getText().toString();
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
                }else if(age < 12) {
                    Toast.makeText(SignupActivity.this, "You need to be 12 or older to use WishRoll", Toast.LENGTH_LONG).show();
                }else if(email.isEmpty() || name.isEmpty() || username.isEmpty() || password.isEmpty()){
                    Toast.makeText(SignupActivity.this, "You missed a spot", Toast.LENGTH_LONG).show();
                }else{
                    //send values to the view model
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    Intent enterWishRoll = new Intent(SignupActivity.this, MainActivity.class);
                                    SignupActivity.this.startActivity(enterWishRoll);
                                }else{
                                    Toast.makeText(SignupActivity.this, "Something went wrong, please try again", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(TAG, "onResponse: THIS DIDNT WORK CHECK THE DOMAIN STRING I THINK YOU GOT THAT WRONG SIS");
                            }

                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(email, name, age,username, password, responseListener);
                    RequestQueue registerQueue = Volley.newRequestQueue(SignupActivity.this);
                    registerQueue.add(registerRequest);


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