package co.wishroll.models.domainmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Creator {
    //TODO(remove following)

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("verified")
    @Expose
    private Boolean verified;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("following")
    @Expose
    private Boolean following;

    /**
     * No args constructor for use in serialization
     *
     */
    public Creator() {
    }

    /**
     *
     *
     * @param following
     * @param verified
     * @param id
     * @param avatar
     * @param username
     */
    public Creator(Integer id, String username, Boolean verified, String avatar, Boolean following) {
        super();
        this.id = id;
        this.username = username;
        this.verified = verified;
        this.avatar = avatar;
        this.following = following;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }


}
