package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.SerializedName;

public class SignupRequest {

    @SerializedName("name")
    String name;

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    @SerializedName("email")
    String email;

    @SerializedName("birth_date")
    String birthday;

    public SignupRequest() {
    }

    public SignupRequest(String name, String username, String password, String email, String birthday) {
        super();
        this.name = name;
        this.username = formatUsername(username);
        this.password = password;
        this.email = email;
        this.birthday = birthday;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String formatUsername(String username) {
        return username.toLowerCase().replace(' ', '_');
    }
}