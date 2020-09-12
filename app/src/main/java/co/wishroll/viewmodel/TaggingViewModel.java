package co.wishroll.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.File;

import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static co.wishroll.WishRollApplication.applicationGraph;

public class TaggingViewModel extends ViewModel {

    private static final String TAG = "TaggingViewModel";
    boolean shareStatus;
    PostRepository postRepository = applicationGraph.postRepository();
    public String postTags;
    public String postCaption;
    public AuthListener authListener = null;
    RequestBody tags;
    RequestBody caption;
    public String postPath = "";
    public String videoThumbnailPath = "";
    RequestBody videoThumbnailRequestbody;
    RequestBody postRequestBody;
    MultipartBody.Part postMultipart;
    MultipartBody.Part videoThumbnail;
    boolean isVideo = false;

    //send url request bodies to server when post button is pressed
    //turns object into multipart, returns an observable that is observed by tagging activity, with state

    public TaggingViewModel() {

    }

    public String getPostPath() {
        return postPath;
    }

    public void setPostPath(String postPath) {
        this.postPath = postPath;
    }

    public RequestBody createPartFromString(String entryString){
            if(entryString == null || entryString.equals("")){
                return null;
            }else{
                return RequestBody.create( entryString, MultipartBody.FORM );
            }
    }


   public void onSharePressed(){
       Log.d(TAG, "onSharePressed: this is the caption that the user entered " + postCaption);
       Log.d(TAG, "onSharePressed: these are the tags that the user entered " + postTags);
       Log.d(TAG, "onSharePressed: this is the file path of the chosen media " + postPath);
        if(postTags == null){
            authListener.sendMessage("Each post must have tags");
        }else{
             caption = createPartFromString(postCaption);

             File postFile = new File(postPath);
             File videoThumbnailFile = new File(videoThumbnailPath);

             postRequestBody = RequestBody.create(postFile, MediaType.parse(FileUtils.getMimeType(postFile)));
             postMultipart = MultipartBody.Part.createFormData("media_item", postFile.getName(), postRequestBody);

             videoThumbnailRequestbody = RequestBody.create(videoThumbnailFile, MediaType.parse(FileUtils.getMimeType(videoThumbnailFile)));
             videoThumbnail = MultipartBody.Part.createFormData("thumbnail_item", videoThumbnailFile.getName(), videoThumbnailRequestbody);







        }
   }


   public void uploadPost(MultipartBody.Part mediaPost, MultipartBody.Part videoThumbnail, RequestBody caption, String tags, boolean isVideo){
        postRepository.uploadPost(mediaPost, videoThumbnail, caption, tags, isVideo);
   }



}
