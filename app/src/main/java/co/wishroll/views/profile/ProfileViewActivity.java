package co.wishroll.views.profile;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import co.wishroll.R;
import co.wishroll.models.repository.local.SessionManagement;

import static co.wishroll.WishRollApplication.applicationGraph;

public class ProfileViewActivity extends AppCompatActivity {
    SessionManagement sessionManagement = applicationGraph.sessionManagement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        int userId = getIntent().getIntExtra("userId", 0);
        boolean isCurrentUser = false;

        if(userId == sessionManagement.getCurrentUserId() ){
            isCurrentUser = true;
        }else{
            isCurrentUser = false;
        }


    }
}