package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.AuthRepository;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.utilities.AuthListener;

import static co.wishroll.WishRollApplication.applicationGraph;


public class LoginViewModel extends ViewModel {
    private static final String TAG = "Login View Model";

    AuthRepository authRepository = applicationGraph.authRepository();
    public AuthListener authListener = null;


    //Login Process
    public String accessCredential = "";
    public String passwordCredential = "";
    AuthResponse loginResponse;
    LoginRequest loginRequest;






   public void onLoginButtonPressed(){
       authListener.onStarted();
      if(accessCredential.isEmpty() || passwordCredential.isEmpty()){
          authListener.onFailure("Please enter a valid username or password.");

       }else {
          loginRequest = new LoginRequest(accessCredential.trim(), passwordCredential);
          loginResponse = authRepository.loginUser(loginRequest);
          authListener.statusGetter(authRepository.getStatusCode());
          authListener.onSuccess();
      }
    }













}
