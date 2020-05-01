package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import co.WishRoll.R;

public class NameActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etFullName;
    Button bNext4;
    ImageButton ibBackAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setfull);

        etFullName = (EditText) findViewById(R.id.etFullName);
        bNext4 = (Button) findViewById(R.id.bNext4);
        ibBackAge = (ImageButton) findViewById(R.id.ibBackAge);

        bNext4.setOnClickListener(this);
        ibBackAge.setOnClickListener(this);


    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.bNext4:
                flowToPassword();
                break;
            case R.id.ibBackAge:
                backToAge();
                break;
        }
    }


    public void flowToPassword(){
        Intent toPassword  = new Intent(this, PasswordActivity.class);
        startActivity(toPassword);
    }

    public void backToAge(){
        Intent backAge = new Intent(this, AgeActivity.class);
        startActivity(backAge);
    }
}
