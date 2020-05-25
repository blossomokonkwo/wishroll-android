package co.WishRoll.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import co.WishRoll.Models.User;
import co.WishRoll.R;
import co.WishRoll.Search.SearchActivity;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {
    //asks for the new user's password
    private static final String TAG = "PasswordActivity";


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


    }


    @Override
    public void onClick(View v) {



        switch(v.getId()) {
            case R.id.bJoin:


                String passwordOne = etSetPassword.getText().toString();
                String passwordTwo = etConfirmPassword.getText().toString();



                if((passwordOne.equals(passwordTwo)) && (!passwordOne.isEmpty() && !passwordTwo.isEmpty())) {

                    flowToMain(passwordOne);




                }else{

                    Toast.makeText(this, "Please confirm correct password", Toast.LENGTH_SHORT).show();
                    //set some sort of an alert to the user, this one isn't working
                    //Toast.makeText isn;t taking the Activity parameter for some reason I might
                    //to use alert dialog

                }


                break;

            case R.id.ibBackName:

                backNameFlow();

                break;
        }
    }









        public void backNameFlow (){
            Intent backName = new Intent(this, NameActivity.class);
            startActivity(backName);
        }

        public void flowToMain (String password){

            Intent mainFlow = new Intent (this, SearchActivity.class);

            Intent intent = getIntent();

            String fullName = intent.getStringExtra("fullName");
            String username = intent.getStringExtra("username");
            String email = intent.getStringExtra("email");

            mainFlow.putExtra("password", password);
            mainFlow.putExtra("username", username);
            mainFlow.putExtra("fullName", fullName);
            mainFlow.putExtra("email", email);

            User registeredData = new User(email, username, fullName, password);
            Log.d(TAG, "onClick: credentials of the registered user, please work lol aha "+ email + " " + username + " " + fullName + " " + password);

            startActivity(mainFlow);


    }

}