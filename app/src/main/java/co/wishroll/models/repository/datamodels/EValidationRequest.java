package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EValidationRequest {

    @SerializedName("email")
    @Expose
    public String email;

    public EValidationRequest() {
    }

    public EValidationRequest(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
