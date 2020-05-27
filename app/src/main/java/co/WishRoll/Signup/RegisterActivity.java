package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.WishRoll.Models.User;
import co.WishRoll.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    //asks for the new user's email, checks if email is already in data base

    private static final String TAG = "RegisterActivity";


    TextView tvAlready;
    Button bNext1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvAlready = (TextView) findViewById(R.id.tvAlready);
        bNext1 = (Button) findViewById(R.id.bNext1);




        bNext1.setOnClickListener(this);
        tvAlready.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bNext1:

                EditText etEmailSignup = (EditText) findViewById(R.id.etEmailSignup);
                String email = etEmailSignup.getText().toString();

                if(validateEmailForm(email)){

                    openRegister2(email);


                }else{

                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.tvAlready:
                openLogin();
                break;
        }
    }



    public void openLogin(){

        Intent backLogin = new Intent(this, MainActivity.class);

        startActivity(backLogin);
    }

    public void openRegister2(String email){
        Intent flowToSetUsername = new Intent(this, UsernameActivity.class );

        flowToSetUsername.putExtra("email", email);
        startActivity(flowToSetUsername);

    }

    public static boolean validateEmailForm(String emailInput){

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9._%+-]+\\.[A-Z]{2,64}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }



}
