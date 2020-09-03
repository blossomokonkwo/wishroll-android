package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class OtherProfileViewModel extends ViewModel {
    //ViewModel for the User class
    //represents the user class

    private static final String TAG = "OtherProfileViewModel";
    public int pointerId;
    public String pointerUsername;


    public OtherProfileViewModel(int id){
        this.pointerId = id;
        Log.d(TAG, "OtherProfileViewModel: the username of this user is " + pointerId);
    }

    public OtherProfileViewModel(String username){
        this.pointerUsername = username;
        Log.d(TAG, "OtherProfileViewModel: the username of this user is " + pointerUsername);
    }


    


}
