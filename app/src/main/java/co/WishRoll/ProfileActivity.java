package co.WishRoll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import co.WishRoll.R;

public class ProfileActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        viewPager2 = findViewById(R.id.viewPagerProfileView);
        viewPager2.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabLayoutProfileView);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(
                tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        //tab.setText("Uploads");
                        tab.setIcon(R.drawable.ic_uploads);
                        break;}
                    case 1:{
                        //tab.setText("Likes");
                        tab.setIcon(R.drawable.ic_likes);
                        break;}
                    case 2:{
                        //tab.setText("Bookmarks");
                        tab.setIcon(R.drawable.ic_bookmark);
                        break;}
                    default: {
                        //tab.setText("Notifications");
                        tab.setIcon(R.drawable.ic_notifications);
                        break;
                    }
                }

            }
        }
        );
        tabLayoutMediator.attach();








    }
}