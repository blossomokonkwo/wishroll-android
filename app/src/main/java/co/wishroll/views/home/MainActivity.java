package co.wishroll.views.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.wishroll.R;
import co.wishroll.databinding.ActivityMainBinding;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.viewmodel.MainViewModel;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    MainViewModel mainViewModel;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    private BottomNavigationView bottomNavigationView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        activityMainBinding.setMainviewmodel(mainViewModel);

        bottomNavigationView = activityMainBinding.getRoot().findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        bottomNavigationView.setItemIconTintList(null);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
















    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;
            switch(item.getItemId()){
                case R.id.homeBottomNav:
                    fragment = new HomeFragment();
                    break;
                case R.id.searchBottomNav:
                    fragment = new SearchFragment();
                    break;
                case R.id.uploadBottomNav:

                    break;
                case R.id.notifBottomNav:
                    fragment = new NotificationsFragment();
                    break;
                case R.id.profileBottomNav:
                    fragment = new ProfileFragment();
                    break;
                default:
                    fragment = new HomeFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            return true;
        }
    };





















}