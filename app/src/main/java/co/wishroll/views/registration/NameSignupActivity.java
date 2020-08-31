package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import co.wishroll.R;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.utilities.AuthListener;


public class NameSignupActivity extends AppCompatActivity implements AuthListener {
    private static final String TAG = "SIGNUP ACTIVITY";


    EditText etName;
    ProgressBar progressBarName;
    ImageButton backName;
    Button nextName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namesignup);


        etName = findViewById(R.id.userFullName);
        progressBarName = findViewById(R.id.progressBarName);
        backName = findViewById(R.id.backName);
        nextName = findViewById(R.id.bNextName);


        backName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NameSignupActivity.this, SignupActivity.class));
                finish();
            }
        });


        nextName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SignupRequest.setName(etName.getText().toString());
                    statusGetter(200);
                    Log.d(TAG, "onNextEmail: asc values: " + SignupRequest.getEmail() + " " + SignupRequest.getName());
            }
        });
















    }


    private void showNameProgressBar(boolean isVisible){
        if(isVisible){
            progressBarName.setVisibility(View.VISIBLE);
        }else{
            progressBarName.setVisibility(View.GONE);
        }
    }



    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String message) {
        //progressBarSignup.setVisibility(View.GONE);
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

    @Override
    public void statusGetter(int statusCode) {

        switch(statusCode){

            case 100:
                Toast.makeText(this, "Please try again.", Toast.LENGTH_SHORT).show();
                break;

            case 200:
                Intent intent = new Intent(NameSignupActivity.this, AgeSignupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                //progressBarSignup.setVisibility(View.INVISIBLE);
                break;

            case 404:
                Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
                break;

            case 401:
                Toast.makeText(this, "You entered the wrong credentials, please try again", Toast.LENGTH_SHORT).show();
                break;

            case 400:
                Toast.makeText(this, "The email or username you have entered is taken", Toast.LENGTH_SHORT).show();
                break;

            default:
                //progressBarSignup.setVisibility(View.VISIBLE);
                break;

        }

    }
}