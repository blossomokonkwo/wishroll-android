package co.wishroll.models.repository.datamodels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    private static final String TAG = "AuthResponse";
    //maps the data given by a login POST request

    @SerializedName("user")
    @Expose
    private UserModel userModel;

    @SerializedName("access_token")
    @Expose
    private AccessToken accessToken;

    public AuthResponse() {
    }

    public AuthResponse(UserModel userModel, AccessToken accessToken) {
        this.userModel = userModel;
        this.accessToken = accessToken;
    }

   

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }
















}




