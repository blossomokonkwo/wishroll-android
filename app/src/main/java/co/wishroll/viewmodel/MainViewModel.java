package co.wishroll.viewmodel;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.models.repository.MainRepository;
import co.wishroll.models.repository.local.SessionManagement;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    MainRepository mainRepository = applicationGraph.mainRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();



        @BindingAdapter("profileImage")
        public static void loadProfileImage(CircularImageView view, String imageUrl) {
            Log.d(TAG, "loadProfileImage: binding adapter lolol XDXDXD ");
            Glide.with(view.getContext())
                    .load(imageUrl).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.defaultprofile)
                    .into(view);
        }






}




