package co.wishroll.viewmodel;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.UserRepository;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class OtherProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    UserRepository userRepository = applicationGraph.userRepository();
    MediatorLiveData<StateData<User>> userLiveData = new MediatorLiveData<>();
    int pointerId;

    @BindingAdapter("otherProfileViewImage")
    public static void loadOtherProfileViewImage(CircularImageView view, String imageUrl) {
        Log.d(TAG, "loadProfileImage: binding adapter lolol XDXDXD ");
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.defaultprofile)
                .into(view);
    }

    @BindingAdapter("otherBannerViewImage")
    public static void loadOtherBannerViewImage(ImageView view, String bannerImageUrl) {
        Log.d(TAG, "loadProfileImage: binding adapter lolol XDXDXD ");
        Glide.with(view.getContext())
                .load(bannerImageUrl).apply(new RequestOptions().centerCrop()).placeholder(R.color.wishroll_blue)
                .into(view);
    }



    public OtherProfileViewModel(int id){
        this.pointerId = id;
        Log.d(TAG, "OtherProfileViewModel: initialized current user username " + id);
        getOtherUser(pointerId);



    }



    public void getOtherUser(int userId){
        userLiveData.setValue(StateData.loading((User)null));
        final LiveData<StateData<User>> source = userRepository.getUserById(userId);

        userLiveData.addSource(source, new Observer<StateData<User>>() {
            @Override
            public void onChanged(StateData<User> userStateData) {
                userLiveData.setValue(userStateData);
                userLiveData.removeSource(source);
            }
        });
    }


    public LiveData<StateData<User>> observeOtherUserProfile(){
        return userLiveData;
    }









}
