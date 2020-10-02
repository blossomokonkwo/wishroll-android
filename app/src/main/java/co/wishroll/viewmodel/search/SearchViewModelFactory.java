package co.wishroll.viewmodel.search;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.views.search.GifSearchFragment;
import co.wishroll.views.search.ImageSearchFragment;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private String searchQuery;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(GifSearchFragment.class)) {

            return (T) new GifSearchViewModel(searchQuery);

        }else if(modelClass.isAssignableFrom(ImageSearchFragment.class)){

            return (T) new ImageSearchViewModel(searchQuery);

        }else {
            return (T) new VideoSearchViewModel(searchQuery);
        }

    }

    public SearchViewModelFactory() {
    }

    public SearchViewModelFactory(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
