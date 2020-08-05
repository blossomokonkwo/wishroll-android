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
import co.wishroll.model.User;
import co.wishroll.utilities.UserListRecyclerViewAdapter;

public class Followers extends AppCompatActivity {


    private RecyclerView myRecyclerView;
    private List<User> followersList;
    FloatingActionButton fabHome;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        backButton = findViewById(R.id.followersBackButton);
        fabHome = findViewById(R.id.fabFollowersListView);

        followersList= new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        User userWhoFollows = new User("blossom@gmail.com", "bhaddieclip", 19,
                "bhaddieclip","princess");

        for(int i = 0; i < 200; i++) {
            followersList.add(userWhoFollows);
        }

        myRecyclerView = (RecyclerView) this.findViewById(R.id.followersRecyclerView);
        UserListRecyclerViewAdapter userListRecyclerViewAdapter = new UserListRecyclerViewAdapter(this, followersList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myRecyclerView.setAdapter(userListRecyclerViewAdapter);







        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Followers.this, MainActivity.class));
                finish();
            }
        });
    }




}