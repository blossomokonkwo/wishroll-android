package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);


        activityLoginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        activityLoginBinding.setAuthviewmodel(authViewModel);
        authViewModel.authListener = this;











        final EditText emailUsername = findViewById(R.id.etEmailUsername);
        final EditText password = findViewById(R.id.etPasswordEntry);

        Button bLogin = findViewById(R.id.bLogin1);
        TextView tvSignUp = (TextView) findViewById(R.id.newSignUp);






















        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openSignUp = new Intent(LoginActivity.this, SignupActivity.class);

                LoginActivity.this.startActivity(openSignUp);

            }
        });




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
}