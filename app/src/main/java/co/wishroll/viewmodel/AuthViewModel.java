package co.wishroll.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import java.util.Date;

import co.wishroll.utilities.AuthListener;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";
    //for logging in and signing up

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
            authListener.onFailure("Invalid email or password.");

            return;
        }

        //if flow of control comes outside if statement, it is considered a success
        authListener.onSuccess();

    }


}
