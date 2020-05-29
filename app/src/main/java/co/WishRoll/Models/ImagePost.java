package co.WishRoll.Models;

import android.net.Uri;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class ImagePost extends Post {


    int numLikes;
    int timesDownloaded;
    int memorySize;
    int viewsGotten;

    boolean liked;
    boolean downloaded;
    boolean bookmarked;
    boolean deleted;
    boolean viewed;

    String postCaption;
    User userWhoPosted;
    String sentenceTag;

    Uri imageURL;

    public ImagePost(){

    }


    public ImagePost(User userWhoPosted, String stringImageURL, String sentenceTag, String caption){
        super(userWhoPosted, stringImageURL, sentenceTag, caption);



    }

    @Override
    public int getNumLikes() {
        return numLikes;
    }

    @Override
    public int getTimesDownloaded() {
        return timesDownloaded;
    }

    @Override
    public int getMemorySize() {
        return memorySize;
    }

    @Override
    public int getViewsGotten() {
        return viewsGotten;
    }

    @Override
    public boolean isLiked() {
        return liked;
    }

    @Override
    public boolean isDownloaded() {
        return downloaded;
    }

    @Override
    public boolean isBookmarked() {
        return bookmarked;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public boolean isViewed() {
        return viewed;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public User getUserWhoPosted() {
        return userWhoPosted;
    }

    public String getSentenceTag() {
        return sentenceTag;
    }

    public Uri getImageURL() {
        return imageURL;
    }

    @Override
    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    @Override
    public void setTimesDownloaded(int timesDownloaded) {
        this.timesDownloaded = timesDownloaded;
    }

    @Override
    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    @Override
    public void setViewsGotten(int viewsGotten) {
        this.viewsGotten = viewsGotten;
    }

    @Override
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }

    @Override
    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public void setUserWhoPosted(User userWhoPosted) {
        this.userWhoPosted = userWhoPosted;
    }

    public void setSentenceTag(String sentenceTag) {
        this.sentenceTag = sentenceTag;
    }

    public void setImageURL(String stringImageURL) {

        this.imageURL = Uri.parse(stringImageURL);
    }
}
