package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class UserModel {
    //model for the response data gotten from the WishRoll Api

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("username")
    @Expose
    private String username;


    @SerializedName("bio")
    @Expose
    private String bio;

    @SerializedName("profile_background_url")
    @Expose
    private URL profileBackgroundUrl;

    @SerializedName("verified")
    @Expose
    private Boolean verified;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("avatar")
    @Expose
    private URL avatar;



    /**
     *
     * @param createdAt
     * @param verified
     * @param name
     * @param bio
     * @param id
     * @param profileBackgroundUrl
     * @param avatar
     * @param email
     * @param username
     */

    public UserModel(){

    }

    public UserModel(String username, int id, boolean verified){
        super();
        this.id = id;
        this.username = username;
        this.verified = verified;

    }
    public UserModel(int id, String username, String bio, URL profileBackgroundUrl, Boolean verified, String email, String name, String createdAt, URL avatar) {
        super();
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.profileBackgroundUrl = profileBackgroundUrl;
        this.verified = verified;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;
        this.avatar = avatar;
    }

    public UserModel(int id, String username, String bio, Boolean verified, String email, String name, String createdAt) {
        super();
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.verified = verified;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;

    }

    public UserModel(int id, String username, Boolean verified, String email, String name, String createdAt) {
        super();
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.verified = verified;
        this.email = email;
        this.name = name;
        this.createdAt = createdAt;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public URL getProfileBackgroundUrl() {
        return profileBackgroundUrl;
    }

    public void setProfileBackgroundUrl(URL profileBackgroundUrl) {
        this.profileBackgroundUrl = profileBackgroundUrl;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public URL getAvatar() {
        return avatar;
    }

    public void setAvatar(URL avatar) {
        this.avatar = avatar;
    }

}


