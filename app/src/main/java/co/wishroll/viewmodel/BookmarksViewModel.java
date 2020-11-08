package co.wishroll.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class BookmarksViewModel  extends ViewModel {

    int userId;
    PostRepository postRepository = applicationGraph.postRepository();
    MediatorLiveData<StateData<ArrayList<Post>>> listOfBookmarks = new MediatorLiveData<>();
    final int START_OFFSET = 0;
    int offset = 0;
    int bookmarksDataSetSize = 0;

    public BookmarksViewModel(int userId) {
        this.userId = userId;
        getFirstPageOfBookmarks();

    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getBookmarksDataSetSize() {
        return bookmarksDataSetSize;
    }

    public void setBookmarksDataSetSize(int bookmarksDataSetSize) {
        this.bookmarksDataSetSize = bookmarksDataSetSize;
    }


    public void getFirstPageOfBookmarks() {
        listOfBookmarks.setValue(StateData.loading((ArrayList<Post>) null));
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getBookmarkedPosts(userId, START_OFFSET);
        listOfBookmarks.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                if(arrayListStateData.data != null){
                    setBookmarksDataSetSize(arrayListStateData.data.size());
                }else{
                    setBookmarksDataSetSize(18);
                }

                listOfBookmarks.setValue(arrayListStateData);
                listOfBookmarks.removeSource(source);
            }

        });

    }

    public void getMoreBookmarked(int offsetie){
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getBookmarkedPosts(userId, offsetie);
        listOfBookmarks.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {


                listOfBookmarks.setValue(arrayListStateData);
                listOfBookmarks.removeSource(source);
            }

        });

    }

    public LiveData<StateData<ArrayList<Post>>> observeBookmarks(){
        return listOfBookmarks;
    }

}
