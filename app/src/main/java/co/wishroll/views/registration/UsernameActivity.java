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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.wishroll.R;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.UValidationRequest;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UsernameActivity extends AppCompatActivity implements AuthListener {

    private static final String TAG = "UsernameActivity";
    Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
    WishRollApi wishRollApi = retrofitInstance.create(WishRollApi.class);



    Button nextUsername;
    ProgressBar progressBarSignup;
    Button bBackUsername;
    EditText etUsername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        bBackUsername = (Button) findViewById(R.id.backButtonUsername);
        progressBarSignup = findViewById(R.id.progressBarSignup);
        etUsername = findViewById(R.id.userUsername);

        nextUsername = findViewById(R.id.bUsernameNext);



        nextUsername.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: username next was clicked");
                showSignupProgressBar(true);


                if(TextUtils.isEmpty(etUsername.getText().toString())) {
                    onFailure("Please enter a username");

                }else if (!usernameIsValid(etUsername.getText().toString())) {
                    onFailure("Please enter a valid username");

                }else {


                    UValidationRequest uValidationRequest = new UValidationRequest(formatUsername(etUsername.getText().toString()));

                    wishRollApi.validateUsername(uValidationRequest).enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            if(response.code() == 200){
                                SignupRequest.setUsername(formatUsername(etUsername.getText().toString()));

                                Log.d(TAG, "onNextEmail: asc values: " + SignupRequest.getUsername());
                                statusGetter(200);

                            }else{
                                showSignupProgressBar(false);


                                ToastUtils.showToast(UsernameActivity.this, "This username is taken");
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

        bBackUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsernameActivity.this, WelcomeActivity.class));

                finish();
            }
        });











    }





    public static String formatUsername(String username) {
        return username.toLowerCase().replace(' ', '_');
    }

    private void showSignupProgressBar(boolean isVisible){
        if(isVisible){
            progressBarSignup.setVisibility(View.VISIBLE);
        }else{
            progressBarSignup.setVisibility(View.GONE);
        }
    }

    public boolean usernameIsValid(String usernameInput) {
        //Username Validation: no ... or ___ , < 20 char
        String usernameRegex = "^[a-z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";
        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = usernamePat.matcher(usernameInput);
        return matcher.find();

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
        ToastUtils.showToast(this, message);

    }

    @Override
    public void sendMessage(String message) {
        ToastUtils.showToast(this, message);

    }

    @Override
    public void errorMessage(String message) {
        ToastUtils.showToast(this, message);

    }

    @Override
    public void statusGetter(int statusCode) {

        switch(statusCode){

            case 100:
                Toast.makeText(this, "Please try again.", Toast.LENGTH_SHORT).show();
                break;

            case 200:
                Intent intent = new Intent(UsernameActivity.this, AgeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case 404:
                ToastUtils.showToast(this, "Not Found");
                break;

            case 401:
                Toast.makeText(this, "You entered the wrong credentials, please try again", Toast.LENGTH_SHORT).show();
                break;

            case 400:
                ToastUtils.showToast(this, "The email or username you have entered is taken");

                break;

            default:
                //progressBarSignup.setVisibility(View.VISIBLE);
                break;

        }

    }
}