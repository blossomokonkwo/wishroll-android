package co.wishroll.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PostageViewModelFactory implements ViewModelProvider.Factory {

    //please o only use this when you want to initalize uploads or likes. if not? just use the default pls
    private static final String TAG = "PostageViewModelFactory";
    private String username;
    private int id;



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UploadsViewModel.class)) {
            Log.d(TAG, "create: setting up the current user profile view");
            return (T) new UploadsViewModel(id);

        } else{
            Log.d(TAG, "create: setting up the likes grid ");
            return (T) new LikesGridViewModel(id);
        }


    }


    /*public ProfileViewModelFactory(String username) {
        this.username = username;

    }*/

    public PostageViewModelFactory(int id) {
        this.id = id;

    }
}
