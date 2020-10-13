package co.wishroll.viewmodel.media;

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
        //postLiveData.setValue(StateData.loading((Post) null));
        final LiveData<StateData<Post>> source = postRepository.getPost(postId);
        postLiveData.addSource(source, new Observer<StateData<Post>>() {
            @Override
            public void onChanged(StateData<Post> postStateData) {

                postLiveData.setValue(postStateData);
                postLiveData.removeSource(source);
            }
        });
    }









}
