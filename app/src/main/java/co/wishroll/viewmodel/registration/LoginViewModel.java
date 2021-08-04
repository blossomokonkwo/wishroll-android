package co.wishroll.viewmodel.registration;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.AuthRepository;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.AuthResource;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;


public class LoginViewModel extends ViewModel {


    AuthRepository authRepository = applicationGraph.authRepository();
    public AuthListener authListener = null;

    MediatorLiveData<AuthResource<AuthResponse>> authResponseMediatorLiveData = new MediatorLiveData<>();





    //Login Process
    public String accessCredential = "";
    public String passwordCredential = "";
    AuthResponse loginResponse;
    LoginRequest loginRequest;






   public void onLoginButtonPressed(){
      if(accessCredential.isEmpty() || passwordCredential.isEmpty()){
          authListener.onFailure("Please enter a valid username or password.");

       }else{
          loginRequest = new LoginRequest(accessCredential.trim(), passwordCredential);
          //authResponseMediatorLiveData = (MediatorLiveData<AuthResource<AuthResponse>>) authRepository.loginUser(loginRequest);
          //authListener.statusGetter(authRepository.getStatusCode());
          authenticateUser(loginRequest);


      }


    }

    public void authenticateUser(LoginRequest loginRequest){

        authResponseMediatorLiveData.setValue(AuthResource.loading(null));
       final LiveData<AuthResource<AuthResponse>> source = authRepository.loginUser(loginRequest);

       authResponseMediatorLiveData.addSource(source, new Observer<AuthResource<AuthResponse>>() {
           @Override
           public void onChanged(AuthResource<AuthResponse> authResponse) {
               authResponseMediatorLiveData.setValue(authResponse);

               authResponseMediatorLiveData.removeSource(source);

           }
       });
    }


    public LiveData<AuthResource<AuthResponse>> observeSignupUser(){
       return authResponseMediatorLiveData;
    }

















}
