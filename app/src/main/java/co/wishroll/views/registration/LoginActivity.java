package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.w3c.dom.Text;

import co.wishroll.R;
import co.wishroll.databinding.ActivityLoginBinding;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.AuthResource;
import co.wishroll.utilities.ToastUtils;
import co.wishroll.viewmodel.registration.LoginViewModel;
import co.wishroll.views.home.MainActivity;

import static co.wishroll.WishRollApplication.applicationGraph;


public class LoginActivity extends AppCompatActivity implements AuthListener {

    ActivityLoginBinding activityLoginBinding;
    LoginViewModel loginViewModel;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private ProgressBar progressBar;
    private ImageButton backImageButton;
    private TextView loginCredentialsWarning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityLoginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);


        activityLoginBinding.setLoginviewmodel(loginViewModel);
        loginViewModel.authListener = this;


        progressBar = activityLoginBinding.progressBarLogin;
        backImageButton = activityLoginBinding.imageButton;
        loginCredentialsWarning = activityLoginBinding.wrongLoginWarning;

        //signupInstead = findViewById(R.id.newSignUp);










        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                LoginActivity.this.startActivity(intent);
                //LoginActivity.this.overridePendingTransition(0, 0);

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
                            onStarted();
                            break;
                        }
                        case ERROR:{
                            //ToastUtils.showToast(LoginActivity.this, "Incorrect Email or Password");
                            showProgressBar(false);
                            showLoginWarning(true);
                            break;

                        }

                        case AUTHENTICATED:{
                            if(welcomeUser(response.getUserModel(), response.getAccessToken())) {
                                showProgressBar(false);
                                showLoginWarning(false);
                                //statusGetter(200);
                                onSuccess();
                                sessionManagement.printEverything("in the authenticated clause of the loginActivity");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            showLoginWarning(true);
                            showProgressBar(false);
                            break;

                        }



                    }
                }
            }
        });


    }

    public boolean welcomeUser(UserModel userModel, AccessToken accessToken) {
        return sessionManagement.saveSession(userModel, accessToken);

    }

    private void showProgressBar(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showLoginWarning(boolean isVisible){
        if(isVisible){
            loginCredentialsWarning.setVisibility(View.VISIBLE);
        }else{
            loginCredentialsWarning.setVisibility(View.GONE);
        }
    }



   @Override
   public void onStarted() {
        progressBar.setVisibility(View.VISIBLE);
       loginCredentialsWarning.setVisibility(View.INVISIBLE);


   }

    @Override
    public void onSuccess() {
        loginCredentialsWarning.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onFailure(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        //ToastUtils.showToast(this, message);
        loginCredentialsWarning.setText(message);
        loginCredentialsWarning.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendMessage(String message){
       ToastUtils.showToast(this, message);
   }

   @Override
    public void errorMessage(String message){
       ToastUtils.showToast(this, message);
   }

    @Override
    public void statusGetter(int statusCode) {

        switch(statusCode){

            case 100:
                //ToastUtils.showToast(LoginActivity.this, "Please try again.");

            break;

            case 200:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            break;

            case 404:
                ToastUtils.showToast(LoginActivity.this, "Not Found");
                break;

            case 401:
                ToastUtils.showToast(LoginActivity.this, "You entered the wrong credentials, please try again");
                break;

            case 400:
                ToastUtils.showToast(LoginActivity.this, "Could not be fulfilled, please try again");
                break;

            default:
                progressBar.setVisibility(View.VISIBLE);
                break;

        }

    }
}
