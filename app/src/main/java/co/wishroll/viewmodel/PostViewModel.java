package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.model.Comment;
import co.wishroll.model.Post;
import co.wishroll.model.User;

public class PostViewModel extends ViewModel {
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

    public PostViewModel(Post post) {
        User user = post.user;
        String caption = post.caption;
        String mediaExtension = post.mediaExtension;
        String thumbnailURL = post.thumbnailURL;
        String numViews = post.numViews;
        String numLikes = post.numLikes;
        String mediaURL = post.mediaURL;
        String numBookmarks = post.numBookmarks;
        String numShares = post.numShares;
        String numComments = post.numComments;
        ArrayList<Comment> commentsSection = post.commentsSection;
        String timeAgo = post.timeAgo;
        ArrayList<User> whoShared = post.whoShared;
        ArrayList<User> whoBookmarked = post.whoBookmarked;
        ArrayList<User> whoLiked = post.whoLiked;
    }
}
