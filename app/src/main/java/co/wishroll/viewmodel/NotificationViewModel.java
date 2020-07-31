package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.model.Notification;
import co.wishroll.model.User;

public class NotificationViewModel extends ViewModel {

    public Notification notification;
    User user;
    String timeAgo;
    String imageThumbnailURL;
    String actionPhrase;
    String notificationDescription;

    public NotificationViewModel(Notification notification) {
        this.notification = notification;
        this.user = notification.user;
        this.notificationDescription = notification.notificationDescription;
        this.timeAgo = notification.timeAgo;
        this.actionPhrase = notification.actionPhrase;
        this.imageThumbnailURL = notification.imageThumbnailURL;
        //TODO(time ago, URL, and action phrase are all things that need to be checked with the database to know for sure what they are so we'll leave that preset)
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }


}
