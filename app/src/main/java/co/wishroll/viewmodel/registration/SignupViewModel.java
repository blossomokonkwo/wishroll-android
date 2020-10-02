package co.wishroll.viewmodel.registration;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.util.Calendar;

import co.wishroll.models.repository.datamodels.SignupRequest;
import co.wishroll.utilities.AuthListener;


public class SignupViewModel extends ViewModel {
    private static final String TAG = "Signup View Model";



    public AuthListener authListenerSign = null;



    //Signup Procees
    public String signupName = "";











    public void onNextName(){

        if(TextUtils.isEmpty(signupName)){
            authListenerSign.onFailure("Please enter your name");

        }else{
            SignupRequest.setName(signupName);
            authListenerSign.statusGetter(200);
            Log.d(TAG, "onNextEmail: asc values: " + SignupRequest.getEmail() + " " + SignupRequest.getName()) ;
        }

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






}
