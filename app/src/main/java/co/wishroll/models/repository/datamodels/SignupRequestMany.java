package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.SerializedName;


public class SignupRequestMany {


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


    public SignupRequestMany() {
    }

    public SignupRequestMany(String email, String password) {
    }


    public SignupRequestMany(String name, String username, String password, String email, String birthday) {
        super();
        this.name = name;
        this.username = formatUsername(username);
        this.password = password;
        this.email = email;
        this.birthday = birthday;
    }

    public SignupRequestMany( String username, String password, String email, String birthday) {
        super();
        this.name = name;
        this.username = formatUsername(username);
        this.password = password;
        this.email = email;
        this.birthday = birthday;
    }


    public  String getName() {
        return name;
    }

    public void setName(String names) {
        name = names;
    }

    public  String getUsername() {
        return username;
    }

    public  void setUsername(String usernames) {
        username = usernames;
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

    public String getBirthday() {
        return birthday;
    }

    public  void setBirthday(String birthdays) {
        birthday = birthdays;
    }

    public String formatUsername(String username) {
        return username.toLowerCase().replace(' ', '_');
    }



}