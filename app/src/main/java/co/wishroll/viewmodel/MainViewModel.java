package co.wishroll.viewmodel;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.local.SessionManagement;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    UserRepository userRepository = applicationGraph.userRepository();
    boolean isDeleted = false;



    public MainViewModel() {
    }



    @BindingAdapter("profileImage")
    public static void loadProfileImage(CircularImageView view, String imageUrl) {
        Log.d(TAG, "loadProfileImage: loading trending page profile picture");
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.defaultprofile)
                .into(view);
    }


}