package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateResponse {

    @SerializedName("current_user")
    @Expose
    public EditedUser currentUser;


    public EditedUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(EditedUser currentUser) {
        this.currentUser = currentUser;
    }
}
