package co.wishroll.models.repository.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.WishRollApplication;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.EditedUser;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.utilities.Encryption;

@Singleton
public class SessionManagement extends Application {
    private static final String TAG = "SessionManagement";
    //stores current user information

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
     Encryption encryption = Encryption.getDefault("Key", "chile", new byte[16]);
    public String token;


    @Inject
    public SessionManagement(){

        sharedPreferences = WishRollApplication.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public boolean saveSession(UserModel userModel, AccessToken accessToken){

        editor.putInt("id", userModel.getId());
        editor.putString("username", userModel.getUsername());

        editor.putString("bio", userModel.getBio());

        if( userModel.getAvatar() == null) {
            editor.putString("avatar", "");
        }else{
            editor.putString("avatar", userModel.getAvatar());

        }



        if( userModel.getProfileBackgroundUrl() != null) {
            editor.putString("background_url", userModel.getProfileBackgroundUrl());
        }else{
            editor.putString("background_url", "");

        }
        editor.putString("email", userModel.getEmail());
        editor.putString("token", encryption.encryptOrNull(accessToken.getAccess()));

        return editor.commit();


    }






    public void printEverything(String location){
        Log.d(TAG, "printEverything: WE ARE CURRENTLY IN: " + location);
        Log.d(TAG, "printEverything: user's id: " + getCurrentUserId());
        Log.d(TAG, "printEverything: user's avatar url: " + getAvatarURL());
        Log.d(TAG, "printEverything: user's email: " + getEmail() );
        Log.d(TAG, "printEverything: user's bio: " + getBio());
        Log.d(TAG, "printEverything: user's name: " + getName());
        Log.d(TAG, "printEverything: user's background url: " + getBackgroundURL());
        Log.d(TAG, "printEverything: user's authie token: " + getToken());



    }




    public String getEmail(){
        return sharedPreferences.getString("email", "");
    }

    public String getAvatarURL(){
        return sharedPreferences.getString("avatar", "");
    }

    public String getBackgroundURL(){
        return sharedPreferences.getString("background_url", "");
    }

    public String getUsername(){
        return sharedPreferences.getString("username", "");
    }

    public String getBio(){

        return sharedPreferences.getString("bio", "");
    }

    public String getName(){
        return sharedPreferences.getString("name", "");
    }

    public String getToken() {
        token = encryption.decryptOrNull(sharedPreferences.getString("token", null));
        return token;
    }

    public int getCurrentUserId() {
        return sharedPreferences.getInt("id", 0);
    }

    public boolean clearSession(){
        editor.remove("id");
        editor.remove("email");
        editor.remove("token");
        editor.remove("name");
        editor.remove("bio");
        editor.remove("background_url");
        editor.remove("avatar");
       return  editor.commit();

    }

    public void editUserDetails(EditedUser editedUser){
        Log.d(TAG, "editUserDetails: updating the user's session with returned values");
        editor.putString("username", editedUser.getUsername());
        editor.putString("name", editedUser.getName());
        editor.putString("avatar", editedUser.getAvatarUrl());
        editor.putString("background_url", editedUser.getBackgroundUrl());
        editor.putString("email", editedUser.getEmail());
        editor.putString("bio", editedUser.getBio());
        editor.commit();



    }



    public void checkLogout(){
        Log.d(TAG, "saveSession: REMOVED CURRENT USER ID: " + sharedPreferences.getInt("id", 0));
        Log.d(TAG, "saveSession: REMOVED CURRENT USERNAME: " + sharedPreferences.getString("username", ""));
        Log.d(TAG, "saveSession: REMOVED CURRENT BIO: " + sharedPreferences.getString("bio", ""));
        Log.d(TAG, "saveSession: REMOVED CURRENT NAME: " + sharedPreferences.getString("name", ""));
        Log.d(TAG, "saveSession: REMOVED CURRENT EMAIL: " + sharedPreferences.getString("email", ""));
        Log.d(TAG, "saveSession: REMOVED ENCRYPTED TOKEN: " + sharedPreferences.getString("token", ""));

    }
}
