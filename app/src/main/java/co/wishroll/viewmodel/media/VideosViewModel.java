package co.wishroll.viewmodel.media;

import androidx.lifecycle.ViewModel;

public class VideosViewModel extends ViewModel {
    public int postId;

    public VideosViewModel() {

    }

    public VideosViewModel(int postId) {
        this.postId = postId;
    }

    //ViewModel for each Post, whether video, image or gif.
    //mostly for image/video view activities

}
