package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.SerializedName;


public class SignupRequestMany {


    @SerializedName("password")
     String password;

    @SerializedName("email")
     String email;



    public SignupRequestMany() {
    }


    public SignupRequestMany( String email, String password) {
        super();
        this.password = password;
        this.email = email;

    }


    public  String getPassword() {
        return password;
    }

    public void setPassword(String passwords) {
        password = passwords;
    }

    public  String getEmail() {
        return email;
    }

    public void setEmail(String emails) {
        email = emails;
    }




}