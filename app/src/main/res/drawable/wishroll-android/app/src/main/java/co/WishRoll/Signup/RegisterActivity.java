package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.WishRoll.Models.User;
import co.WishRoll.R;

//This activity is the first page in the RegisterActivity sequence. It asks for the users email and validates.
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvAlready;
    EditText etEmailSignup;
    Button bNext1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tvAlready = (TextView) findViewById(R.id.tvAlready);
        bNext1 = (Button) findViewById(R.id.bNext1);
        etEmailSignup = (EditText) findViewById(R.id.etEmailSignup);



        bNext1.setOnClickListener(this);
        tvAlready.setOnClickListener(this);






    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bNext1:
                //takes email entered and validates it
                String email = etEmailSignup.getText().toString();
                if(validateEmailForm(email)) {
                    openRegister2();
                }else{
                    //Alert that shows that email is invalid
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

    public void openRegister2(){
        Intent flowToSetUsername = new Intent(this, UsernameActivity.class );
        startActivity(flowToSetUsername);
    }

    public static boolean validateEmailForm(String emailInput){

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9._%+-]+\\.[A-Z]{2,64}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }


}
