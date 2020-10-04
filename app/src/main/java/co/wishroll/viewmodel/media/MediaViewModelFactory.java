package co.wishroll.viewmodel.media;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.viewmodel.PostsGridViewModel;

public class MediaViewModelFactory implements ViewModelProvider.Factory {

    private int postId;
    private int trendingTagId;
    private boolean isBookmarkingQuery;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MoreLikeThisViewModel.class)){
            return (T) new MoreLikeThisViewModel(postId);

        }else if (modelClass.isAssignableFrom(VideosViewModel.class)){
            return (T) new VideosViewModel(postId);

        }else if(modelClass.isAssignableFrom(ImageViewModel.class)) {
            return (T) new ImageViewModel(postId);

        }else if(modelClass.isAssignableFrom(PostsGridViewModel.class)){
                return (T) new PostsGridViewModel(trendingTagId, isBookmarkingQuery);
        }else{
            return null;

        }
    }


    public MediaViewModelFactory(int postId) {
        this.postId = postId;

    }


    public MediaViewModelFactory(int trendingTagId, boolean isBookmarkingQuery ){
        this.trendingTagId = trendingTagId;
        this.isBookmarkingQuery = isBookmarkingQuery;
    }

}
