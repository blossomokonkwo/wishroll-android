package co.WishRoll.Models;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends AppCompatActivity {

    String username;
    int followersCount;
    int followingCount;
    int totalViewCount;
    String profilePictureURL;
    boolean isVerified;
    boolean isFollowing;
    String fullName;
    String bio;



    public User(String username){
        //constructor for an instance of the User class
        this.username = username;






    }

    public boolean usernameCheck(String usernameInput){
        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";
        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = usernamePat.matcher(usernameInput);


        return matcher.find();





    }


    public int getFollowersCount() {
        //returns number of users following.

        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        //increments the user count by one when another user is followed.

        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        //returns numbers of users followed by this user.

        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        //increments following count when another user is followed.

        this.followingCount = followingCount;
    }

    public int getTotalViewCount() {
        //returns total number of times other users have seen a post from this user
        

        return totalViewCount;
    }

    public void setTotalViewCount(int totalViewCount) {
        //increments the users total view count



        this.totalViewCount = totalViewCount;
    }

    public String getProfilePictureURL() {
        //returns users profile picture URL, if null set as default

            return profilePictureURL;



    }


    public void setProfilePictureURL(String profilePictureURL) {
        //sets users profile picture URL


        this.profilePictureURL = profilePictureURL;
    }




    public boolean isVerified() {
        //checks if user is verified or not


        return isVerified;
    }




    public void setVerified(boolean verified) {
        //makes user verified


        isVerified = verified;
    }




    public boolean isFollowing() {
        //checks if user is following/not following another user.


        return isFollowing;
    }





    public void setFollowing(boolean following) {
        //makes user follow/unfollow another user


        isFollowing = following;
    }




    public String getFullName() {
        //returns user's full name

        return fullName;
    }




    public void setFullName(String fullName) {
        //sets user's full name

        this.fullName = fullName;
    }



    public String getBio() {
        //returns user's bio

        return bio;
    }




    public void setBio(String bio) {
        //sets user's bio

        if (bio.length()>200){

        }

        this.bio = bio;
    }





    public String getUsername() {
        //returns username

        return username;
    }




    public void setUsername(String rawUsername) {
        //sets username
        if(usernameCheck(rawUsername)) {

            String username = rawUsername.toLowerCase().replace(' ', '_');
            //convert all to lowercase and replace spaces with underscores

            this.username = username;

        }else{

            Toast.makeText(this, "Please enter a valid username", Toast.LENGTH_SHORT).show();

        }

    }
}
