package co.wishroll.views.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import co.wishroll.R;
import co.wishroll.databinding.ActivitySearchBinding;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.tools.SearchViewPagerAdapter;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    ActivitySearchBinding activitySearchBinding;

    SearchView searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBar = findViewById(R.id.etSearchBarView);

        ViewPager2 viewPager2 = findViewById(R.id.viewPagerSearchView);

        //timing of text, on text changed, with view model, on enter listener

        viewPager2.setAdapter(new SearchViewPagerAdapter(SearchActivity.this));

        TextView cancelButton = findViewById(R.id.cancelButton);








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

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                finish();
            }
        });
    }

}
