package co.wishroll.viewmodel.media;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.PostRepository;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.TrendingViewModel;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MoreLikeThisViewModel extends ViewModel {

    public PostRepository postRepository = applicationGraph.postRepository();
    MediatorLiveData<StateData<ArrayList<Post>>> moreLikeThisLiveData = new MediatorLiveData<>();
    int START_OFFSET = 0;
    public static int offset = 0;
    public int dataSetSize = 0;
    int postId = 0;

    public int getDataSetSize() {
        return dataSetSize;
    }

    public void setDataSetSize(int dataSetSize) {
        this.dataSetSize = dataSetSize;
    }

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset) {
        TrendingViewModel.offset = offset;
    }

    public MoreLikeThisViewModel(int postId) {
        this.postId = postId;
        getFirstMoreLikeThisPage();
    }

    public LiveData<StateData<ArrayList<Post>>> observeMoreLikeThis(){
        return moreLikeThisLiveData;
    }


    public void getFirstMoreLikeThisPage(){
        moreLikeThisLiveData.setValue(StateData.loading((ArrayList<Post>) null));
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getMoreLikeThis(START_OFFSET, postId);
        moreLikeThisLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> postStateData) {

                if(postStateData.data != null){
                    setDataSetSize(postStateData.data.size());
                }else{
                    setDataSetSize(15);
                }

                moreLikeThisLiveData.setValue(postStateData);
                moreLikeThisLiveData.removeSource(source);
            }
        });
    }



    public void getMoreLikeThese(int offsetie){
        final LiveData<StateData<ArrayList<Post>>> source = postRepository.getMoreLikeThis(offsetie, postId);
        moreLikeThisLiveData.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> postStateData) {
                moreLikeThisLiveData.setValue(postStateData);
                moreLikeThisLiveData.removeSource(source);
            }
        });
    }





}
