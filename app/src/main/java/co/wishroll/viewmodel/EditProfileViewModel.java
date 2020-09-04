package co.wishroll.viewmodel;

import android.text.TextUtils;
import android.widget.EditText;

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

    UserRepository userRepository = applicationGraph.userRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    AuthListener authListener = null;
    MediatorLiveData<StateData<EditedUser>> editedCurrentUser = new MediatorLiveData<>();



    public String editName;
    public String editUsername;
    public String editEmail = "";
    public String editBio = "";
    public String editProfileURL;
    public String editBackgroundURL;



    public void onSaveChanges(){
        if(TextUtils.isEmpty(editUsername) || TextUtils.isEmpty(editEmail)){
            authListener.sendMessage("Please enter a value.");

        }else if(!usernameIsValid(editUsername)){
            authListener.sendMessage("Please enter a valid username");

        }else if(!userRepository.usernameIsAvailable(editUsername)){
            authListener.sendMessage("That username is taken");

        }else if(!emailIsVerified(editEmail)){
            authListener.sendMessage("Please enter a valid email");

        }else{
            HashMap<String, String> changedValues = new HashMap<String, String>();

            if(!sessionManagement.getUsername().equals(editUsername)){
                changedValues.put("username", editUsername);

            }else if(!sessionManagement.getName().equals(editName)){
                changedValues.put("name", editName);

            } else if(!sessionManagement.getEmail().equals(editEmail)){
                changedValues.put("email", editEmail);

            }else if(!sessionManagement.getBio().equals(editBio)){
                changedValues.put("bio", editBio);

            }else if(!sessionManagement.getBackgroundURL().equals(editBackgroundURL)){
                changedValues.put("profile_background_media", editBackgroundURL);
            }else if(!sessionManagement.getAvatarURL().equals(editProfileURL)){
                changedValues.put("avatar", editProfileURL);
            }else{
                updateCurrentUser(changedValues);
            }



        }

    }


    public void updateCurrentUser(Map<String, String> changedAttributes){
        editedCurrentUser.setValue(StateData.loading((EditedUser)null));
        final LiveData<StateData<EditedUser>> source = userRepository.updateUser(changedAttributes);
        editedCurrentUser.addSource(source, new Observer<StateData<EditedUser>>() {
            @Override
            public void onChanged(StateData<EditedUser> editedUserStateData) {
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

    public String formatUsername(EditText username){

        return username.getText().toString().toLowerCase().replace(' ', '_');
    }

    public static boolean emailIsVerified(String emailInput){
        //checks if email entry is in correct email form

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }
}
