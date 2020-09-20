package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadPostResponse {

    @SerializedName("post_id")
    @Expose
    private int postId;


    public UploadPostResponse() {
    }


    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
