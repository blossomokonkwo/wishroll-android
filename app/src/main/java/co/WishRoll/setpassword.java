package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class setpassword extends AppCompatActivity implements View.OnClickListener {

    Button bJoin;
    EditText etSetPassword, etConfirmPassword;
    ImageButton ibBackName;
    TextView tvTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpassword);

        bJoin = (Button) findViewById(R.id.bJoin);
        etSetPassword = (EditText) findViewById(R.id.etSetPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        ibBackName = (ImageButton) findViewById(R.id.ibBackName);

        bJoin.setOnClickListener(this);
        ibBackName.setOnClickListener(this);






    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.bJoin:
                String passwordOne = etSetPassword.getText().toString();
                String passwordTwo = etConfirmPassword.getText().toString();
                //is it a problem that this is defined in the switch case? can this still be sent to the database styl?
                if((passwordOne.equals(passwordTwo)) && (!passwordOne.isEmpty() && !passwordTwo.isEmpty())) {
                    flowToMain();
                }else{
                    Toast.makeText(this, "Please confirm correct password", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ibBackName:
                backNameFlow();
                break;
        }
    }

    public void backNameFlow(){
        Intent backName = new Intent(this, setfull.class);
        startActivity(backName);
    }

    public void flowToMain(){
        Intent mainFlow = new Intent (this, searchpage.class);
        startActivity(mainFlow);
    }
}
