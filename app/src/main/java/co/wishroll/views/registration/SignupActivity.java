package co.wishroll.views.registration;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

import co.wishroll.R;
import co.wishroll.databinding.ActivitySignupBinding;
import co.wishroll.utilities.AuthListener;
import co.wishroll.viewmodel.AuthViewModel;

public class SignupActivity extends AppCompatActivity implements AuthListener {

    private static final String TAG = "SIGNUP ACTIVITY";
    ActivitySignupBinding activitySignupBinding;
    AuthViewModel authViewModel;




    EditText etEmail;
    EditText etFullName;
    EditText birthDate;
    EditText etUsername;
    EditText etPasswordOne;
    EditText etPasswordTwo;
    private ProgressBar progressBar;
    Button bSignup;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail = findViewById(R.id.userEmail);
        etFullName = findViewById(R.id.userFullName);
        birthDate = findViewById(R.id.selectBirthdate);
        etUsername = findViewById(R.id.userUsername);
        etPasswordOne = findViewById(R.id.userPasswordOne);
        etPasswordTwo = findViewById(R.id.userPasswordTwo);
        progressBar = findViewById(R.id.progressBar);




        birthDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            final int year = calendar.get(Calendar.YEAR);
            final int day = calendar.get(Calendar.DAY_OF_MONTH);
            final int month = calendar.get(Calendar.MONTH);
            datePickerDialog = new DatePickerDialog(SignupActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    birthDate.setText(year + "-" + month + "-" + day);

                }
            }, year, month, day);
            datePickerDialog.show();
        });

        activitySignupBinding = DataBindingUtil.setContentView(SignupActivity.this, R.layout.activity_signup);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        activitySignupBinding.setAuthviewmodel(authViewModel);
        authViewModel.authListener = this;


        bSignup = findViewById(R.id.bCreateAccount);




    }






    @Override
    public void onStarted() {
        Toast.makeText(this, "Signup started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void sendMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void errorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}