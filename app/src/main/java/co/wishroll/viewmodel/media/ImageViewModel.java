package co.wishroll.viewmodel.media;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;

import co.wishroll.R;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.StateData;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ImageViewModel extends ViewModel {
    PostRepository postRepository = applicationGraph.postRepository();
    MediatorLiveData<StateData<Post>> postLiveData = new MediatorLiveData<>();
    private static final String TAG = "ImageViewModel";
    int postId;



    @BindingAdapter("mainViewImage")
    public static void loadProfileViewImage(ImageView view, String imageUrl) {


        Glide.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.color.white)
                .into(view);
    }

    public ImageViewModel() {

    }

    public ImageViewModel(int postId) {
        this.postId = postId;
        getPost(postId);
    }

    public LiveData<StateData<Post>> observeThisPost(){
        return postLiveData;
    }

    public void getPost(int postId){
        postLiveData.setValue(StateData.loading((Post) null));
        final LiveData<StateData<Post>> source = postRepository.getPost(postId);
        postLiveData.addSource(source, new Observer<StateData<Post>>() {
            @Override
            public void onChanged(StateData<Post> postStateData) {

                postLiveData.setValue(postStateData);
                postLiveData.removeSource(source);
            }
        });
    }

    public void toggleBookmarking(boolean bookmarking){
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






}
