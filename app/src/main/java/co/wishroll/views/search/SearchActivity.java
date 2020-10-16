package co.wishroll.views.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.wishroll.R;
import co.wishroll.databinding.ActivitySearchBinding;
import co.wishroll.viewmodel.search.SearchViewModel;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.tools.SearchViewPagerAdapter;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    ActivitySearchBinding activitySearchBinding;
    SearchViewModel searchViewModel;
    SearchView searchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        activitySearchBinding = DataBindingUtil.setContentView(SearchActivity.this, R.layout.activity_search);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        ViewPager2 viewPager2 = findViewById(R.id.viewPagerSearchView);
        activitySearchBinding.setSearchviewmodel(searchViewModel);

        searchBar = activitySearchBinding.getRoot().findViewById(R.id.etSearchBarView);


        //timing of text, on text changed, with view model, on enter listener


        viewPager2.setAdapter(new SearchViewPagerAdapter(SearchActivity.this));
        TextView cancelButton = findViewById(R.id.cancelButton);


        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: EUGHH SUBMIT BUTTON CLICKED CHILE");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: text changed " + newText);
                return false;
            }
        });










        TabLayout tabLayout = findViewById(R.id.tabLayoutSearchView);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {



                switch (position){

                    case 0:{
                        tab.setText("Videos");

                        break;}

                    case 1:{
                        tab.setText("Images");

                        break;}

                    default: {
                        tab.setText("Gifs");
                        break;
                    }
                }

            }
        });

        tabLayoutMediator.attach();



        SearchViewModel.setCurrentFragment(tabLayout.getSelectedTabPosition());






        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SearchViewModel.setCurrentFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //maybe clear lists from livedata??????
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
    }


    public void hideKeyboard(){
        searchBar.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(searchBar.getWindowToken(), 0);
    }

}
