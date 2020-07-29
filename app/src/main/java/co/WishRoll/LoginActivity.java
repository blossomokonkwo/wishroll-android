package co.WishRoll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.content.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText emailUsername = findViewById(R.id.etEmailUsername);
        final EditText password = findViewById(R.id.etPasswordEntry);

        Button bLogin = findViewById(R.id.bLogin1);
        final ProgressBar progressBarLogin = findViewById(R.id.progressBarLogin);
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        TextView tvSignUp = (TextView) findViewById(R.id.newSignUp);




        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent openSignUp = new Intent(LoginActivity.this, SignupActivity.class);

                LoginActivity.this.startActivity(openSignUp);

            }
        });



        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //authenticates data

                String emailEntry = emailUsername.getText().toString();
                String passwordEntry = password.getText().toString();

                progressBarLogin.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(emailEntry, passwordEntry).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));

                        }else{

                            Toast.makeText(LoginActivity.this, "Error" + task.getException(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

    }

}