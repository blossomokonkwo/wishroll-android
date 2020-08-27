package co.wishroll.models.repository.datamodels;

import com.google.gson.annotations.SerializedName;


public class SignupRequest {

    static SignupRequest signupRequest;


    @SerializedName("name")
    static String name;

    @SerializedName("username")
    static String username;

    @SerializedName("password")
    static String password;

    @SerializedName("email")
    static String email;

    @SerializedName("birth_date")
    static String birthday;



    public static SignupRequest getInstance()
    {
        if (signupRequest == null)
            signupRequest = new SignupRequest();

        return signupRequest;
    }

    public SignupRequest() {
    }


    /*public SignupRequest(String name, String username, String password, String email, String birthday) {
        super();
        this.name = name;
        this.username = formatUsername(username);
        this.password = password;
        this.email = email;
        this.birthday = birthday;
    }*/


    public static String getName() {
        return name;
    }

    public static void setName(String names) {
        name = names;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String usernames) {
        username = usernames;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String passwords) {
        password = passwords;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String emails) {
        email = emails;
    }

    public static String getBirthday() {
        return birthday;
    }

    public static void setBirthday(String birthdays) {
        birthday = birthdays;
    }

    public static String formatUsername(String username) {
        return username.toLowerCase().replace(' ', '_');
    }
}