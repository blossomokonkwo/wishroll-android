package co.wishroll.views.registration;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.R;
import co.wishroll.databinding.ActivityLoginBinding;
import co.wishroll.utilities.AuthListener;
import co.wishroll.viewmodel.AuthViewModel;


public class LoginActivity extends AppCompatActivity implements AuthListener {


    private static final String TAG = "LOGIN ACTIVITY";
    ActivityLoginBinding activityLoginBinding;


    AuthViewModel authViewModel;

    Button login;
    EditText emailUser, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);


        activityLoginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        activityLoginBinding.setAuthviewmodel(authViewModel);
        authViewModel.authListener = this;



        emailUser = findViewById(R.id.etEmailUsername);
        password = findViewById(R.id.etPasswordEntry);







    }






   @Override
   public void onStarted() {
    Toast.makeText(this, "Login Started", Toast.LENGTH_SHORT).show();


}

    @Override
    public void onSuccess() {

        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(String message) {
       Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void sendMessage(String message){
       Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
   }

   @Override
    public void errorMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
   }
}
