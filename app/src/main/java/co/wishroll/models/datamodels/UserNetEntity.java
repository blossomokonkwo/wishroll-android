package co.wishroll.models.datamodels;


import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Date;

public class UserNetEntity {
    //used by Retrofit to map data from JSON to objects


    @Expose
    @SerializedName("")
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


    public UserNetEntity(String username, int id, boolean verified) {
        this.username = username;
        this.id = id;
        this.verified = verified;
    }


}




