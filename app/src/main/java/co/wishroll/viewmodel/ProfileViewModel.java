package co.wishroll.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ProfileViewModel extends ViewModel {
    UserRepository userRepository = applicationGraph.userRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();

    LiveData<StateData<String>> username = new MediatorLiveData<>();
    LiveData<StateData<String>> profilePictureUrl = new MediatorLiveData<>();
    LiveData<StateData<String>> bannerPictureURL = new MediatorLiveData<>();
    LiveData<StateData<String>> fullName = new MediatorLiveData<>();
    LiveData<StateData<String>> bio = new MediatorLiveData<>();
    MediatorLiveData<StateData<UserModel>> currentUserLiveData = new MediatorLiveData<>();


    public LiveData<StateData<UserModel>> observeCurrentUser() {
        return currentUserLiveData;
    }

    /*public void retrieveCurrentUser(){
        currentUserLiveData.setValue(StateData.loading((UserModel)null));
        //final LiveData<StateData<UserModel>> source = null; // = userRepository.getUserModel(sessionManagement.getCurrentUserId());

        currentUserLiveData.addSource(source, new Observer<StateData<UserModel>>() {
            @Override
            public void onChanged(StateData<UserModel> usermodelResponse) {
                currentUserLiveData.setValue(usermodelResponse);
                currentUserLiveData.removeSource(source);

            }
        });
    }*/
}
