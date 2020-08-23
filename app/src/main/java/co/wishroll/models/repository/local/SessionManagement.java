package co.wishroll.models.repository.local;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.datamodels.AccessToken;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.utilities.Encryption;

@Singleton
public class SessionManagement extends Application {
    //stores current user information
    //wipes current user information on log out
    //stores authentication token of user
    //wipes authentication token of user
    //TODO(responsible for refreshing user authorization token when it expires)

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    public static User currentClient;
    Encryption encryption = Encryption.getDefault("Key", "chile", new byte[16]);
    public static String token;




    @Inject
    public SessionManagement(){
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



    }

    public void saveSession(UserModel userModel, AccessToken accessToken){
        editor.putInt("id", userModel.getId());
        editor.putString("token", encryption.encryptOrNull(accessToken.getAccess()));
        editor.commit();


    }

    public int getSession(){

        return -1;
    }

    public String getToken() {
         token = encryption.decryptOrNull(sharedPreferences.getString("token", null));
        return token;
    }

    public static User getCurrentClient() {
        return currentClient;
    }

    public void clearSession(){

    }
}
