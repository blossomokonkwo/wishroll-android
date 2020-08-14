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











}




