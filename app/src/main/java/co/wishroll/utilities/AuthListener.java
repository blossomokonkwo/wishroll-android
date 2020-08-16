package co.wishroll.utilities;

public interface AuthListener {

    void onStarted();

    void onSuccess();

    void onFailure(String message);

    void sendMessage(String message);

    void errorMessage(String message);


}
