package co.wishroll.views.reusables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.User;
import co.wishroll.utilities.UserListRecyclerViewAdapter;
import co.wishroll.views.home.MainActivity;

public class Following extends AppCompatActivity {



    private RecyclerView myRecyclerView;
    private List<User> followingList;
    private FloatingActionButton fabHome;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        followingList= new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        User userWhoFollows = new User("blossomfiller", 1, false);

        for(int i = 0; i < 200; i++) {
            followingList.add(userWhoFollows);
        }

        myRecyclerView = (RecyclerView) this.findViewById(R.id.followingRecyclerView);
        UserListRecyclerViewAdapter userListRecyclerViewAdapter = new UserListRecyclerViewAdapter(this, followingList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(userListRecyclerViewAdapter);





        backButton = findViewById(R.id.backFollowingView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fabHome = findViewById(R.id.fabFollowingListView);

        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Following.this, MainActivity.class));
                finish();
            }
        });
    }


}