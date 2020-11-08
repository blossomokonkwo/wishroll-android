package co.wishroll.models.domainmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserNotification {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("phrase")
    @Expose
    private String phrase;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("post")
    @Expose
    private Post post;
    @SerializedName("active_user")
    @Expose
    private ActiveUser activeUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserNotification withId(Integer id) {
        this.id = id;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public UserNotification withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public UserNotification withPhrase(String phrase) {
        this.phrase = phrase;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserNotification withType(String type) {
        this.type = type;
        return this;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public UserNotification withPost(Post post) {
        this.post = post;
        return this;
    }

    public ActiveUser getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
    }

    public UserNotification withActiveUser(ActiveUser activeUser) {
        this.activeUser = activeUser;
        return this;
    }

}
