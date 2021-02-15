package co.wishroll.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.models.domainmodels.UserNotification;
import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class NotificationsViewModel extends ViewModel {

    int userId;
    PostRepository postRepository = applicationGraph.postRepository();
    MediatorLiveData<StateData<ArrayList<UserNotification>>> listOfNotifications = new MediatorLiveData<>();
    final int START_OFFSET = 0;
    int offset = 0;
    int notificationsDataSetSize = 0;

    public NotificationsViewModel(int userId) {
        this.userId = userId;

        if(userId == 0){

        }else{
            getFirstPageOfNotifications();

        }

    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNotificationsDataSetSize() {
        return notificationsDataSetSize;
    }

    public void setNotificationsDataSetSize(int notificationsDataSetSize) {
        this.notificationsDataSetSize = notificationsDataSetSize;
    }


    public void getFirstPageOfNotifications() {

    }

    public void getMoreNotifications(int offsetie){

    }

    public LiveData<StateData<ArrayList<UserNotification>>> observeNotifications(){
        return listOfNotifications;
    }

}

