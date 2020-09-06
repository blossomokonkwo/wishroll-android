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
    //wipes current user information on log out
    //stores authentication token of user
    //wipes authentication token of user
    

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

    public void saveSession(UserModel userModel, AccessToken accessToken){

        editor.putInt("id", userModel.getId());
        Log.d(TAG, "saveSession: CURRENT USER ID: " + userModel.getId());

        editor.putString("username", userModel.getUsername());
        Log.d(TAG, "saveSession: CURRENT USERNAME: " + userModel.getUsername());

        editor.putString("bio", userModel.getBio());
        Log.d(TAG, "saveSession: CURRENT BIO: " + userModel.getBio());

        if( userModel.getAvatar() == null) {
            editor.putString("avatar", "");
        }else{
            editor.putString("avatar", userModel.getAvatar());
            Log.d(TAG, "saveSession: CURRENT AVATAR URL STRING: " + userModel.getAvatar());

        }

        if( userModel.getProfileBackgroundUrl() != null) {
            editor.putString("background_url", userModel.getProfileBackgroundUrl());
            Log.d(TAG, "saveSession: CURRENT BACKGROUND URL STRING: " + userModel.getProfileBackgroundUrl());
        }else{
            editor.putString("background_url", "");

        }

        editor.putString("name", userModel.getName());
        Log.d(TAG, "saveSession: CURRENT NAME: " + userModel.getName());

        editor.putString("email", userModel.getEmail());
        Log.d(TAG, "saveSession: CURRENT EMAIL: " + userModel.getEmail());

        Log.d(TAG, "saveSession: ORIGINAL TOKEN: " + accessToken.getAccess());
        editor.putString("token", encryption.encryptOrNull(accessToken.getAccess()));
        Log.d(TAG, "saveSession: ENCRYPTED TOKEN: " + encryption.encryptOrNull(accessToken.getAccess()));

        editor.commit();


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

    public boolean clearClearables(){
        editor.remove("username");
        editor.remove("bio");
        editor.remove("avatar");
        editor.remove("name");
        editor.remove("email");
        editor.remove("background");
        return editor.commit();
    }

    public void printEverything(String location){
        Log.d(TAG, "printing everything at this point: " + location );
        Log.d(TAG, "printEverything: username: " + sharedPreferences.getString("username", "username is blank"));
        Log.d(TAG, "printEverything: name: " + sharedPreferences.getString("name", "name is blank"));
        Log.d(TAG, "printEverything: bio: " + sharedPreferences.getString("bio", "bio is blank"));
        Log.d(TAG, "printEverything: email: " + sharedPreferences.getString("email", "email is blank"));

    }

    //Editing User Profile Methods




    public String getEmail(){
        Log.d(TAG, "getEmail: returning the email held in session");

        return sharedPreferences.getString("email", "");
    }

    public String getAvatarURL(){
        Log.d(TAG, "getAvatarURL: returning the avatar url held in session");
        return sharedPreferences.getString("avatar", "");
    }

    public String getBackgroundURL(){
        return sharedPreferences.getString("background_url", "");
    }

    public String getUsername(){
        Log.d(TAG, "getUsername: returning the username held in session");
        return sharedPreferences.getString("username", "");
    }

    public String getBio(){

        Log.d(TAG, "getBio: returning the bio held in session");
        return sharedPreferences.getString("bio", "");
    }

    public String getName(){
        Log.d(TAG, "getName: returning the name held in session");
        return sharedPreferences.getString("name", "");
    }

    public String getToken() {
        Log.d(TAG, "getToken: DECRYPTED TOKEN: " + encryption.decryptOrNull(sharedPreferences.getString("token", null)));
         token = encryption.decryptOrNull(sharedPreferences.getString("token", null));
        return token;
    }

    public int getCurrentUserId() {

        return sharedPreferences.getInt("id", 0);
    }

    public void clearSession(){
        editor.remove("id");
        editor.remove("username");
        editor.remove("bio");
        editor.remove("avatar");
        editor.remove("name");
        editor.remove("email");
        editor.remove("token");
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
