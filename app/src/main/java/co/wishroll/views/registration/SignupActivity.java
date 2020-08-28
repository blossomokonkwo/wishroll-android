package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.R;
import co.wishroll.databinding.ActivitySignupBinding;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.AuthResource;
import co.wishroll.viewmodel.SignupViewModel;


public class SignupActivity extends AppCompatActivity implements AuthListener {

    private static final String TAG = "SIGNUP ACTIVITY";
    ActivitySignupBinding activitySignupBinding;
    SignupViewModel signupViewModel;




    ProgressBar progressBarSignup;
    Button bSignup;
    ImageButton backEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_signup);

        activitySignupBinding = DataBindingUtil.setContentView(SignupActivity.this, R.layout.activity_signup);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        activitySignupBinding.setSignupviewmodel(signupViewModel);
        signupViewModel.authListenerSign = this;

        bSignup = findViewById(R.id.bCreateAccount);

        progressBarSignup = findViewById(R.id.progressBarSignup);
        backEmail = findViewById(R.id.backEmail);

        backEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });

        subscribeObservers();










    }

    private void subscribeObservers(){
        signupViewModel.observeSignupUser().observe(this, new Observer<AuthResource<AuthResponse>>() {
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
                            Toast.makeText(SignupActivity.this, "Please enter the correct credentials", Toast.LENGTH_SHORT).show();
                            showProgressBar(false);
                            break;

                        }

                        case AUTHENTICATED:{
                            showProgressBar(false);
                            Log.d(TAG, "onChanged: login successful why is this showing either way???");
                            statusGetter(200);
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            Toast.makeText(SignupActivity.this, "Please enter the correct credentials ", Toast.LENGTH_SHORT).show();
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
            progressBarSignup.setVisibility(View.VISIBLE);
        }else{
            progressBarSignup.setVisibility(View.GONE);
        }
    }






    @Override
    public void onStarted() {
        //progressBarSignup.setVisibility(View.VISIBLE);
        //Toast.makeText(this, "Signup started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        //progressBarSignup.setVisibility(View.INVISIBLE);
        //Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();

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

            if(statusCode == 400) {
                Toast.makeText(this, "This email is linked with another account", Toast.LENGTH_SHORT).show();
            }else{
                startActivity(new Intent(SignupActivity.this, NameSignupActivity.class));

            }


    }
}