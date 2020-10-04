package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import java.io.File;

import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.AuthListener;
import co.wishroll.utilities.FileUtils;
import co.wishroll.utilities.ThumbnailHandler;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static co.wishroll.WishRollApplication.applicationGraph;

public class TaggingViewModel extends ViewModel {

    private static final String TAG = "TaggingViewModel";
    PostRepository postRepository = applicationGraph.postRepository();
    public AuthListener authListener = null;

    public String postTags;




    public String postPath = "";
    public String videoThumbnailPath = "";

    RequestBody postRequestBody;
    RequestBody videoThumbnailRequestBody;
    MultipartBody.Part postMultipart;
    MultipartBody.Part videoThumbnail;

    File videoThumbnailFile;

    boolean isVideo = false;

    //send url request bodies to server when post button is pressed
    //turns object into multipart, returns an observable that is observed by tagging activity, with state

    public TaggingViewModel() {

    }

    public String getVideoThumbnailPath() {
        return videoThumbnailPath;
    }

    public void setVideoThumbnailPath(String videoThumbnailPath) {
        this.videoThumbnailPath = videoThumbnailPath;
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

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public void onSharePressed(){


        if(postTags == null){
            authListener.sendMessage("Each post must have tags");
        }else{


             File postFile = new File(postPath);
             postRequestBody = RequestBody.create(postFile, MediaType.parse(FileUtils.getMimeType(postFile)));
             postMultipart = MultipartBody.Part.createFormData("media_item", postFile.getName(), postRequestBody);
             String[] tags = {postTags};



             if(isVideo) {

                 videoThumbnailFile = new File(videoThumbnailPath);
                 videoThumbnailRequestBody = RequestBody.create(videoThumbnailFile, MediaType.parse(FileUtils.getMimeType(videoThumbnailFile)));
                 videoThumbnail = MultipartBody.Part.createFormData("thumbnail_item", videoThumbnailFile.getName(), videoThumbnailRequestBody);


             }

             if(isVideo){

                 uploadPost(postMultipart, videoThumbnail, tags, true);

             }else {
                 uploadPost(postMultipart,  null, tags, false);

             }










        }
   }






    public void uploadPost(MultipartBody.Part mediaPost, MultipartBody.Part videoThumbnail, String[] tags, boolean isVideo){

        Disposable uploadDisposable = postRepository.uploadPost(mediaPost, videoThumbnail, tags, isVideo)
                .subscribeWith(new DisposableCompletableObserver(){
                    @Override
                    public void onStart() {
                        authListener.statusGetter(100);
                    }

                    @Override
                    public void onError(Throwable error) {
                        authListener.statusGetter(400);

                        error.printStackTrace();

                    }

                    @Override
                    public void onComplete() {
                        authListener.statusGetter(200);
                        ThumbnailHandler.clearApplicationData();
                        dispose();

                    }
                });







   }




}
