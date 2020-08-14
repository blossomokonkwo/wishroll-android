package co.wishroll.models.repository.datamodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    //maps the data given by a login POST request

    @SerializedName("user")
    @Expose
    private UserModel userModel;

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
     * @param userModel
     */
    public LoginResponse(UserModel userModel, AccessToken accessToken) {
        this.userModel = userModel;
        this.accessToken = accessToken;
    }

   

    public UserModel getUser() {
        return userModel;
    }

    public void setUser(UserModel userModel) {
        this.userModel = userModel;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
















}




