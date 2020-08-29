package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.R;
import co.wishroll.databinding.ActivityLoginBinding;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.AuthResource;
import co.wishroll.viewmodel.LoginViewModel;
import co.wishroll.views.home.MainActivity;


public class LoginActivity extends AppCompatActivity implements AuthListener {


    private static final String TAG = "LOGIN ACTIVITY";
    ActivityLoginBinding activityLoginBinding;
    LoginViewModel loginViewModel;

    TextView signupInstead;
    ProgressBar progressBar;
    EditText emailUser, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        activityLoginBinding.setLoginviewmodel(loginViewModel);
        loginViewModel.authListener = this;
        progressBar = findViewById(R.id.progressBarLogin);
        signupInstead = findViewById(R.id.newSignUp);










        signupInstead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

        subscribeObservers();





    }

    private void subscribeObservers(){
        loginViewModel.observeSignupUser().observe(this, new Observer<AuthResource<AuthResponse>>() {
            @Override
            public void onChanged(AuthResource<AuthResponse> authResponseAuthResource) {
                if(authResponseAuthResource != null){

                    AuthResponse response = authResponseAuthResource.data;
                    switch (authResponseAuthResource.status){
                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case ERROR:{
                            Toast.makeText(LoginActivity.this, "Please enter the correct credentials", Toast.LENGTH_SHORT).show();
                            showProgressBar(false);
                            break;

                        }

                        case AUTHENTICATED:{
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: login successful");
                            statusGetter(200);
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            Toast.makeText(LoginActivity.this, "Please enter the correct credentials sis", Toast.LENGTH_SHORT).show();
                            showProgressBar(false);
                            break;

                        }



                    }
                }
            }
        });


    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }



   @Override
   public void onStarted() {
        progressBar.setVisibility(View.VISIBLE);
        //Toast.makeText(this, "Login Started", Toast.LENGTH_SHORT).show();


}

    @Override
    public void onSuccess() {

        progressBar.setVisibility(View.INVISIBLE);
        //Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(String message) {
        progressBar.setVisibility(View.INVISIBLE);
       Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void sendMessage(String message){
       Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
   }

   @Override
    public void errorMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
   }

    @Override
    public void statusGetter(int statusCode) {

        switch(statusCode){

            case 100:
            Toast.makeText(this, "Please try again.", Toast.LENGTH_SHORT).show();
            break;

            case 200:
                Intent intent = new Intent(this, MainActivity.class);
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
                Toast.makeText(this, "Could not be fulfilled, please try again", Toast.LENGTH_SHORT).show();
                break;

            default:
                progressBar.setVisibility(View.VISIBLE);
                break;

        }

    }
}
