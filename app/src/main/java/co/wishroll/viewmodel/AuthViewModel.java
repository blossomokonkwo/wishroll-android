package co.wishroll.viewmodel;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.wishroll.models.repository.AuthRepository;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.LoginResponse;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.models.repository.datamodels.SignupResponse;
import co.wishroll.utilities.AuthListener;
import co.wishroll.views.registration.SignupActivity;



public class AuthViewModel extends ViewModel {


    public String accessCredential = "";
    public String passwordCredential = "";
    LoginResponse loginAnswer;
    SignupRequest signupRequest;
    LoginRequest loginRequest;

    AuthRepository authRepository = AuthRepository.getInstance();

    public AuthListener authListener = null;

    //Signup Procees
    public String signupEmail;
    public String signupName = "";
    public String signupBirthdate;
    public String signupUsername;
    public String signupPassword;
    public String signupPasswordTwo;





   public void onLoginButtonPressed(){


      if(accessCredential.isEmpty() || passwordCredential.isEmpty()){
          authListener.sendMessage("Please enter a valid username or password.");
       }else {
          loginRequest = new LoginRequest(accessCredential.trim(), passwordCredential);
          loginAnswer = authRepository.loginUser(loginRequest);
        }

      if(authRepository.getStatusCode() == 200){

      }



    }


    public void onSignupButtonPressed(View view){
       //when user already has an account
        Intent openSignUp = new Intent(view.getContext(), SignupActivity.class);
        view.getContext().startActivity(openSignUp);

    }

    public void onSignupPressed(View view){

       String ageYear = signupBirthdate.charAt(7) + "";

        if (TextUtils.isEmpty(signupEmail) || TextUtils.isEmpty(signupUsername) || TextUtils.isEmpty(signupPassword) || TextUtils.isEmpty(signupName)) {
            //checks if any mandatory fields are null

            authListener.sendMessage("You missed a spot");

        } else if (!emailIsVerified(signupEmail)) {
            //checks if email is in proper email form  TODO(another method to check if email is taken in the database)
            authListener.sendMessage("Please enter a valid email");

        } else if (!usernameIsValid(signupUsername)) {
            //checks if the format of the username is proper
            authListener.sendMessage("Please enter a valid username");

        } else if (signupBirthdate.charAt(3) < 12) {
            //checks if the user is older than 12
            authListener.sendMessage("You need to be 12 or older to use WishRoll");

        } else if(!signupPassword.equals(signupPasswordTwo)) {
            //checks if both passwords match each other
            authListener.sendMessage("Please enter the correct password");

        } else  {
            //sends signup request to the server, formats username, trims what needs to be trimmed

            authListener.sendMessage("Login Starting, Welcome to WishRoll");
            signupRequest = new SignupRequest(signupName, signupUsername, signupPassword, signupEmail, signupBirthdate);
            SignupResponse signupAnswer = authRepository.signupUser(signupRequest);


        }


    }


    public boolean emailIsVerified(String emailInput) {
        //checks if email entry is in correct email form

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }

    public boolean usernameIsValid(String usernameInput) {
        //Username Validation: no triple periods or underscores, no longer than 20 characters, no special characters

        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";

        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = usernamePat.matcher(usernameInput);

        return matcher.find();

    }

    public String formatUsername(String username) {

        return username.toLowerCase().replace(' ', '_');
    }




}
