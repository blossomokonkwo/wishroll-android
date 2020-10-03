package co.wishroll.views.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import co.wishroll.R;

public class PostsGridActivity extends AppCompatActivity {

    TextView activityTitle;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //literally going to be used for any mass grid viewing
        setContentView(R.layout.activity_trendinggrid);
        activityTitle = findViewById(R.id.tagGridTitle);
        backButton = findViewById(R.id.backTrendingGrid);


        String title = getIntent().getStringExtra("query");
        activityTitle.setText(title);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




    }
}