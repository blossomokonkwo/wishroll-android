package co.wishroll.models.domainmodels;

import java.util.ArrayList;

public class Images extends Post {

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

    public Images(User user, String caption, String mediaExtension,
                  String thumbnailURL, String mediaURL, String numViews,
                  String numLikes, String numBookmarks, String numShares,
                  ArrayList<User> whoShared, ArrayList<User> whoLiked,
                  ArrayList<User> whoBookmarked, String numComments,
                  ArrayList<Comment> commentsSection, String timeAgo) {

        this.user = user;
        this.caption = caption;
        this.mediaExtension = mediaExtension;
        this.thumbnailURL = thumbnailURL;
        this.mediaURL = mediaURL;
        this.numViews = numViews;
        this.numLikes = numLikes;
        this.numBookmarks = numBookmarks;
        this.numShares = numShares;
        this.whoShared = whoShared;
        this.whoLiked = whoLiked;
        this.whoBookmarked = whoBookmarked;
        this.numComments = numComments;
        this.commentsSection = commentsSection;
        this.timeAgo = timeAgo;
    }
}
