package co.wishroll.models.domainmodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("num_followers")
    @Expose
    public Integer numFollowers;

    @SerializedName("num_following")
    @Expose
    public Integer numFollowing;

    @SerializedName("view_count")
    @Expose
    public Integer viewCount;

    @SerializedName("avatar")
    @Expose
    public String avatar;

    @SerializedName("profile_background_url")
    @Expose
    public String profileBackgroundUrl;


    @SerializedName("verified")
    @Expose
    private boolean verified;

    @SerializedName("following")
    @Expose
    public Boolean following;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("bio")
    @Expose
    public String bio;

    @SerializedName("wishroll_score")
    @Expose
    public Integer wishrollScore;

    @SerializedName("created_at")
    @Expose
    public String createdAt;


    public User(){

    }

    public User(String username, int id, Boolean verified) {
        this.username = username;
        this.id = id;
        this.verified = verified;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public Integer getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(Integer numFollowers) {
        this.numFollowers = numFollowers;
    }

    public Integer getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(Integer numFollowing) {
        this.numFollowing = numFollowing;
    }


    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getProfileBackgroundUrl() {
        return profileBackgroundUrl;
    }

    public void setProfileBackgroundUrl(String profileBackgroundUrl) {
        this.profileBackgroundUrl = profileBackgroundUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Boolean isFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getWishrollScore() {
        return wishrollScore;
    }

    public void setWishrollScore(Integer wishrollScore) {
        this.wishrollScore = wishrollScore;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}




