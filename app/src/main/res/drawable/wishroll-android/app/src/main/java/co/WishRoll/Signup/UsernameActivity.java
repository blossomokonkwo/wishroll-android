package co.wishroll.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import co.wishroll.Models.User;
import co.wishroll.R;
import co.wishroll.models.datamodels.User;

public class UsernameActivity extends AppCompatActivity implements View.OnClickListener {

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

        User user = new User(null);
        user.setUsername(etUsernameSignUp.getText().toString());

    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.ibBackEmail:
                openSetEmail();
                break;
            case R.id.bNext2:
                openSetAge();
                break;
        }

    }

    public void openSetEmail(){
        Intent backEmailFlow = new Intent(this, RegisterActivity.class);
        startActivity(backEmailFlow);

    }

    public void openSetAge(){
        Intent setAgeFlow = new Intent(this, AgeActivity.class);
        startActivity(setAgeFlow);
    }


}
