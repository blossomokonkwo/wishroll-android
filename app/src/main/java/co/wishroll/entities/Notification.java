package co.wishroll.entities;

import androidx.annotation.Nullable;

public class Notification {


    public User user = new User("blossomokonkwo1@gmail.com", "blossom", 19, "potatochips", "okonkwo1");
    public String timeAgo;
    public String imageThumbnailURL;
    public String actionPhrase;
    public String notificationDescription;

    public Notification() {
        //TODO(for now, skinnier version)
        //preset values for actionPhrase & timeago, and yes, thumbnail which is in the recyclerviewpager
        this.user = user;
        timeAgo = "35 minutes ago";
        actionPhrase = "liked your post!";
        notificationDescription = user.username + " " + actionPhrase;

    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public void setNotificationDescription() {
        //Form: potatochips liked your post!
        //TODO(sort of written with the intent that nothing is being fetched from the API rn, atm, at this present moment)
        this.notificationDescription = this.user.username + " " + this.actionPhrase + "!";
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
        //TODO(once again, no networking stuff just yet)
        return imageThumbnailURL;
    }

    public void setImageThumnailURL(String imageThumnailURL) {
        //TODO(no networking stuff just yet)
        //This is most definitely where I would use a request to idk, get these URLS as photos? or even commuicate with a repository that communicates with the API
        this.imageThumbnailURL = imageThumnailURL;
    }

    public String getActionPhrase() {
        //TODO(get user interaction and create a sort of a switch case with CATEGORIES of: liked, shared, or followed, depending on the action)
        //if followed, no URL picture shown, maybe its all in the notification tuple and we get to have it come back as null, who knows
        return actionPhrase;
    }

    public void setActionPhrase(String actionPhrase) {
        this.actionPhrase = actionPhrase;
    }
}
