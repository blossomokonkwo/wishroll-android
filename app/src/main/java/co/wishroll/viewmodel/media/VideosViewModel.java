package co.wishroll.viewmodel.media;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class VideosViewModel extends ViewModel {
    private static final String TAG = "VideosViewModel";
    PostRepository postRepository = applicationGraph.postRepository();
    MediatorLiveData<StateData<ArrayList<Post>>> videoListLiveData = new MediatorLiveData<>();
    public int postId;
    int START_OFFSET = 0;
    public static int offset = 0;
    public int dataSetSize = 0;

    public int getDataSetSize() {
        return dataSetSize;
    }

    public void setDataSetSize(int dataSetSize) {
        this.dataSetSize = dataSetSize;
    }

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset){ VideosViewModel.offset = offset;
    }

    public VideosViewModel() {

    }

    public VideosViewModel(int postId) {
        this.postId = postId;
        Log.d(TAG, "VideosViewModel: we in here the post id for this is: " + postId);
        getFirstVideoList();
    }

    public LiveData<StateData<ArrayList<Post>>> observeVideosList(){
        return videoListLiveData;
    }

    public void getFirstVideoList(){
        videoListLiveData.setValue(StateData.loading((ArrayList<Post>) null));
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getVideosListPosts(postId, START_OFFSET);
        videoListLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> videoListStateData) {

                if(videoListStateData.data != null){
                    setDataSetSize(videoListStateData.data.size());
                }else{
                    setDataSetSize(15);
                }

                videoListLiveData.setValue(videoListStateData);
                videoListLiveData.removeSource(source);
            }
        });
    }

    public void getMoreVideos(){
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getVideosListPosts(postId, offset);
        videoListLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> videoListStateData) {

                videoListLiveData.setValue(videoListStateData);
                videoListLiveData.removeSource(source);
            }
        });
    }








   /* public void toggleBookmarking(boolean bookmarking){
        Disposable bookmarkStatus = postRepository.toggleBookmark(postId, bookmarking)
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.d(TAG, "onError: this couldn't be bookmarkd. you wanted to bookmark, true or false? " + bookmarking);
                        getPost(postId);
                        error.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                        getPost(postId);
                        Log.d(TAG, "onComplete: this completed, whatever you did here did you bookmark it? " + bookmarking);
                        dispose();

                    }
                });
    }
*/

    /*public void getPost(int postId){
        bookmarkStatusLiveData.setValue(StateData.loading((Post) null));
        final LiveData<StateData<Post>> source = postRepository.getPost(postId);
        videoListLiveData.addSource(source, new Observer<StateData<Post>>() {
            @Override
            public void onChanged(StateData<Post> postStateData) {
                bookmarkStatusLiveData.setValue(postStateData);
                bookmarkStatusLiveData.removeSource(source);
            }
        });
    }*/



    //ViewModel for each Post, whether video, image or gif.
    //mostly for image/video view activities

}
