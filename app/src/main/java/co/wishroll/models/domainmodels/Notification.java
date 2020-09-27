package co.wishroll.models.domainmodels;

import androidx.annotation.Nullable;

public class Notification {

    public User user = new User("blossom", 1, false);
    public String timeAgo;
    public String imageThumbnailURL;
    public String actionPhrase;
    public String notificationDescription;

    public Notification() {
        this.user = user;
        timeAgo = "35 minutes ago";
        actionPhrase = "liked your post!";
        notificationDescription = "blossom liked your post!";

    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription() {
        this.notificationDescription = "blossom liked your post!";
    }

    public String getTimeAgo() {
        //Form: 35 minutes ago
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {

        this.timeAgo = timeAgo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Nullable
    public String getImageThumnailURL() {
        return imageThumbnailURL;
    }

    public void setImageThumnailURL(String imageThumnailURL) {
        this.imageThumbnailURL = imageThumnailURL;
    }

    public String getActionPhrase() {
        return actionPhrase;
    }

    public void setActionPhrase(String actionPhrase) {
        this.actionPhrase = actionPhrase;
    }
}
