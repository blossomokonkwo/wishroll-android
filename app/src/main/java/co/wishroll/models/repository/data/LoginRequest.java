package co.wishroll.models.repository.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequest {

    @SerializedName("access")
    @Expose
    String accessCredential;

    @SerializedName("password")
    @Expose
    String password;


    public LoginRequest(String accessCredential, String password) {
        this.accessCredential = accessCredential;
        this.password = password;
    }

    public String getAccessCredential() {
        return accessCredential;
    }

    public void setAccessCredential(String accessCredential) {
        this.accessCredential = accessCredential;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
