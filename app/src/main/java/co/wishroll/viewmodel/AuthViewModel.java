package co.wishroll.viewmodel;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.lifecycle.ViewModel;

import java.util.Date;

public class AuthViewModel extends ViewModel {
    //for logging in and signing up

    String usermail;

    String email;

    String password;

    String username;

    Date birth_date;

    @Nullable
    String name;

    public void onLoginButtonPressed(View view){
        if(usermail.isEmpty() || usermail == null || password.isEmpty() || password == null){
            //TODO(display an error message in the activity)
            return;
        }

        //if flow of control comes outside if statement, it is considered a success


    }


}
