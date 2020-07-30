package co.wishroll.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import co.wishroll.model.User;

public class UserViewModel extends ViewModel {

    public String username;
    public String email;
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
        this.username = user.username;
        this.email = user.email;
        this.fullName = user.fullName;
        this.bio = user.bio;
        this.user = user;
        this.wishRollScore = user.wishRollScore.toString();
        this.profilePictureURL = user.profilePictureURL;
        this.isVerified = user.isVerified;
        this.followersCount = user.followersCount + "";
        this.followingCount = user.followingCount + "";
        this.totalViewCount = user.totalViewCount + "";


    }
}
