package co.WishRoll.Models;

import android.net.Uri;

import java.util.ArrayList;

public class Post {

    int postIDB;
    int postIDU;
    int numLikes;
    int timesDownloaded;
    int memorySize;
    int viewsGotten;
    

    boolean liked;
    boolean downloaded;
    boolean bookmarked;
    boolean deleted;
    boolean viewed;

    String caption;
    User userWhoPosted;
    Uri imageURL;
    String sentenceTag;
    String stringPostUrl;

    //More instance variables I'm not sure if I should add:
    //comments? More like this??

    public Post(){

    }


    public Post(User userWhoPosted, String stringPostUrl, String sentenceTag, String caption){
        this.userWhoPosted = userWhoPosted;
        this.caption = caption;
        this.sentenceTag = sentenceTag;
        this.imageURL = Uri.parse(stringPostUrl);


    }





    public int getNumLikes() {
        //returns number of likes a certain post has
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        //I don't think we ever set this i think we might just increment this
        this.numLikes = numLikes;
    }

    public int getTimesDownloaded() {
        //not going to use this cosmetically in the near future
        return timesDownloaded;
    }

    public void setTimesDownloaded(int timesDownloaded) {
        //very weird setter, probably wont be used and just incremented
        this.timesDownloaded = timesDownloaded;
    }

    public int getMemorySize() {
        //lolol memory management effort part 1
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        //once again a very weird setter, I dont think I'll every use this cosmetically
        this.memorySize = memorySize;
    }

    public User getUserWhoPosted() {
        //returns the attributes of the user who posted the post
        return userWhoPosted;
    }

    public void setUserWhoPosted(User userWhoPosted) {

        this.userWhoPosted = userWhoPosted;
    }

    public boolean isLiked() {
        //whether or not the heart turns red for a user, onsetListener tingz

        return liked;
    }

    public void setLiked(boolean liked) {
        //make this true or false according to what the onSetClickListener says
        this.liked = liked;
    }

    public boolean isDownloaded() {
        //used for showing dialogs to let the user 'dem know
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        //used to say "yeah this is downloaded now"
        this.downloaded = downloaded;
    }

    public boolean isBookmarked() {
        //showing different icon fills for whether or not ting 'dem is bookmarked or not
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        //asking the onsetClickListener if it was clicked
        this.bookmarked = bookmarked;
    }

    public boolean isDeleted() {
        //dialog box talk, internal stuff talk, internal request stuff talk
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        //toast talk
        this.deleted = deleted;
    }

    public String getCaption() {
        //displaying the caption for the world to see
        return caption;
    }

    public void setCaption(String caption) {
        //happens in the post dressing stage
        this.caption = caption;
    }

    public int getViewsGotten() {
        //honestly i think i may have to total this up with the rest to display on the profile
        return viewsGotten;
    }

    public void setViewsGotten(int viewsGotten) {
        //increment tingz
        this.viewsGotten = viewsGotten;
    }

    public boolean isViewed() {
        //onViewListener???
        return viewed;
    }

    public void setViewed(boolean viewed) {
        //setViewed true because of onViewListener, then increment the setViewsGotten (might have to be hidden doe idk how its finna work)
        this.viewed = viewed;
    }












}
