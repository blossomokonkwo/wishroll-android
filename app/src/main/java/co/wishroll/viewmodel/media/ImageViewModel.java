package co.wishroll.viewmodel.media;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;

import co.wishroll.R;

public class ImageViewModel extends ViewModel {
    private static final String TAG = "ImageViewModel";

    @BindingAdapter("mainViewImage")
    public static void loadProfileViewImage(ImageView view, String imageUrl) {
        Log.d(TAG, "loadProfileImage: binding adapter for imageViewing ");
        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.color.white)
                .into(view);
    }

    public ImageViewModel() {

    }

    //ViewModel for each Post, whether video, image or gif.
    //mostly for image/video view activities

}
