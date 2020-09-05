package co.wishroll.viewmodel;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.datamodels.EditedUser;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class EditProfileViewModel extends ViewModel {
    private static final String TAG = "EditProfileViewModel";

    UserRepository userRepository = applicationGraph.userRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    public AuthListener authListener = null;
    MediatorLiveData<StateData<EditedUser>> editedCurrentUser = new MediatorLiveData<>();




    //the strings that are supposed to be placeholders are coming up empty :((((




    public String editName = sessionManagement.getName();
    public String editUsername = sessionManagement.getUsername();
    public String editEmail = sessionManagement.getEmail();
    public String editBio = ""; //= sessionManagement.getBio();
    public String editProfileURL = sessionManagement.getAvatarURL();
    public String editBackgroundURL = sessionManagement.getBackgroundURL();

   /* public EditProfileViewModel(){
         editName = sessionManagement.getName();
         editUsername = sessionManagement.getUsername();
        editEmail = sessionManagement.getEmail();
        editBio = sessionManagement.getBio();
         editProfileURL = sessionManagement.getAvatarURL();
       editBackgroundURL = sessionManagement.getBackgroundURL();
    }
*/





    public void onSaveChanges(){

        Log.d(TAG, "onSaveChanges: save changes button was pressed.");
        if(TextUtils.isEmpty(editUsername) || TextUtils.isEmpty(editEmail)){
            authListener.sendMessage("Please enter a value.");

        }else if(!usernameIsValid(editUsername)){
            Log.d(TAG, "onSaveChanges: username is not valid clause in the if statement");
            authListener.sendMessage("Please enter a valid username");

        }else{
            Log.d(TAG, "onSaveChanges: all these things are correct now we are adding them into the hashmap");
            HashMap<String, String> changedValues = new HashMap<String, String>();

                    if(!sessionManagement.getUsername().equals(editUsername)){
                         if(!userRepository.usernameIsAvailable(editUsername)) {
                             authListener.sendMessage("That username is taken");
                             Log.d(TAG, "onSaveChanges: this username is not available");

                         }else{
                             Log.d(TAG, "onSaveChanges: username was changed so we are adding it to the hashmap");
                             changedValues.put("username", formatUsername(editUsername));
                         }
                    }else if(!sessionManagement.getName().equals(editName)){
                        Log.d(TAG, "onSaveChanges: name was changed so we are adding it to the hashmap");
                        changedValues.put("name", editName);

                    } else if(!sessionManagement.getEmail().equals(editEmail)){

                        if(!emailIsVerified(editEmail)) {
                            Log.d(TAG, "onSaveChanges: email is not valid clause in the if statement");
                            authListener.sendMessage("Please enter a valid email");

                        }else{
                            Log.d(TAG, "onSaveChanges: email was changed so we are adding it to the hashmap");
                            changedValues.put("email", editEmail);
                        }

                    }else if(!sessionManagement.getBio().equals(editBio)){
                            Log.d(TAG, "onSaveChanges: bio was changed so we are adding it to the hashmap");
                            changedValues.put("bio", editBio);

                    }else if(!sessionManagement.getBackgroundURL().equals(editBackgroundURL)){
                            Log.d(TAG, "onSaveChanges: background url was changed so we are adding it to the hashmap");
                            changedValues.put("profile_background_media", editBackgroundURL);

                    }else if (!sessionManagement.getAvatarURL().equals(editProfileURL)){
                            Log.d(TAG, "onSaveChanges: profile picture was changed so we are adding it to the hashmap");
                            changedValues.put("avatar", editProfileURL);
                    }


                if(!changedValues.isEmpty()){
                    Log.d(TAG, "onSaveChanges: we are now going to update the current user, finally");
                    updateCurrentUser(changedValues);
                }else{
                    Log.d(TAG, "onSaveChanges: the user did not make any changes");
                }



        }

    }



    public void updateCurrentUser(Map<String, String> changedAttributes){
        Log.d(TAG, "updateCurrentUser: in the update current user method of the view model");
        editedCurrentUser.setValue(StateData.loading((EditedUser)null));
        final LiveData<StateData<EditedUser>> source = userRepository.updateUser(changedAttributes);
        editedCurrentUser.addSource(source, new Observer<StateData<EditedUser>>() {
            @Override
            public void onChanged(StateData<EditedUser> editedUserStateData) {
                Log.d(TAG, "onChanged: value has changed so now we set value and remove source");
                editedCurrentUser.setValue(editedUserStateData);
                editedCurrentUser.removeSource(source);
            }
        });
    }

    public MediatorLiveData<StateData<EditedUser>> observeEditsMade(){
        return editedCurrentUser;
    }


    public boolean usernameIsValid(String usernameInput){
        //Username Validation: no triple periods or underscores, no longer than 20 characters, no special characters

        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";

        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = usernamePat.matcher(usernameInput);

        return matcher.find();

    }

    public String formatUsername(String username){

        return username.toLowerCase().replace(' ', '_');
    }

    public static boolean emailIsVerified(String emailInput){
        //checks if email entry is in correct email form

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }
}
