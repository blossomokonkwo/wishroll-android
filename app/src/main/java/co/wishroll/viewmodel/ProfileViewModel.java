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

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";
    UserRepository userRepository = applicationGraph.userRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    MediatorLiveData<StateData<User>> userLiveData = new MediatorLiveData<>();
    int pointerId;

    @BindingAdapter("profileViewImage")
    public static void loadProfileViewImage(CircularImageView view, String imageUrl) {
        Log.d(TAG, "loadProfileImage: binding adapter lolol XDXDXD ");
        Glide.with(view.getContext())
                .load(imageUrl).apply(new RequestOptions().circleCrop())
                .into(view);
    }

    @BindingAdapter("bannerViewImage")
    public static void loadBannerViewImage(ImageView view, String bannerImageUrl) {
        Log.d(TAG, "loadProfileImage: binding adapter lolol XDXDXD ");
        Glide.with(view.getContext())
                .load(bannerImageUrl).apply(new RequestOptions().centerCrop())
                .into(view);
    }



    public ProfileViewModel(int id){
        this.pointerId = id;
        Log.d(TAG, "ProfileViewModel: initialized current user username " + pointerId);
        getCurrentUser(pointerId);



    }



    public void getCurrentUser(int id){
        userLiveData.setValue(StateData.loading((User)null));
        final LiveData<StateData<User>> source = userRepository.getUserById(id);
        userLiveData.addSource(source, new Observer<StateData<User>>() {
            @Override
            public void onChanged(StateData<User> userStateData) {
                userLiveData.setValue(userStateData);
                userLiveData.removeSource(source);
            }
        });




    }
    public LiveData<StateData<User>> observeCurrentUserProfile(){
        return userLiveData;
    }


    //do this when the activity is created






}
