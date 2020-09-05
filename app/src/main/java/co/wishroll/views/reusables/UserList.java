package co.wishroll.views.reusables;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.models.domainmodels.User;
import co.wishroll.views.home.MainActivity;
import co.wishroll.views.tools.UserListRecyclerViewAdapter;

public class UserList extends AppCompatActivity {


    private RecyclerView myRecyclerView;
    private List<User> userList;
    private FloatingActionButton fabHome;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        backButton = findViewById(R.id.followersBackButton);
        fabHome = findViewById(R.id.fabFollowersListView);

        userList= new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        User userWhoFollows = new User("blossomfiller", 1, false);

        for(int i = 0; i < 200; i++) {
            userList.add(userWhoFollows);
        }

        myRecyclerView = this.findViewById(R.id.followersRecyclerView);
        UserListRecyclerViewAdapter userListRecyclerViewAdapter = new UserListRecyclerViewAdapter(this, userList);
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
                startActivity(new Intent(UserList.this, MainActivity.class));
                finish();
            }
        });
    }




}