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

public class LikesViewModel extends ViewModel {
    int userId;
    PostRepository postRepository = applicationGraph.postRepository();
    MediatorLiveData<StateData<ArrayList<Post>>> listOfLikes= new MediatorLiveData<>();
    final int START_OFFSET = 0;
    int offset = 0;
    int likesDataSetSize = 0;

    public LikesViewModel(int userId) {
        this.userId = userId;
        getFirstPageOfLikes();

    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLikesDataSetSize() {
        return likesDataSetSize;
    }

    public void setLikesDataSetSize(int likesDataSetSize) {
        this.likesDataSetSize = likesDataSetSize;
    }


    public void getFirstPageOfLikes() {
        listOfLikes.setValue(StateData.loading((ArrayList<Post>) null));
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getLikedPosts(userId, START_OFFSET);
        listOfLikes.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                if(arrayListStateData.data != null){
                    setLikesDataSetSize(arrayListStateData.data.size());
                }else{
                    setLikesDataSetSize(18);
                }

                listOfLikes.setValue(arrayListStateData);
                listOfLikes.removeSource(source);
            }

        });

    }

    public void getMoreLiked(int offsetie){
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getLikedPosts(userId, offsetie);
        listOfLikes.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {

                listOfLikes.setValue(arrayListStateData);
                listOfLikes.removeSource(source);
            }

        });
    }

    public LiveData<StateData<ArrayList<Post>>> observeLikes(){
        return listOfLikes;
    }
}
