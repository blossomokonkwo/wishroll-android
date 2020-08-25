package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValidationResponse {

    @SerializedName("error")
    @Expose
    public String errorMessage;

    public ValidationResponse() {
    }

    public ValidationResponse(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
