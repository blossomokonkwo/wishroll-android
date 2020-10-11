package co.wishroll.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private String username;
    private int id;



    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ProfileViewModel.class)){
            return (T) new ProfileViewModel(id);
        }else{
            return (T) new OtherProfileViewModel(id);
        }

    }


    public ProfileViewModelFactory(String username) {
        this.username = username;

    }

    public ProfileViewModelFactory(int id) {
        this.id = id;

    }
}