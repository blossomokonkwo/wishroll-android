package co.wishroll.viewmodel.search;

import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
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
    private static final String TAG = "SearchViewModel";
    public static MediatorLiveData<StateData<ArrayList<Post>>> listOfVideoMedia = new MediatorLiveData<>();
    public static MediatorLiveData<StateData<ArrayList<Post>>> listOfImageMedia = new MediatorLiveData<>();
    public static MediatorLiveData<StateData<ArrayList<Post>>> listOfGifMedia = new MediatorLiveData<>();
    //three different mediator live datas?????
    static SearchRepository searchRepository = applicationGraph.searchRepository();

    public static String query;
    public static int currentFragment;
    public String dummyText;



    static int START_OFFSET = 0;
    public static int offset = 0; //nervous for the offsets, they might need to live in the activity? or have multiple different ones?????????
    public int dataSetSize = 0;

    public static int getOffset() {
        return offset;
    }

    public static void setOffset(int offset) {
        SearchViewModel.offset = offset;
    }

    private enum ContentType {
        VIDEO, IMAGES, GIF
    };
    
    
    public SearchViewModel() {

    }

   

    public int getCurrentFragment() {
        return currentFragment;
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

    @BindingAdapter("onEnterPressed")
    public static void onEnterButtonPressed(EditText editText, String query){


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                SearchViewModel.performFirstSearch();
                return false;
            }
        });
    }

    public void onTextChanged(CharSequence s, int start, int before, int count){

        

        SearchViewModel.query = s.toString();
        /*if(s != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: SEARCH REQUEST BEING MADE FOR FRAGMENT " + " WITH SEARCH QUERY: " + s);
                }
            }, 500);

        }*/

    }
    
    
    
    
    public static void performFirstSearch(){

        if(currentFragment == 0){
            listOfVideoMedia.setValue(StateData.loading((ArrayList<Post>) null));

            final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, query, ContentType.VIDEO.toString());
            listOfVideoMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> videoSearchStateData) {
                    Log.d(TAG, "onChanged: LETS HOPE THIS WORKS VIDEO:  " + videoSearchStateData.data.size());
                    /*if(videoSearchStateData.data != null){
                        //setDataSetSize(trendingTagStateData.data.size());
                    }else{
                        //setDataSetSize(8);
                    }*/

                    listOfVideoMedia.setValue(videoSearchStateData);
                    listOfVideoMedia.removeSource(source);
                }
            });

           
            

        }else if(currentFragment == 1){
            listOfImageMedia.setValue(StateData.loading((ArrayList<Post>) null));
            final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, query, ContentType.IMAGES.toString());
            listOfImageMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> imageSearchStateData) {

                   /* if(imageSearchStateData.data != null){
                        //setDataSetSize(trendingTagStateData.data.size());
                    }else{
                        //setDataSetSize(8);
                    }*/

                    listOfVideoMedia.setValue(imageSearchStateData);
                    listOfVideoMedia.removeSource(source);
                }
            });

        
            
        }else if(currentFragment == 2){

            listOfGifMedia.setValue(StateData.loading((ArrayList<Post>) null));
            final LiveData<StateData<ArrayList<Post>>> source = searchRepository.getSearchResults(START_OFFSET, query, ContentType.GIF.toString());
            listOfGifMedia.addSource(source, new Observer<StateData<ArrayList<Post>>>() {
                @Override
                public void onChanged(StateData<ArrayList<Post>> gifSearchStateData) {
                    Log.d(TAG, "onChanged: LETS HOPE THIS WORKS GIF:  " + gifSearchStateData.data.size());

                    /*if(gifSearchStateData.data != null){
                        //setDataSetSize(trendingTagStateData.data.size());
                    }else{
                        //setDataSetSize(8);
                    }*/

                    listOfGifMedia.setValue(gifSearchStateData);
                    listOfGifMedia.removeSource(source);
                }
            });

            
        }else{
            //lol idk
        }
        Log.d(TAG, "performSearch: this is the query: " + query);
        Log.d(TAG, "performSearch: this is the fragment we are on " + currentFragment);
    }

    public static  void getMoreSearchResults(){


        if(currentFragment == 0){
            listOfVideoMedia.setValue(StateData.loading((ArrayList<Post>) null));

            Log.d(TAG, "getMoreSearchResults: getting more videos to append");
            

            //make network call to update live data but with a video content type

        }else if(currentFragment == 1){
            listOfImageMedia.setValue(StateData.loading((ArrayList<Post>) null));
            
            
            
            

            //make network call to update live data but with an image content type

        }else if(currentFragment == 2){
            listOfGifMedia.setValue(StateData.loading((ArrayList<Post>) null));
            
            
            
            

            //make network call update livedata but with a gif content type
        }else{
            //lol idk
        }

    }

    public LiveData<StateData<ArrayList<Post>>> observeSearchResults(){

        if(currentFragment == 0){
            //make network call to update live data but with a video content type
                return listOfVideoMedia;
        }else if(currentFragment == 1){
            //make network call to update live data but with an image content type
                return listOfImageMedia;
        }else if(currentFragment == 2){
            //make network call update livedata but with a gif content type
            return listOfGifMedia;
        }else{
            return null;
            //lol idk
        }
    }
    
   







}
