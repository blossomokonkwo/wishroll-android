package co.wishroll.models.domainmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post implements Parcelable {


    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("viewed")
    @Expose
    private Boolean viewed;

    @SerializedName("bookmarked")
    @Expose
    private Boolean bookmarked;

    @SerializedName("share_count")
    @Expose
    private Integer shareCount;

    @SerializedName("bookmark_count")
    @Expose
    private Integer bookmarkCount;

    @SerializedName("like_count")
    @Expose
    private Integer likeCount;

    @SerializedName("liked")
    @Expose
    private Boolean liked;

    @SerializedName("view_count")
    @Expose
    private Integer viewCount;

    @SerializedName("media_url")
    @Expose
    private String mediaUrl;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;

    @SerializedName("creator")
    @Expose
    private PostCreator creator;

    /**
     * No args constructor for use in serialization
     *
     */
    public Post() {
    }

    /**
     *
     * @param creator
     * @param mediaUrl
     * @param viewed
     * @param id
     * @param thumbnailUrl
     */
    public Post(Integer id,  Integer viewCount, Integer shareCount, Boolean viewed, Boolean bookmarked, Integer bookmarkCount, Integer commentCount, Integer likeCount, Boolean liked,  String mediaUrl, String thumbnailUrl, PostCreator creator) {        super();
        this.id = id;
        this.viewCount = viewCount;
        this.shareCount = shareCount;
        this.viewed = viewed;
        this.bookmarked = bookmarked;
        this.bookmarkCount = bookmarkCount;
        this.likeCount = likeCount;
        this.liked = liked;
        this.mediaUrl = mediaUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.creator = creator;
    }


    protected Post(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        byte tmpViewed = in.readByte();
        viewed = tmpViewed == 0 ? null : tmpViewed == 1;
        byte tmpBookmarked = in.readByte();
        bookmarked = tmpBookmarked == 0 ? null : tmpBookmarked == 1;
        if (in.readByte() == 0) {
            shareCount = null;
        } else {
            shareCount = in.readInt();
        }
        if (in.readByte() == 0) {
            bookmarkCount = null;
        } else {
            bookmarkCount = in.readInt();
        }
        if (in.readByte() == 0) {
            likeCount = null;
        } else {
            likeCount = in.readInt();
        }
        byte tmpLiked = in.readByte();
        liked = tmpLiked == 0 ? null : tmpLiked == 1;
        if (in.readByte() == 0) {
            viewCount = null;
        } else {
            viewCount = in.readInt();
        }
        mediaUrl = in.readString();
        thumbnailUrl = in.readString();
        creator = in.readParcelable(PostCreator.class.getClassLoader());
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeByte((byte) (viewed == null ? 0 : viewed ? 1 : 2));
        parcel.writeByte((byte) (bookmarked == null ? 0 : bookmarked ? 1 : 2));
        if (shareCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(shareCount);
        }
        if (bookmarkCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(bookmarkCount);
        }
        if (likeCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(likeCount);
        }
        parcel.writeByte((byte) (liked == null ? 0 : liked ? 1 : 2));
        if (viewCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(viewCount);
        }
        parcel.writeString(mediaUrl);
        parcel.writeString(thumbnailUrl);
        parcel.writeParcelable(creator, i);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(Integer bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
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

    public PostCreator getCreator() {
        return creator;
    }

    public void setCreator(PostCreator creator) {
        this.creator = creator;
    }
}
