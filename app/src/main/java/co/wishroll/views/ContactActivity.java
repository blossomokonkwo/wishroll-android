package co.wishroll.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.wishroll.R;

public class ContactActivity extends AppCompatActivity {

    private EditText emailSendee;
    private EditText emailSender;
    private EditText emailSubject;
    private EditText emailBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Button sendEmail = findViewById(R.id.bSendEmail);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });


    }


    private void openEmail(){
        //https://www.youtube.com/watch?v=tZ2YEw6SoBU @ 8:42

    }
}