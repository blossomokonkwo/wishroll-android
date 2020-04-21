package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class register extends AppCompatActivity implements View.OnClickListener{

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

       String emailSignup = etEmailSignup.toString();

        bNext1.setOnClickListener(this);
        tvAlready.setOnClickListener(this);

      //boolean emailCorrect = validateEmailForm(etEmailSignup);
      //turning editText to String


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bNext1:
                openRegister2();

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
        Intent flowToSetUsername = new Intent(this, setusername.class );
        startActivity(flowToSetUsername);
    }

    public static boolean validateEmailForm(String emailInput){

        String emailRegex = "^[A-ZO-9._%+-]+@[A-ZO-9._%+-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(emailInput);
        return matcher.find();
    }
}
