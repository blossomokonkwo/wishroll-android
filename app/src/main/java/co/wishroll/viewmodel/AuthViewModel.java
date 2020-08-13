package co.wishroll.viewmodel;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.data.LoginRequest;
import co.wishroll.models.repository.data.LoginResponse;
import co.wishroll.models.repository.data.SignupResponse;
import co.wishroll.utilities.AuthListener;

public class AuthViewModel extends ViewModel {


    private static final String TAG = "AuthViewModel";
    //for logging in and signing up

    UserRepository userRepository = UserRepository.getInstance();
    private MutableLiveData<LoginResponse> mldLoginResponse = new MutableLiveData<>();
    private MutableLiveData<SignupResponse> mldSignupResponse = new MutableLiveData<>();



    public String usermail = "";

    String email;

    String password = "";

    String username;

    Date birth_date;

    String name = null;

    public AuthListener authListener = null;

    public void onLoginButtonPressed(View view){
        authListener.onStarted();

       if(usermail.isEmpty() && password.isEmpty()){ //
            //TODO(display an error message in the activity)
            authListener.onFailure("Please enter your username or email and password");


            return;
        }

      mldLoginResponse =  userRepository.loginUser(new LoginRequest(usermail, password));

       if(mldLoginResponse == null){
           Log.d(TAG, "onLoginButtonPressed: your credentials are wrong");
           authListener.onFailure("Please enter a valid username or email");
           //error message, bro you don't have the right credentials
       }else{
           //successfull login process
       }


        // else, if both are entered, now take it to the REST API.if flow of control comes outside if statement, it is considered a success




    }

    public void onSignupButtonPressed(){

    }


}
