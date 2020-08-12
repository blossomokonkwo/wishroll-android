package co.wishroll.models.repository.data;


import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Date;

import co.wishroll.models.datamodels.User;

public class LoginResponse {
    //maps the data given by a login POST request

    @Expose
    @SerializedName("user")
    private User user;


    @Expose
    @SerializedName("id")
    private String user_id;

    @Expose
    @SerializedName("username")
    private String username;

    @Nullable
    @Expose
    @SerializedName("bio")
    private String bio;

    @Nullable
    @Expose
    @SerializedName("profile_background_url")
    private String profile_background_url;

    @Expose
    @SerializedName("verified")
    private boolean verified;

    @Expose
    @SerializedName("email")
    private String email;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("avatar")
    private String avatar;













}




