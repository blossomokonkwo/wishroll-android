package co.wishroll.models.repository.data;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.wishroll.models.datamodels.User;

public class LoginResponse {
    //maps the data given by a login POST request

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("access_token")
    @Expose
    private AccessToken accessToken;

    /**
     * No args constructor for use in serialization
     */
    public LoginResponse() {
    }

    /**
     * @param accessToken
     * @param user
     */
    public LoginResponse(User user, AccessToken accessToken) {
        super();
        this.user = user;
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
















}




