package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.domainmodels.User;

public class OtherProfileViewModel extends ViewModel {
    //ViewModel for the User class
    //represents the user class

    private static final String TAG = "OtherProfileViewModel";
    public int pointerId;
    public String followersCount;
    public String followingCount;
    public String totalViewCount;
    public String profilePictureURL;
    public String defaultProfileURL;
    public boolean isVerified;
    public boolean isFollowing;
    public String fullName;
    public String bio;
    public String wishRollScore;
    private User user;

    public OtherProfileViewModel(int id){
        this.pointerId = id;
        Log.d(TAG, "OtherProfileViewModel: the username of this user is " + pointerId);
    }


    


}
