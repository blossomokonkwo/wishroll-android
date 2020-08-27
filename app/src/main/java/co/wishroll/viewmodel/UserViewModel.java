package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.domainmodels.User;

public class UserViewModel extends ViewModel {
    //ViewModel for the User class
    //represents the user class

    public String username;
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



    public UserViewModel(User user) {

    }
}
