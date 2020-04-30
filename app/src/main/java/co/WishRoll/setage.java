package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class setage extends AppCompatActivity implements View.OnClickListener {

    Button bNext3;
    ImageButton ibBackUsername;
    EditText etMonth, etDay, etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setage);

        bNext3 = (Button) findViewById(R.id.bNext3);
        etMonth = (EditText) findViewById(R.id.etMonth);
        etDay = (EditText) findViewById(R.id.etDay);
        etYear = (EditText) findViewById(R.id.etYear);
        ibBackUsername = (ImageButton) findViewById(R.id.ibBackUsername);

        bNext3.setOnClickListener(this);
        ibBackUsername.setOnClickListener(this);

    }


    public void onClick(View v){
        switch(v.getId()){
            case R.id.bNext3:

                    openFullName();
                break;

            case R.id.ibBackUsername:
                backUsername();
                break;
        }
    }


    public void backUsername(){
        Intent backToUser = new Intent(this, setusername.class);
        startActivity(backToUser);

    }

    public void openFullName(){
        Intent flowToFull = new Intent(this, setfull.class);
        startActivity(flowToFull);

    }





}
