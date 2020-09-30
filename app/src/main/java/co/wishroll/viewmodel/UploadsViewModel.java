package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class UploadsViewModel extends ViewModel {
    private static final String TAG = "UploadsViewModel";
    int userId;



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UploadsViewModel(int id) {
        this.userId = id;
        Log.d(TAG, "UploadsViewModel: this is the user's id " + userId);
    }


}
