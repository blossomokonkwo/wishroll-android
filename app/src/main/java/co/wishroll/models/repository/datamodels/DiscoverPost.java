package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DiscoverPost {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("view_count")
    @Expose
    private Integer viewCount;

    @SerializedName("share_count")
    @Expose
    private Integer shareCount;

    @SerializedName("viewed")
    @Expose
    private Boolean viewed;

    @SerializedName("bookmarked")
    @Expose
    private Boolean bookmarked;

    @SerializedName("bookmark_count")
    @Expose
    private Integer bookmarkCount;

    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;

    @SerializedName("like_count")
    @Expose
    private Integer likeCount;

    @SerializedName("liked")
    @Expose
    private Boolean liked;

    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("media_url")
    @Expose
    private String mediaUrl;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;

    @SerializedName("creator")
    @Expose
    private Creator creator;

    /**
     * No args constructor for use in serialization
     *
     */
    public DiscoverPost() {
    }

    /**
     *
     * @param creator
     * @param mediaUrl
     * @param bookmarked
     * @param caption
     * @param likeCount
     * @param liked
     * @param commentCount
     * @param createdAt
     * @param shareCount
     * @param viewed
     * @param bookmarkCount
     * @param id
     * @param viewCount
     * @param updatedAt
     * @param thumbnailUrl
     */
    public DiscoverPost(Integer id, String createdAt, String updatedAt, Integer viewCount, Integer shareCount, Boolean viewed, Boolean bookmarked, Integer bookmarkCount, Integer commentCount, Integer likeCount, Boolean liked, String caption, String mediaUrl, String thumbnailUrl, Creator creator) {
        super();
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.viewCount = viewCount;
        this.shareCount = shareCount;
        this.viewed = viewed;
        this.bookmarked = bookmarked;
        this.bookmarkCount = bookmarkCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.liked = liked;
        this.caption = caption;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public Integer getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }
}
