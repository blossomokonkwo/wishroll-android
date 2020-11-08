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

public class UploadsViewModel extends ViewModel {

    int userId;
    PostRepository postRepository = applicationGraph.postRepository();
    MediatorLiveData<StateData<ArrayList<Post>>> listOfUploads = new MediatorLiveData<>();
    final int START_OFFSET = 0;
    int offset = 0;
    int uploadsDataSetSize = 0;

    public UploadsViewModel(int userId) {
        this.userId = userId;
        getFirstPageOfUploads();

    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getUploadsDataSetSize() {
        return uploadsDataSetSize;
    }

    public void setUploadsDataSetSize(int uploadsDataSetSize) {
        this.uploadsDataSetSize = uploadsDataSetSize;
    }


    public void getFirstPageOfUploads(){
        listOfUploads.setValue(StateData.loading((ArrayList<Post>) null));
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getUploadedPosts(userId, START_OFFSET);
        listOfUploads.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {
                if(arrayListStateData.data != null){
                    setUploadsDataSetSize(arrayListStateData.data.size());
                }else{
                    setUploadsDataSetSize(18);
                }

                listOfUploads.setValue(arrayListStateData);
                listOfUploads.removeSource(source);
            }

        });
    }

    public void getMoreUploads(int offsetie){
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getUploadedPosts(userId, offsetie);
        listOfUploads.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {

                listOfUploads.setValue(arrayListStateData);
                listOfUploads.removeSource(source);
            }

        });
    }

    public LiveData<StateData<ArrayList<Post>>> observeUploads(){
        return listOfUploads;
    }


}
