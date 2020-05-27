package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import co.WishRoll.R;
import co.WishRoll.Search.SearchActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //gets already signed in user's email/username and password verifies credentials


    Button bLogin;
    TextView tvPrompt;
    EditText etPassword, etUsernameEmail;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLogin = (Button) findViewById(R.id.bLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsernameEmail = (EditText) findViewById(R.id.etUsernameEmail);
        tvPrompt = (TextView) findViewById(R.id.tvPrompt);

        tvPrompt.setOnClickListener(this);
        bLogin.setOnClickListener(this);







    }


    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.bLogin:
                //might need to add verification method here, more if else statements
                //checking credentials against the database values etc

                //if log in is successfull
                //User user = null;
                //userLocalStore.storeUserData(user);
                //userLocalStore.setUserLoggedIn(true);




                    startMainPg();
                break;
            case R.id.tvPrompt:
                openSignUpActivity();
                break;

        }
    }

    public void openSignUpActivity(){
        Intent signUpFlow = new Intent(this, RegisterActivity.class);
        startActivity(signUpFlow);
    }


    public void startMainPg(){
        Intent flowToMain = new Intent(this, SearchActivity.class);
        startActivity(flowToMain);
    }
}
