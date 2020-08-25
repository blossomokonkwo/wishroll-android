package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UValidationRequest {

    @SerializedName("username")
    @Expose
    public String username;

    public UValidationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
