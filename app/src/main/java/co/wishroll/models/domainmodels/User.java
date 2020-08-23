package co.wishroll.models.domainmodels;


import androidx.annotation.Nullable;

import java.util.Date;

public class User {
    //for viewage and representation on views


    private String username;

    @Nullable
    private Integer numFollowers;

    @Nullable
    private Integer numFollowing;

    @Nullable
    private Integer viewCount;

    @Nullable
    private String avatar;


    private int id;

    @Nullable
    private String profileBackgroundUrl;


    private boolean verified;

    @Nullable
    private boolean following;

    @Nullable
    private String name;

    @Nullable
    private String bio = "";

    @Nullable
    private Integer wishrollScore;

    @Nullable
    private Date createdAt;


    public User(String username, int id, boolean verified) {
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

    @Nullable
    public Integer getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(@Nullable Integer numFollowers) {
        this.numFollowers = numFollowers;
    }

    @Nullable
    public Integer getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(@Nullable Integer numFollowing) {
        this.numFollowing = numFollowing;
    }

    @Nullable
    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(@Nullable Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@Nullable String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getProfileBackgroundUrl() {
        return profileBackgroundUrl;
    }

    public void setProfileBackgroundUrl(@Nullable String profileBackgroundUrl) {
        this.profileBackgroundUrl = profileBackgroundUrl;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getBio() {
        return bio;
    }

    public void setBio(@Nullable String bio) {
        this.bio = bio;
    }

    @Nullable
    public Integer getWishrollScore() {
        return wishrollScore;
    }

    public void setWishrollScore(@Nullable Integer wishrollScore) {
        this.wishrollScore = wishrollScore;
    }

    @Nullable
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable Date createdAt) {
        this.createdAt = createdAt;
    }
}




