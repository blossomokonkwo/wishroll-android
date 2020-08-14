package co.wishroll.models.domainmodels;

import java.util.ArrayList;

public class Post {

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

    public Post() {

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
