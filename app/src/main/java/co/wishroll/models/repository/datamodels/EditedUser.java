package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditedUser {

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("email")
    @Expose
    public String email;

    @SerializedName("bio")
    @Expose
    public String bio;

    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;

    @SerializedName("profile_background_url")
    @Expose
    public String backgroundUrl;

    public EditedUser() {
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }
}
