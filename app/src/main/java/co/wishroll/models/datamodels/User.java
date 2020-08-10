package co.wishroll.models.datamodels;


import androidx.annotation.Nullable;

import java.net.URI;
import java.net.URL;
import java.util.Date;

public class User {


    private String username;

    @Nullable
    private int numFollowers;

    @Nullable
    private int numFollowing;

    @Nullable
    private int viewCount;

    @Nullable
    private URL avatar;

    private int id;

    @Nullable
    private URL profileBackgroundUrl;


    private boolean verified;

    @Nullable
    private boolean following;

    @Nullable
    private String name;

    @Nullable
    private String bio;

    @Nullable
    private int wishrollScore;

    @Nullable
    private Date createdAt;


    public User(String username, int id, boolean verified) {
        this.username = username;
        this.id = id;
        this.verified = verified;
    }


}




