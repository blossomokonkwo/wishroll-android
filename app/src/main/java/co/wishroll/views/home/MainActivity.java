package co.wishroll.views.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.utilities.MainViewPagerAdapter;
import co.wishroll.views.upload.UploadActivity;
import co.wishroll.views.profile.ProfileViewActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CircularImageView profileThumbnail;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchBarFake = findViewById(R.id.etSearchBarMain);

        profileThumbnail = findViewById(R.id.profileMain);
        FloatingActionButton fabUpload = findViewById(R.id.fabUpload);

        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UploadActivity.class));
            }
        });



        profileThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "before navigating to the profile activity");
                startActivity(new Intent(MainActivity.this, ProfileViewActivity.class));
            }
        });

        searchBarFake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });


        ViewPager2 viewPager2 = findViewById(R.id.viewPagerMainView);
        viewPager2.setAdapter(new MainViewPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayoutMainView);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                Log.d(TAG, "inside the onConfigure tab, TabLayout mediator");

                switch (position){

                    case 0:{
                        tab.setText("Trending");

                        break;}

                    case 1:{

                        tab.setText("Messages");

                        break;}

                    default: {
                        tab.setText("Feed");
                        break;
                    }
                }

            }
        });

        tabLayoutMediator.attach();
    }







}