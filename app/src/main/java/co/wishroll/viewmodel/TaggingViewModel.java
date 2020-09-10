package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.StateData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static co.wishroll.WishRollApplication.applicationGraph;

public class TaggingViewModel extends ViewModel {

    private static final String TAG = "TaggingViewModel";
    MediatorLiveData<StateData<ResponseBody>> shareStatus = new MediatorLiveData<>();
    PostRepository postRepository = applicationGraph.postRepository();
    public String postTags;
    public String postCaption;

    //send url request bodies to server when post button is pressed
    //turns object into multipart, returns an observable that is observed by tagging activity, with state

    public TaggingViewModel() {

    }



    public RequestBody createPartFromString(String entryString){

        return RequestBody.create( entryString, MultipartBody.FORM );
    }


   public void onSharePressed(){
       Log.d(TAG, "onSharePressed: this is the caption that the user entered " + postCaption);
       Log.d(TAG, "onSharePressed: these are the tags that the user entered " + postTags);


   }

   public LiveData<StateData<ResponseBody>> observeShareStatus(){
        return shareStatus;
   }

   public void uploadPost(MultipartBody.Part post, RequestBody tags, RequestBody caption){

   }

}
