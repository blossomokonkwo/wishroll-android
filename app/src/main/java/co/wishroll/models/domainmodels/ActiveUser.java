package co.wishroll.models.domainmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActiveUser {


    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("avatar")
    @Expose
    private String avatar;

    @SerializedName("verified")
    @Expose
    private Boolean verified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ActiveUser withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ActiveUser withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ActiveUser withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public ActiveUser withVerified(Boolean verified) {
        this.verified = verified;
        return this;
    }
}
