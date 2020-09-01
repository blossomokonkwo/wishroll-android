package co.wishroll.views.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.databinding.ActivityMainBinding;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.viewmodel.MainViewModel;
import co.wishroll.views.profile.ProfileViewActivity;
import co.wishroll.views.tools.MainViewPagerAdapter;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MAIN ACTIVITY";
    ActivityMainBinding activityMainBinding;
    MainViewModel mainViewModel;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    CircularImageView profileThumbnail;
    FloatingActionButton fabUpload;
    EditText searchBarFake;
    private static final int PERMISSION_CODE = 1001;
    private static final int IMAGE_PICK_CODE = 1000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        activityMainBinding.setMainviewmodel(mainViewModel);

        fabUpload = findViewById(R.id.fabUpload);
        searchBarFake = findViewById(R.id.etSearchBarMain);
        profileThumbnail = findViewById(R.id.profileMain);







        fabUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permissions ={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);

                    }else{

                    }

                }else{
                    goToGallery();
                }



            }
        });



        profileThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "before navigating to the profile activity");
                Intent intent = new Intent(MainActivity.this, ProfileViewActivity.class);
                startActivity(intent);
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

                   /* case 1:{

                        tab.setText("Messages");

                        break;}*/

                    default: {

                        tab.setText("Feed");

                        break;
                    }
                }

            }
        });

        tabLayoutMediator.attach();

        loadProfileCircle();
    }

    public void goToGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }



    public void loadProfileCircle(){
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.defaultprofile)
                .error(R.drawable.defaultprofile);
        Glide.with(MainActivity.this).load(sessionManagement.getAvatarURL()).apply(options).into(profileThumbnail);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    goToGallery();
                }else{

                }
                break;
        }
    }
}