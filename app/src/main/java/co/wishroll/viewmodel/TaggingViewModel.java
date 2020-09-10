package co.wishroll.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import co.wishroll.utilities.StateData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class TaggingViewModel extends ViewModel {
    MediatorLiveData<StateData<ResponseBody>> shareStatus = new MediatorLiveData<>();


    //send url request bodies to server when post button is pressed
    //turns object into multipart, returns an observable that is observed by tagging activity, with state

    public TaggingViewModel() {

    }



   public void onSharePressed(){


   }

   public LiveData<StateData<ResponseBody>> observeShareStatus(){
        return shareStatus;
   }

   public void uploadPost(MultipartBody.Part post, RequestBody tags, RequestBody caption){

   }

}
