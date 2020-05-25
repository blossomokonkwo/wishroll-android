package co.WishRoll.Utils;

import co.WishRoll.Models.User;

public interface GetUserCallback {
     //allows us to inform the activity which performs a server request when the request is completed
    //tells the activity which method will be called when the server request is completed

    public abstract void done(User returnedUser);

}
