package co.wishroll.views.reusables;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.entities.Videos;
import co.wishroll.utilities.VideoRecyclerViewAdapter;

public class VideoActivity extends AppCompatActivity {

    private List<Videos> videosList = new ArrayList<>();
    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private ImageButton backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        backButton = findViewById(R.id.backVideoView);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView = (RecyclerView)  findViewById(R.id.videoRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

       for(int i = 0; i < 10; i++ ) {
           videosList.add(new Videos());
       }

       videoRecyclerViewAdapter = new VideoRecyclerViewAdapter(videosList, getApplicationContext());
       recyclerView.setAdapter(videoRecyclerViewAdapter);
        videoRecyclerViewAdapter.notifyDataSetChanged();





    }



}