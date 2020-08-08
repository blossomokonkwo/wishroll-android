package co.wishroll.views;

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
import co.wishroll.model.Notification;
import co.wishroll.model.User;
import co.wishroll.utilities.NotificationRecyclerViewAdapter;
import co.wishroll.utilities.UserListRecyclerViewAdapter;

public class Following extends AppCompatActivity {



    private RecyclerView myRecyclerView;
    private List<User> followingList;
    FloatingActionButton fabHome;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        followingList= new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        User userWhoFollows = new User("blossom@gmail.com", "blossom is coo", 19,
                "blossomokonkwo","princess");

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