package co.WishRoll.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {
    //local storage of the current user on their device



    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
            userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);


    }

    public void storeUserData(User user){
        //initially stores user data
            SharedPreferences.Editor spEditor = userLocalDatabase.edit();
            spEditor.putString("username", user.username);
            spEditor.putString("fullName", user.fullName);
            spEditor.putString("password", user.password);
            spEditor.putString("profilePictureURL", user.profilePictureURL);
            spEditor.putString("email", user.email);
            //yeah lol when do I stop? do I copy over everything? Is everything shared in the local database??
            spEditor.commit();



    }

    public User getLoggedInUser(){
        //returns the user that has already been stored
            String username = userLocalDatabase.getString("username", "");
            String password = userLocalDatabase.getString("password", "");
            String email = userLocalDatabase.getString("email", "");


            User storedUser = new User(username, password, email );

            return storedUser;

    }

    public void setUserLoggedIn(boolean loggedIn){
        //sets the status to loggen in
            SharedPreferences.Editor spEditor = userLocalDatabase.edit();
            spEditor.putBoolean("loggedIn", loggedIn);
            spEditor.commit();
    }

    public void clearUserData(){
        //clears user from local storage, logs user out
            SharedPreferences.Editor spEditor = userLocalDatabase.edit();
            spEditor.clear();
            spEditor.commit();


    }

    public boolean getUserLoggedIn(){
        //returns whether or not the userd is logged in
        if(userLocalDatabase.getBoolean("loggedIn", false)){
            //if the user is logged in, return true
            return true;
        }else{
            return false;
        }
    }






}