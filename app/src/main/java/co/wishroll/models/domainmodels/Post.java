package co.wishroll.models.domainmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {
    //TODO(Remove Comment Count)

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("viewed")
    @Expose
    private Boolean viewed;

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
    public Post(Integer id, Boolean viewed, String mediaUrl, String thumbnailUrl, Creator creator) {
        super();
        this.id = id;
        this.viewed = viewed;
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

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }
}
