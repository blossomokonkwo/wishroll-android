package co.wishroll.viewmodel;

import android.util.Log;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mikhaellopez.circularimageview.CircularImageView;

import co.wishroll.R;
import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.local.SessionManagement;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    SessionManagement sessionManagement = applicationGraph.sessionManagement();
    UserRepository userRepository = applicationGraph.userRepository();
    boolean isDeleted = false;



    public MainViewModel() {
    }







}