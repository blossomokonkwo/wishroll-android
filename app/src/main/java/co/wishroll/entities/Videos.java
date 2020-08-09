package co.wishroll.entities;

import java.util.ArrayList;

public class Videos extends Post {

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
}
