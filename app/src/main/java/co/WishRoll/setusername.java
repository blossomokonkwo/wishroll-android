package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class setusername extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibBackEmail;
    EditText etUsernameSignUp;
    // Do we even need this
    // TextView tvUsernameLabel;
    Button bNext2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setusername);

        ibBackEmail = (ImageButton) findViewById(R.id.ibBackEmail);
        etUsernameSignUp = (EditText) findViewById(R.id.etUsernameSignup);
        bNext2 = (Button) findViewById(R.id.bNext2);


        ibBackEmail.setOnClickListener(this);
        bNext2.setOnClickListener(this);

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.ibBackEmail:
                openSetEmail();
                break;
            case R.id.bNext2:
                String username = etUsernameSignUp.getText().toString();
                if(usernameCheck(username)){
                    openSetAge();
                }else{
                    Toast.makeText(this, "Please enter a valid username", Toast.LENGTH_SHORT).show();
                }

                break;


        }

    }

    public void openSetEmail(){
        Intent backEmailFlow = new Intent(this, register.class);
        startActivity(backEmailFlow);

    }

    public void openSetAge(){
        Intent setAgeFlow = new Intent(this, setage.class);
        startActivity(setAgeFlow);
    }

    public boolean usernameCheck(String usernameInput){
        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";
        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = usernamePat.matcher(usernameInput);

        return matcher.find();
    }
}
