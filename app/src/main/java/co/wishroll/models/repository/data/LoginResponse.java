package co.wishroll.models.repository.data;


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

    @SerializedName("error")
    @Expose
    private String errorMessage;

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
        super();
        this.userModel = userModel;
        this.accessToken = accessToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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




