package co.wishroll.entities;

import java.util.ArrayList;

public class Videos extends Post {

    public String user_id;
    public User user;
    public String caption;
    public String mediaExtension;
    public String thumbnailURL;
    public String mediaURL;
    public String numViews;
    public String numLikes;
    public String numBookmarks;
    public String numShares;
    public ArrayList<User> whoShared;
    public ArrayList<User> whoLiked;
    public ArrayList<User> whoBookmarked;
    public String numComments;
    public ArrayList<Comment> commentsSection;
    public String timeAgo;

  /*  public Videos(User user, String caption,
                  String thumbnailURL, String mediaURL, String numViews,
                  String numLikes, String numBookmarks, String numShares,
                  ArrayList<User> whoShared, ArrayList<User> whoLiked,
                  ArrayList<User> whoBookmarked, String numComments,
                  ArrayList<Comment> commentsSection, String timeAgo) {*/

  public Videos(){

        this.user = new User("blossomokonkwo1@gmail.com", "blossom", 19, "potatochips", "okonkwo1");
        this.caption = "Hi this is a post, Im so excited";
        this.thumbnailURL = thumbnailURL;
        this.mediaURL = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4";
        this.numViews = "78";
        this.numLikes = "98";
        this.numBookmarks = "98";
        this.numShares = "90";
        this.whoShared = whoShared;
        this.whoLiked = whoLiked;
        this.whoBookmarked = whoBookmarked;
        this.numComments = "54";
        this.commentsSection = commentsSection;
        this.timeAgo = "56 minutes ago";

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMediaExtension() {
        return mediaExtension;
    }

    public void setMediaExtension(String mediaExtension) {
        this.mediaExtension = mediaExtension;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }

    public String getNumViews() {
        return numViews;
    }

    public void setNumViews(String numViews) {
        this.numViews = numViews;
    }

    public String getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(String numLikes) {
        this.numLikes = numLikes;
    }

    public String getNumBookmarks() {
        return numBookmarks;
    }

    public void setNumBookmarks(String numBookmarks) {
        this.numBookmarks = numBookmarks;
    }

    public String getNumShares() {
        return numShares;
    }

    public void setNumShares(String numShares) {
        this.numShares = numShares;
    }

    public ArrayList<User> getWhoShared() {
        return whoShared;
    }

    public void setWhoShared(ArrayList<User> whoShared) {
        this.whoShared = whoShared;
    }

    public ArrayList<User> getWhoLiked() {
        return whoLiked;
    }

    public void setWhoLiked(ArrayList<User> whoLiked) {
        this.whoLiked = whoLiked;
    }

    public ArrayList<User> getWhoBookmarked() {
        return whoBookmarked;
    }

    public void setWhoBookmarked(ArrayList<User> whoBookmarked) {
        this.whoBookmarked = whoBookmarked;
    }

    public String getNumComments() {
        return numComments;
    }

    public void setNumComments(String numComments) {
        this.numComments = numComments;
    }

    public ArrayList<Comment> getCommentsSection() {
        return commentsSection;
    }

    public void setCommentsSection(ArrayList<Comment> commentsSection) {
        this.commentsSection = commentsSection;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }
}
