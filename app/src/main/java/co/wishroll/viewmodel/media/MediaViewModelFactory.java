package co.wishroll.viewmodel.media;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MediaViewModelFactory implements ViewModelProvider.Factory {

    private int postId;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MoreLikeThisViewModel.class)){
            return (T) new MoreLikeThisViewModel(postId);

        }else if (modelClass.isAssignableFrom(VideosViewModel.class)){
            return (T) new VideosViewModel(postId);

        }else if(modelClass.isAssignableFrom(ImageViewModel.class)){
            return (T) new ImageViewModel(postId);

        }else{
            return null;

        }
    }


    public MediaViewModelFactory(int postId) {
        this.postId = postId;

    }


}
