package co.wishroll.models.domainmodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post implements Parcelable {

    //TODO(Remove Comment Count)

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("viewed")
    @Expose
    private Boolean viewed;

    @SerializedName("bookmarked")
    @Expose
    private Boolean bookmarked;

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
    public Post(Integer id, Boolean viewed, Boolean bookmarked, String mediaUrl, String thumbnailUrl, PostCreator creator) {
        super();
        this.id = id;
        this.viewed = viewed;
        this.bookmarked = bookmarked;
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

    public Boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        this.bookmarked = bookmarked;
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
        parcel.writeString(mediaUrl);
        parcel.writeString(thumbnailUrl);
        parcel.writeParcelable(creator, i);
    }
}
