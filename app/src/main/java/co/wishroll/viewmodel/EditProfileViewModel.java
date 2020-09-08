package co.wishroll.viewmodel;

import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.datamodels.UpdateResponse;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class EditProfileViewModel extends ViewModel{

    private static final String TAG = "EditProfileViewModel";
    UserRepository userRepository = applicationGraph.userRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    public AuthListener authListener = null;
    public HashMap<String, String> changedValues = new HashMap<String, String>();
    MediatorLiveData<StateData<UpdateResponse>> editedCurrentUser = new MediatorLiveData<>();


    public ObservableField<String> editName = new ObservableField<>(sessionManagement.getName());

    public String getEditName(){
        return editName.get();
    }

    public void setEditName(ObservableField<String> name) {
        this.editName = name;
        this.editName.notifyChange();
    }


    public ObservableField<String> editUsername = new ObservableField<>(sessionManagement.getUsername());

    public String getEditUsername(){
        return editUsername.get();
    }

    public void setEditUsername(ObservableField<String> username) {
        this.editUsername = username;
        this.editUsername.notifyChange();
    }




    public ObservableField<String> editEmail = new ObservableField<>(sessionManagement.getEmail());

    public String getEditEmail(){
        return editEmail.get();
    }

    public void setEditEmail(ObservableField<String> email) {
        this.editEmail = email;
        this.editEmail.notifyChange();
    }


    public ObservableField<String> editBio = new ObservableField<>(sessionManagement.getBio());
    public String getEditBio(){
        return editBio.get();
    }

    public void setEditBio(ObservableField<String> bio) {
        this.editBio = bio;
        this.editBio.notifyChange();
    }



   /* public ObservableField<String> editProfileURL = new ObservableField<>(sessionManagement.getAvatarURL());
    public String getEditProfileURL(){
        return editProfileURL.get();
    }

    public void setEditProfileURL(ObservableField<String> profileURL) {
        this.editProfileURL = profileURL;
        this.editProfileURL.notifyChange();
    }*/

   /* public ObservableField<String> editBackgroundURL = new ObservableField<>(sessionManagement.getBackgroundURL());
    public String getEditBackgroundURL(){
        return editBackgroundURL.get();
    }

    public void setEditBackgroundURL(ObservableField<String> backgroundURL) {
        this.editBackgroundURL = backgroundURL;
        this.editBackgroundURL.notifyChange();
    }*/

    String editNameNow;
    String editUsernameNow;
    String editEmailNow;
    String editBioNow;
    String editProfileURLNow;
    String editBackgroundURLNow;

    public void setEditProfileURLNow(String url){
        this.editProfileURLNow = url;

    }

    public String getEditProfileURLNow(){
        return editProfileURLNow;
    }

    public void setEditBackgroundURLNow(String url){
        this.editBackgroundURLNow = url;
    }

    public String getEditBackgroundURLNow(){
        return editBackgroundURLNow;
    }




    public void afterNameChange(CharSequence s){
        this.editNameNow =  s.toString();
    }
    public void afterUsernameChange(CharSequence s){
        this.editUsernameNow =  s.toString();



    }
    public void afterEmailChange(CharSequence s){
        this.editEmailNow =  s.toString();
    }
    public void afterBioChange(CharSequence s){
        //Log.d(TAG, "afterBioChange: this is how the bio has changed " + s.toString());
        this.editBioNow =  s.toString();
    }
    public void afterBackgroundURLChange(CharSequence s){
        this.editBackgroundURLNow =  s.toString();
    }
    public void afterProfileURLChange(CharSequence s){
        this.editProfileURLNow =  s.toString();
    }








   public void onSaveChanges(){


       sessionManagement.printEverything("save changes button is pressed.");

        if(editUsername.get() == null ||  editEmail.get() == null ){  //probably can't even do if it is null because it could just mean that it didn't change
            authListener.sendMessage("Please enter a value.");

        }else if(!usernameIsValid(editUsername.get())){
            authListener.sendMessage("Please enter a valid username");

        }else {


            if (editUsernameNow != null && !sessionManagement.getUsername().equals(editUsernameNow)) { //if its changed then do this
                changedValues.put("username", editUsernameNow);

            }

            if (editNameNow != null) {  //meaning if it changed
                changedValues.put("name", editNameNow);
            }

            if (editEmailNow != null) { //if this changed then do this

                if (!emailIsVerified(editEmailNow)) {
                    authListener.sendMessage("Please enter a valid email");

                } else {
                    changedValues.put("email", editEmailNow);
                }

            }

            if (editBioNow != null) {
                changedValues.put("bio", editBioNow);

            }

            if (editBackgroundURLNow != null) {
                Log.d(TAG, "onSaveChanges: BACKGROUND URL GOTTEN FROM ACTIVITY " + editBackgroundURLNow);
                changedValues.put("profile_background_media", editBackgroundURLNow);

            }

            if (editProfileURLNow != null) {
                Log.d(TAG, "onSaveChanges: PROFILE URL GOTTEN FROM ACTIVITY " + editBackgroundURLNow);

                changedValues.put("avatar", editProfileURLNow);
            }


            if (!changedValues.isEmpty()) {
                sessionManagement.printEverything("user changed some values");
                Log.d(TAG, "onSaveChanges: all values in the changed " + changedValues.toString());

                if(changedValues.get("username") == null ){
                    changedValues.remove("username");


                }
                //send to server!!!! XD<333
                //updateCurrentUser(changedValues);

            } else {
                sessionManagement.printEverything("user did not change any values");
                Log.d(TAG, "onSaveChanges: the user did not make any changes");
            }


        }
        }





    public MediatorLiveData<StateData<UpdateResponse>> observeEditsMade(){
        return editedCurrentUser;
    }



    public void updateCurrentUser(Map<String, String> changedAttributes){
        //for when the save button is pressed
        Log.d(TAG, "updateCurrentUser: in the update current user method of the view model");
        editedCurrentUser.setValue(StateData.loading((UpdateResponse) null));
        final LiveData<StateData<UpdateResponse>> source = userRepository.updateUser(changedAttributes);
        editedCurrentUser.addSource(source, new Observer<StateData<UpdateResponse>>() {

            @Override
            public void onChanged(StateData<UpdateResponse> updatedResponseStateData) {
                Log.d(TAG, "onChanged: value has changed so now we set value and remove source");
                editedCurrentUser.setValue(updatedResponseStateData);
                editedCurrentUser.removeSource(source);
            }
        });
    }


    public boolean usernameIsValid(String usernameInput){
        //Username Validation: no triple periods or underscores, no longer than 20 characters, no special characters

        String usernameRegex = "^[A-Z0-9]([._](?![._])|[a-z0-9]){1,20}[a-z0-9]$";

        Pattern usernamePat = Pattern.compile(usernameRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = usernamePat.matcher(usernameInput);

        return matcher.find();

    }

    public String formatUsername(String username){

        return username.toLowerCase().trim().replace(' ', '_');
    }

    public static boolean emailIsVerified(String emailInput){
        //checks if email entry is in correct email form

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);

        Matcher matcher = emailPat.matcher(emailInput);

        return matcher.find();
    }


}
