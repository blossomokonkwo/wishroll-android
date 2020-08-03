package co.wishroll.model;

import java.util.ArrayList;

public class Post {

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

    public Post() {
        this.user = new User("blossomokonkwo1@gmail.com", "blossom", 19, "potatochips", "okonkwo1");
        this.caption = "Just testing this out.";
        this.mediaExtension = "mp4";
        this.thumbnailURL = thumbnailURL;
        this.mediaURL = mediaURL;
        this.numViews = "0";
        this.numLikes = "0";
        this.numBookmarks = "0";
        this.numShares = "0";
        this.whoShared = whoShared;
        this.whoLiked = whoLiked;
        this.whoBookmarked = whoBookmarked;
        this.numComments = "0";
        this.commentsSection = commentsSection;
        this.timeAgo = "35 minutes ago";
    }
}
