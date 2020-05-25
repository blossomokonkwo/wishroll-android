package co.WishRoll.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;


import co.WishRoll.Profile.ProfileActivity;
import co.WishRoll.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
//Images are repped as blocks, need to be factored in later with image caching, storage problems



    private static final String TAG = "SearchActivity";
    private Context mContext = SearchActivity.this;
    private static final int ACTIVITY_NUM = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
        Log.d(TAG, "onCreate: starting");

        ImageView profilePictureMain =  findViewById(R.id.profilePicSearch);

        profilePictureMain.setOnClickListener(this);




    }


        @Override
        public void onClick(View v) {

            switch(v.getId()){

               case R.id.profilePicSearch:
                   Log.d(TAG, "onClick: going to main profile page");
                    openUserProfile();
                break;


            }

        }


        public void openUserProfile(){
            Intent flowToProfile = new Intent(this, ProfileActivity.class);
            Log.d(TAG, "openUserProfile: Going into user profile page");
            startActivity(flowToProfile);


        }
}
