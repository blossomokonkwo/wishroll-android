package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.wishroll.R;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.SignupRequestMany;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthListener;
import co.wishroll.views.home.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;


public class PasswordSignupActivity extends AppCompatActivity implements AuthListener {

    private static final String TAG = "SIGNUP ACTIVITY";
    private Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
    private WishRollApi wishRollApi = retrofitInstance.create(WishRollApi.class);
    private SessionManagement sessionManagement = applicationGraph.sessionManagement();


    private Button signUp;
    private ProgressBar progressBarEnd;
    private Button bBackPassword;
    private EditText etPasswordOne, etPasswordTwo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordsignup);

        bBackPassword = (Button) findViewById(R.id.backButtonPassword);
        progressBarEnd = findViewById(R.id.progressBarSignupLast);
        etPasswordOne = findViewById(R.id.userPasswordOne);
        etPasswordTwo = findViewById(R.id.userPasswordTwo);
        signUp = findViewById(R.id.bCreateAccount);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupProgressBar(true);


                if(TextUtils.isEmpty(etPasswordOne.getText().toString()) || TextUtils.isEmpty(etPasswordTwo.getText().toString())){

                    onFailure("Please enter a password");

                }else if(!etPasswordOne.getText().toString().equals(etPasswordTwo.getText().toString())) {
                    onFailure("Please enter the correct password");

                }else if(etPasswordOne.getText().toString().length() <= 6 ) {
                    sendMessage("Your password must be 8 characters or longer");

                }else {

                    SignupRequest.setPassword(etPasswordOne.getText().toString());

                    Log.d(TAG, "onNextEmail: SETTING asc values: " + SignupRequest.getEmail() + " " + SignupRequest.getName() + " " +
                            SignupRequest.getBirthday() + " " + SignupRequest.getUsername() + " " + SignupRequest.getPassword());

                    SignupRequestMany signupRequestMany = new SignupRequestMany("", SignupRequest.getUsername(), SignupRequest.getPassword(),SignupRequest.getEmail(),SignupRequest.getBirthday() );


                    wishRollApi.signupUser(signupRequestMany).enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                            Log.d(TAG, "onNextEmail: asc values IN THE CALL: " + SignupRequest.getEmail() + " " + SignupRequest.getName() + " " +
                                    SignupRequest.getBirthday() + " " + SignupRequest.getUsername() + " " + SignupRequest.getPassword());

                            if (response.code() == 201) {

                                showSignupProgressBar(false);
                                AuthResponse auth = response.body();
                                assert auth != null;
                                welcomeNewUser(auth.getUserModel(), auth.getAccessToken());
                                Log.d(TAG, "onNextEmail: asc values: " + SignupRequest.getEmail() + " " + SignupRequest.getName() + " " +
                                        SignupRequest.getBirthday() + " " + SignupRequest.getUsername() + " " + SignupRequest.getPassword());
                                statusGetter(200);


                            } else if (response.code() == 400) {
                                showSignupProgressBar(false);
                                Log.d(TAG, "onResponse: something went wrong with the signup process bundle");
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }




            }
        });

        bBackPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PasswordSignupActivity.this, UsernameSignupActivity.class));
                finish();
            }
        });











    }





    public void welcomeNewUser(UserModel userModel, AccessToken accessToken) {
        Log.d(TAG, "welcomeUser: saving user to sharedPreferences");
        sessionManagement.saveSession(userModel, accessToken);

    }


    private void showSignupProgressBar(boolean isVisible){
        if(isVisible){
            progressBarEnd.setVisibility(View.VISIBLE);
        }else{
            progressBarEnd.setVisibility(View.GONE);
        }
    }




    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String message) {
        //progressBarSignup.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void errorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void statusGetter(int statusCode) {

        switch(statusCode){

            case 100:
                Toast.makeText(this, "Please try again.", Toast.LENGTH_SHORT).show();
                break;

            case 200:
                Intent intent = new Intent(PasswordSignupActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case 404:
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
                break;

            case 401:
                Toast.makeText(this, "You entered the wrong credentials, please try again", Toast.LENGTH_SHORT).show();
                break;

            case 400:
                Toast.makeText(this, "The email or username you have entered is taken", Toast.LENGTH_SHORT).show();
                break;

            default:
                //progressBarSignup.setVisibility(View.VISIBLE);
                break;

        }

    }
}