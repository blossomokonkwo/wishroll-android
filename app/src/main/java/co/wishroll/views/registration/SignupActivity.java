package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.wishroll.R;
import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.EValidationRequest;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.utilities.AuthListener;
import io.reactivex.Completable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignupActivity extends AppCompatActivity implements AuthListener {

    private static final String TAG = "SIGNUP ACTIVITY";





    ProgressBar progressBarEmail;
    Button bNextEmail;
    ImageButton backEmail;
    EditText signupEmailET;
    Completable emailValid;
    Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
    WishRollApi wishRollApi = retrofitInstance.create(WishRollApi.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupEmailET = findViewById(R.id.userEmail);

        bNextEmail = findViewById(R.id.bNextEmail);

        progressBarEmail = findViewById(R.id.progressBarEmail);


        backEmail = findViewById(R.id.backEmail);

        backEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });






        bNextEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onNextEmail: in the on next email entering for the first time");
                if (TextUtils.isEmpty(signupEmailET.getText().toString()) || !emailIsVerified(signupEmailET.getText().toString())) {
                    onFailure("Please enter a valid email");
                    Log.d(TAG, "onNextEmail: this person did not enter value");

                }else{
                    showEmailProgressBar(true);
                    EValidationRequest eValidationRequest = new EValidationRequest(signupEmailET.getText().toString());
                    wishRollApi.validateEmail(eValidationRequest).enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            if(response.code() ==200){
                                SignupRequest.setEmail(signupEmailET.getText().toString());
                                showEmailProgressBar(false);
                                statusGetter(200);
                            }else if(response.code() == 400){
                                showEmailProgressBar(false);
                                statusGetter(400);
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












    }





    public boolean emailIsVerified(String emailInput) {
        //checks if email entry is in correct email form  easy regex: ^(.+)@(.+)$  "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailInput);
        return matcher.find();
    }

    private void showEmailProgressBar(boolean isVisible){
        if(isVisible){
            progressBarEmail.setVisibility(View.VISIBLE);
        }else{
            progressBarEmail.setVisibility(View.GONE);
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
            if(statusCode == 400) {
                Log.d(TAG, "statusGetter: showing the taken message");
                Toast.makeText(this, "This email is linked with another account", Toast.LENGTH_SHORT).show();
            }else{
                Log.d(TAG, "statusGetter: on our way to the next activity");
                startActivity(new Intent(SignupActivity.this, NameSignupActivity.class));
            }
    }
}