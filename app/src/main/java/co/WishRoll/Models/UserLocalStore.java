package co.WishRoll.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStore {



    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
            userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);


    }

    public void storeUserData(User user){
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
            String username = userLocalDatabase.getString("username", "");
            String password = userLocalDatabase.getString("password", "");
            String email = userLocalDatabase.getString("email", "");


            User storedUser = new User(username, password, email );

            return storedUser;

    }

    public void setUserLoggedIn(boolean loggedIn){
            SharedPreferences.Editor spEditor = userLocalDatabase.edit();
            spEditor.putBoolean("loggedIn", loggedIn);
            spEditor.commit();
    }

    public void clearUserData(){
            SharedPreferences.Editor spEditor = userLocalDatabase.edit();
            spEditor.clear();
            spEditor.commit();


    }






}