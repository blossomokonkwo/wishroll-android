package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    UserRepository userRepository = applicationGraph.userRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    MediatorLiveData<StateData<User>> userLiveData = new MediatorLiveData<>();
    int pointerId;

    public ProfileViewModel(int id){
        this.pointerId = id;
        Log.d(TAG, "ProfileViewModel: initialized current user username " + pointerId);
        getCurrentUser(pointerId);

    }

    public void getCurrentUser(int id){
        userLiveData.setValue(StateData.loading((User)null));
        final LiveData<StateData<User>> source = userRepository.getUserById(id);
        userLiveData.addSource(source, new Observer<StateData<User>>() {
            @Override
            public void onChanged(StateData<User> userStateData) {
                userLiveData.setValue(userStateData);
                userLiveData.removeSource(source);
            }
        });




    }
    public LiveData<StateData<User>> observeCurrentUserProfile(){
        return userLiveData;
    }


    //do this when the activity is created






}
