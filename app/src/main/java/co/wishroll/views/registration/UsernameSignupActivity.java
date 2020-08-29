package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.R;
import co.wishroll.databinding.ActivityUsernamesignupBinding;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.AuthResource;
import co.wishroll.viewmodel.SignupViewModel;
import co.wishroll.views.home.MainActivity;


public class UsernameSignupActivity extends AppCompatActivity implements AuthListener {

    private static final String TAG = "SIGNUP ACTIVITY";
    ActivityUsernamesignupBinding usernamesignupBinding;
    SignupViewModel signupViewModel;


    ProgressBar progressBarSignup;
    ImageButton backUsername;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_signup);

        usernamesignupBinding = DataBindingUtil.setContentView(UsernameSignupActivity.this, R.layout.activity_usernamesignup);
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        usernamesignupBinding.setSignupviewmodel(signupViewModel);
        signupViewModel.authListenerSign = this;

        backUsername = findViewById(R.id.backUsername);

        progressBarSignup = findViewById(R.id.progressBarSignup);

        backUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UsernameSignupActivity.this, AgeSignupActivity.class));
                finish();
            }
        });



        subscribeSignupObservers();









    }


    private void subscribeSignupObservers(){
        Log.d(TAG, "subscribeSignupObservers: in here going down and under");
        signupViewModel.observeSignupUser().observe(this, new Observer<AuthResource<AuthResponse>>() {
            @Override
            public void onChanged(AuthResource<AuthResponse> authResponseAuthResource) {
                if(authResponseAuthResource != null){

                    AuthResponse response = authResponseAuthResource.data;
                    switch (authResponseAuthResource.status){
                        case LOADING: {
                            showSignupProgressBar(true);
                            break;
                        }
                        case ERROR:{
                            Log.d(TAG, "onChanged: somethings else went wrong, might have been network error, idk cuz username is checked");
                            Toast.makeText(UsernameSignupActivity.this, "Please enter the correct credentials", Toast.LENGTH_SHORT).show();
                            showSignupProgressBar(false);
                            break;

                        }

                        case AUTHENTICATED:{
                            showSignupProgressBar(false);
                            Log.d(TAG, "onChanged: if you see this, nesting them worked!! lol");
                            statusGetter(200);
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            Toast.makeText(UsernameSignupActivity.this, "Please enter the correct credentials ", Toast.LENGTH_SHORT).show();
                            showSignupProgressBar(false);
                            break;

                        }



                    }
                }
            }
        });


    }


    private void showSignupProgressBar(boolean isVisible){
        if(isVisible){
            progressBarSignup.setVisibility(View.VISIBLE);
        }else{
            progressBarSignup.setVisibility(View.GONE);
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
                Intent intent = new Intent(UsernameSignupActivity.this, MainActivity.class);
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