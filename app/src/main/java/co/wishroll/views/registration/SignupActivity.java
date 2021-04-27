package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import co.wishroll.utilities.ToastUtils;
import co.wishroll.views.home.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;


public class SignupActivity extends AppCompatActivity implements AuthListener {

    private final Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
    private final WishRollApi wishRollApi = retrofitInstance.create(WishRollApi.class);
    private final SessionManagement sessionManagement = applicationGraph.sessionManagement();


    private Button signUp;
    private ProgressBar progressBarEnd;
    private Button bBackPassword;
    private EditText etPasswordOne, etPasswordTwo;
    private EditText signupEmailET;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        bBackPassword = findViewById(R.id.backButtonPassword);
        signupEmailET = findViewById(R.id.userEmail);

        progressBarEnd = findViewById(R.id.progressBarSignupLast);
        etPasswordOne = findViewById(R.id.userPasswordOne);
        etPasswordTwo = findViewById(R.id.userPasswordTwo);
        signUp = findViewById(R.id.bCreateAccount);



        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignupProgressBar(true);




                if(TextUtils.isEmpty(etPasswordOne.getText().toString()) || TextUtils.isEmpty(etPasswordTwo.getText().toString())){
                    ToastUtils.showToast(SignupActivity.this, "Please enter the correct password");
                    showSignupProgressBar(false);


                }else if(etPasswordOne.getText().toString().length() <= 7 ) {
                    ToastUtils.showToast(SignupActivity.this, "Your password must be 8 characters or longer");
                    showSignupProgressBar(false);



                }else if(!etPasswordOne.getText().toString().equals(etPasswordTwo.getText().toString())) {
                    ToastUtils.showToast(SignupActivity.this, "Please enter the correct password");
                    showSignupProgressBar(false);



                }else if (TextUtils.isEmpty(signupEmailET.getText().toString()) || !emailIsVerified(signupEmailET.getText().toString())) {
                    ToastUtils.showToast(SignupActivity.this, "Please enter a valid email");
                    showSignupProgressBar(false);


                }else {

                    SignupRequest.setEmail(signupEmailET.getText().toString());
                    SignupRequest.setPassword(etPasswordOne.getText().toString());




                    SignupRequestMany signupRequestMany = new SignupRequestMany(" ", SignupRequest.getUsername(), SignupRequest.getPassword(),
                            SignupRequest.getEmail(), SignupRequest.getBirthday(), SignupRequest.getGender());



                    wishRollApi.signupUser(signupRequestMany).enqueue(new Callback<AuthResponse>() {

                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {


                            AuthResponse auth = response.body();
                            if (response.code() == 201 && auth.getUserModel() != null ) {




                                    if(welcomeNewUser(auth.getUserModel(), auth.getAccessToken())) {
                                        showSignupProgressBar(false);
                                        statusGetter(200);
                                    }



                            } else if (response.code() == 400) {

                                showSignupProgressBar(false);
                                ToastUtils.showToast(SignupActivity.this, "This email is linked with another account");

                            }else{
                                showSignupProgressBar(false);
                                ToastUtils.showToast(SignupActivity.this, "Something went wrong, please try again");

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
                startActivity(new Intent(SignupActivity.this, AgeActivity.class));
                finish();
            }
        });











    }





    public boolean welcomeNewUser(UserModel userModel, AccessToken accessToken) {
         return sessionManagement.saveSession(userModel, accessToken);

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

    public boolean emailIsVerified(String emailInput) {
        //checks if email entry is in correct email form  easy regex: ^(.+)@(.+)$  "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"
        String emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailInput);
        return matcher.find();
        }

    @Override
    public void statusGetter(int statusCode) {

        if(statusCode == 200){

                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);




        }

    }
}