package co.wishroll.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private static final String TAG = "ProfileViewModelFactory";
    private String username;
    private int id;



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            Log.d(TAG, "create: setting up the current user profile view");
            return (T) new ProfileViewModel(id);

        } else{
            Log.d(TAG, "create: setting up the others profile view ");
            return (T) new OtherProfileViewModel(id);
        }


    }


    /*public ProfileViewModelFactory(String username) {
        this.username = username;

    }*/

    public ProfileViewModelFactory(int id) {
        this.id = id;

    }
}
