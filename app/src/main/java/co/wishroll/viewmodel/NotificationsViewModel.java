package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;


public class NotificationsViewModel extends ViewModel {

    public NotificationsViewModel() {
        //doesnt need to pass an id because will always belong to current user so maybe some session action idk

    }

    //ViewModel for the Notification Fragment of the Profile Page View, only current user can see their own
    //fetches notifications and displays them in a list form
}
