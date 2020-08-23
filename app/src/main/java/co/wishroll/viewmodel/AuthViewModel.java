package co.wishroll.viewmodel;

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

import static co.wishroll.WishRollApplication.applicationGraph;


public class AuthViewModel extends ViewModel {
    AuthRepository authRepository = applicationGraph.authRepository();
    public AuthListener authListener = null;


    //Login Process
    public String accessCredential = "";
    public String passwordCredential = "";
    LoginResponse loginAnswer;
    LoginRequest loginRequest;




    //Signup Procees
    public String signupEmail;
    public String signupName = "";
    public String signupBirthdate;
    public String signupUsername;
    public String signupPassword;
    public String signupPasswordTwo;
    SignupRequest signupRequest;
    SignupResponse signupResponse;





   public void onLoginButtonPressed(){

       authListener.onStarted();
      if(accessCredential.isEmpty() || passwordCredential.isEmpty()){
          authListener.onFailure("Please enter a valid username or password.");
       }else {

          loginRequest = new LoginRequest(accessCredential.trim(), passwordCredential);
          loginAnswer = authRepository.loginUser(loginRequest);
          authListener.statusGetter(authRepository.getStatusCode());
      }



    }


    public void onSignupButtonPressed(){

    }

    public void onSignupPressed(View view){
       authListener.onStarted();

       String ageYear = signupBirthdate.charAt(7) + "";

        if (TextUtils.isEmpty(signupEmail) || TextUtils.isEmpty(signupUsername) || TextUtils.isEmpty(signupPassword) || TextUtils.isEmpty(signupName)) {
            //checks if any mandatory fields are null

            authListener.onFailure("You missed a spot");

        } else if (!emailIsVerified(signupEmail)) {
            //checks if email is in proper email form  TODO(another method to check if email is taken in the database)
            authListener.onFailure("Please enter a valid email");

        } else if (!usernameIsValid(signupUsername)) {
            //checks if the format of the username is proper
            authListener.onFailure("Please enter a valid username");

        } else if (signupBirthdate.charAt(3) < 12) {
            //checks if the user is older than 12
            authListener.onFailure("You need to be 12 or older to use WishRoll");

        } else if(!signupPassword.equals(signupPasswordTwo)) {
            //checks if both passwords match each other
            authListener.onFailure("Please enter the correct password");

        } else  {
            //sends signup request to the server, formats username, trims what needs to be trimmed
            signupRequest = new SignupRequest(signupName, signupUsername, signupPassword, signupEmail, signupBirthdate);
             signupResponse = authRepository.signupUser(signupRequest);

        }

        authListener.statusGetter(authRepository.getStatusCode());



    }


    public boolean emailIsVerified(String emailInput) {
        //checks if email entry is in correct email form
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailInput);
        return matcher.find();
    }

    public boolean usernameIsValid(String usernameInput) {
        //Username Validation: no ... or ___ , < 20 char
        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";
        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = usernamePat.matcher(usernameInput);
        return matcher.find();

    }

    public String formatUsername(String username) {
        return username.toLowerCase().replace(' ', '_');
    }




}
