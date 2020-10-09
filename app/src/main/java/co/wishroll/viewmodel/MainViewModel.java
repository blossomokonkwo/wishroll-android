package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.local.SessionManagement;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    UserRepository userRepository = applicationGraph.userRepository();
    boolean isDeleted = false;



    public MainViewModel() {
    }


    public boolean deleteAccount(){
        Disposable bookmarkStatus = userRepository.deleteThisUser()
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "was not deleted, something is wrong");
                        isDeleted = false;
                        error.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                        isDeleted = true;
                        Log.d(TAG, "ACCOUNT DELETED");
                        dispose();

                    }
                });

        return isDeleted;
    }
}




