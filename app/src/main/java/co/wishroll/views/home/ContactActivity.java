package co.wishroll.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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



        emailSubject = findViewById(R.id.emailSubject);
        emailBody = findViewById(R.id.emailBody);

        Button sendEmail = findViewById(R.id.bSendEmail);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmail();
            }
        });

        ImageView backButton = findViewById(R.id.backContact);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    private void openEmail(){


        String [] recepients = {"support@wishroll.co"};
        String subject = emailSubject.getText().toString();
        String message = emailBody.getText().toString();


        if(TextUtils.isEmpty(subject) || TextUtils.isEmpty(message)){
            Toast.makeText(ContactActivity.this, "You missed a spot", Toast.LENGTH_LONG).show();
        }else{


            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_EMAIL, recepients);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, message);

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Choose Email App:"));

        }

    }

}