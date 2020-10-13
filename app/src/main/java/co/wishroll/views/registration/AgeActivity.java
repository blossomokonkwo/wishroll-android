package co.wishroll.views.registration;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import co.wishroll.R;
import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.ToastUtils;

public class AgeActivity extends AppCompatActivity implements AuthListener {

    private static final String TAG = "SIGNUP ACTIVITY";
    public String year;
    public String month;
    public String day;
    RadioGroup radioGroupGender;
    RadioButton radioButtonGender;




    EditText etMonth, etDay, etYear;
    Button nextAgeButton;
    ProgressBar progressBarAge;
    ImageButton backAge;
    int genderNum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age);
        etMonth = findViewById(R.id.etMonth);
        etDay = findViewById(R.id.etDay);
        etYear = findViewById(R.id.etYear);




        nextAgeButton = findViewById(R.id.bNextBirthday);
        progressBarAge = findViewById(R.id.progressBarAge);
        backAge = findViewById(R.id.backAge);

        radioGroupGender = findViewById(R.id.rgGender);




        backAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgeActivity.this, UsernameActivity.class));
                finish();
            }
        });



        nextAgeButton.setOnClickListener(new View.OnClickListener() {
            //Male 0 Female 1 PNA 2 ints
            @Override
            public void onClick(View view) {

                year = etYear.getText().toString();
                month = etMonth.getText().toString();
                day = etDay.getText().toString();

                if((TextUtils.isEmpty(year) || TextUtils.isEmpty(month) || TextUtils.isEmpty(day)) || !ageClean(month, day, year)){
                    onFailure("Please enter a correct birthday");

                }else if(!ofAge(month, day, year)){
                    onFailure("You need to be 12 or older to use WishRoll");

                }else{

                    int radioId = radioGroupGender.getCheckedRadioButtonId();
                    Log.d(TAG, "checkButton: THIS IS WHAT THIS LOOKS LIKE " + radioId);

                    switch (radioGroupGender.getCheckedRadioButtonId()){
                        case (R.id.rbMale):
                            genderNum = 0;
                            break;
                        case (R.id.rbFemale):
                            genderNum = 1;
                            break;
                        case (R.id.rbPrefer):
                            genderNum = 2;
                            break;
                    }

                    SignupRequest.setGender(genderNum);
                    SignupRequest.setBirthday(formatBirthdate(month, day, year));
                    statusGetter(200);
                    Log.d(TAG, "onNextEmail: asc values: " + SignupRequest.getEmail() +  " " + SignupRequest.getBirthday() + " " + SignupRequest.getGender()) ;
                }
            }
        });











    }


    public String formatBirthdate(String month, String day, String year){
        int monthNum = Integer.parseInt(month);
        int dayNum = Integer.parseInt(day);


        String dbMonth;
        String dbDay;

        if(monthNum <= 9) {
            dbMonth = "0" + monthNum;
        }else{
            dbMonth = month;
        }


        if(dayNum <= 9){
            dbDay = "0" + dayNum;
        }else{
            dbDay = day;
        }


        return year + "-" + dbMonth + "-" + dbDay;
    }

    public boolean ofAge(String month, String day, String year){
        int monthNum = Integer.parseInt(month);
        int dayNum = Integer.parseInt(day);
        int yearNum = Integer.parseInt(year);

        final Calendar c = Calendar.getInstance();
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int mYear = c.get(Calendar.YEAR);

        int birth = (monthNum * 10) + (dayNum) + (yearNum * 100);
        int rightNow = (mMonth * 10) + (mDay) + (mYear * 100);
        int twelve = 1200;
        int age = rightNow - birth;


        return (age >= twelve);




    }

    public boolean ageClean(String month, String day, String year){
        int monthNum = Integer.parseInt(month);
        int dayNum = Integer.parseInt(day);
        int yearNum = Integer.parseInt(year);

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);

        if(monthNum > 12 || dayNum > 31 || yearNum >= mYear || monthNum == 0 || dayNum == 0 || yearNum == 0){
            return false;

        }else{
            return true;

        }


    }





    @Override
    public void onStarted() {

    }

    @Override
    public void onSuccess() {


    }

    @Override
    public void onFailure(String message) {
        //progressBarSignup.setVisibility(View.GONE);
        ToastUtils.showToast(this, message);

    }

    @Override
    public void sendMessage(String message) {
        ToastUtils.showToast(this, message);

    }

    @Override
    public void errorMessage(String message) {
        ToastUtils.showToast(this, message);

    }

    @Override
    public void statusGetter(int statusCode) {

        switch(statusCode){

            case 100:
                break;

            case 200:
                Intent intent = new Intent(AgeActivity.this, SignupActivity.class);

                startActivity(intent);

                //progressBarSignup.setVisibility(View.INVISIBLE);
                break;

            case 401:
                Toast.makeText(this, "You entered the wrong credentials, please try again", Toast.LENGTH_SHORT).show();
                break;

            case 400:
                Toast.makeText(this, "The email or username you have entered is taken", Toast.LENGTH_SHORT).show();
                break;

            default:
                //progressBarSignup.setVisibility(View.VISIBLE);
                break;

        }

    }
}