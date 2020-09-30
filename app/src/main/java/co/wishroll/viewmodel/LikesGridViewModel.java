package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class LikesGridViewModel extends ViewModel {
    private static final String TAG = "LikesGridViewModel";
    int userId;

    public LikesGridViewModel(int id) {
        this.userId = id;
        Log.d(TAG, "LikesGridViewModel: this belongs to the user with the id of " + userId);

    }

    //ViewModel for the Likes Fragment of the Profile View
    //Communicates with Repository to fetch the list of posts that User has liked


}
