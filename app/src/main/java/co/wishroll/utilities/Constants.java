package co.wishroll.utilities;

public class Constants {
    //stores values that do not change application-wide


    final static String API_URL = "\"https://wishroll-testing.herokuapp.com\"";
    //const val API_URL = "http://10.0.2.2:3000/"

    public Constants() {

        String API_URL = "https://sample-node-social-app.herokuapp.com/";
        //const val API_URL = "http://10.0.2.2:3000/"




    }

    public static String getAPI_URL() {
        return API_URL;
    }
}
