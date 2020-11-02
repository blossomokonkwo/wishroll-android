package co.wishroll.viewmodel.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import co.wishroll.models.domainmodels.Post;
import co.wishroll.models.repository.SearchRepository;
import co.wishroll.utilities.StateData;

import static co.wishroll.WishRollApplication.applicationGraph;

public class SearchViewModel extends ViewModel {
    public static  MediatorLiveData<StateData<ArrayList<Post>>> listOfVideoMedia = new MediatorLiveData<>();
    public  static MediatorLiveData<StateData<ArrayList<Post>>> listOfImageMedia = new MediatorLiveData<>();
    public  static MediatorLiveData<StateData<ArrayList<Post>>> listOfGifMedia = new MediatorLiveData<>();
    //three different mediator live datas?????
    static SearchRepository searchRepository = applicationGraph.searchRepository();

    public static String query;
    public static int currentFragment;




    static int START_OFFSET = 0;
    public static int offset = 0; //nervous for the offsets, they might need to live in the activity? or have multiple different ones?????????
    public static int videoDataSetSize = 0;
    public static int gifDataSetSize = 0;
    public static int imageDataSetSize = 0;


    public static int getVideoDataSetSize() {
        return SearchViewModel.videoDataSetSize;
    }

    public static void setVideoDataSetSize(int videoDataSetSize) {
        SearchViewModel.videoDataSetSize = videoDataSetSize;
    }

    public static int getGifDataSetSize() {
        return SearchViewModel.gifDataSetSize;
    }

    public static void setGifDataSetSize(int gifDataSetSize) {
        SearchViewModel.gifDataSetSize = gifDataSetSize;
    }

    public static int getImageDataSetSize() {
        return SearchViewModel.imageDataSetSize;
    }

    public static void setImageDataSetSize(int imageDataSetSize) {
        SearchViewModel.imageDataSetSize = imageDataSetSize;
    }

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset) {
        SearchViewModel.offset = offset;
    }

    private enum ContentType {
        video, image, gif
    };
    
    
    public SearchViewModel() {

    }

   

    public static int getCurrentFragment() {
        return SearchViewModel.currentFragment;
    }

    public static void setCurrentFragment(int currentFragment) {
        SearchViewModel.currentFragment = currentFragment;
    }

    public static String getQuery() {
        return query;
    }

    public static void setQuery(String query) {
        SearchViewModel.query = query;

    }



    
    
    
    
    public static void performFirstSearch(){


        if(SearchViewModel.getCurrentFragment() == 0){


                listOfVideoMedia.setValue(StateData.loading((ArrayList<Post>) null));

                final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, query, ContentType.video.toString());
                listOfVideoMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                    @Override
                    public void onChanged(StateData<ArrayList<Post>> videoSearchStateData) {
                    if(videoSearchStateData.data != null){
                        setVideoDataSetSize(videoSearchStateData.data.size());
                    }else{
                        setVideoDataSetSize(18);
                    }

                        listOfVideoMedia.setValue(videoSearchStateData);
                        listOfVideoMedia.removeSource(source);
                    }
                });


           
            

        }else if(SearchViewModel.getCurrentFragment() == 1){


                listOfImageMedia.setValue(StateData.loading((ArrayList<Post>) null));
                final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, query, ContentType.image.toString());
                listOfImageMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                    @Override
                    public void onChanged(StateData<ArrayList<Post>> imageSearchStateData) {

                        if(imageSearchStateData.data != null){
                            setImageDataSetSize(imageSearchStateData.data.size());
                        }else{
                            setImageDataSetSize(18);
                        }

                        listOfImageMedia.setValue(imageSearchStateData);
                        listOfImageMedia.removeSource(source);
                    }
                });


        
            
        }else if(SearchViewModel.getCurrentFragment() == 2){


                listOfGifMedia.setValue(StateData.loading((ArrayList<Post>) null));
                final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, query, ContentType.gif.toString());
            //Log.d(TAG, "performFirstSearch: VALUE OF SOURCE GIF: " + source.getValue().status);
                listOfGifMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                    @Override
                    public void onChanged(StateData<ArrayList<Post>> gifSearchStateData) {

                        if(gifSearchStateData.data != null){
                            setGifDataSetSize(gifSearchStateData.data.size());
                        }else{
                            setGifDataSetSize(18);
                        }

                        listOfGifMedia.setValue(gifSearchStateData);
                        listOfGifMedia.removeSource(source);
                    }
                });


            
        }else{
            //lol idk
        }

    }

    public static void getMoreSearchResults(int offsetie){


        if(SearchViewModel.getCurrentFragment() == 0){
            final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(offsetie, query, ContentType.video.toString());
            listOfVideoMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> videoStateData) {
                    listOfVideoMedia.setValue(videoStateData);
                    listOfVideoMedia.removeSource(source);
                }
            });



            //make network call to update live data but with a video content type

        }else if(SearchViewModel.getCurrentFragment() == 1){


            final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(offsetie, query, ContentType.image.toString());
            listOfImageMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> imageStateData) {
                    listOfImageMedia.setValue(imageStateData);
                    listOfImageMedia.removeSource(source);
                }
            });
            

            //make network call to update live data but with an image content type

       /* }else if(SearchViewModel.getCurrentFragment() == 2){
            final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(offsetie, query, ContentType.gif.toString());
            listOfGifMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> gifStateData) {
                    listOfGifMedia.setValue(gifStateData);
                    listOfGifMedia.removeSource(source);
                }
            });
            */
            
            

            //make network call update livedata but with a gif content type
        }else{
            //lol idk
        }

    }




    public LiveData<StateData<ArrayList<Post>>> observeSearchResults(){

        if(SearchViewModel.getCurrentFragment() == 0){
            //make network call to update live data but with a video content type
                return listOfVideoMedia;
        }else if(SearchViewModel.getCurrentFragment() == 1){
            //make network call to update live data but with an image content type
                return listOfImageMedia;
        }else if(SearchViewModel.getCurrentFragment() == 2){
            //make network call update livedata but with a gif content type
            return listOfGifMedia;
        }else{
            return null;
            //lol idk
        }
    }

    public static void onSearchingEmpty(){
        listOfGifMedia.setValue(StateData.notauthenticated((ArrayList<Post>)null));
        listOfVideoMedia.setValue(StateData.notauthenticated((ArrayList<Post>)null));
        listOfImageMedia.setValue(StateData.notauthenticated((ArrayList<Post>)null));

    }

    public static void clearSpecificTab(int position){
        if(position == 0){
            listOfVideoMedia.setValue(StateData.notauthenticated((ArrayList<Post>) null));
        }else if(position == 1){
            listOfImageMedia.setValue(StateData.notauthenticated((ArrayList<Post>) null));
        }else if(position == 2 ){
            listOfGifMedia.setValue(StateData.notauthenticated((ArrayList<Post>) null));
        }else{
            //do nothing
        }
    }
    
   







}
