package co.wishroll.views.upload;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import co.wishroll.R;
import co.wishroll.databinding.ActivityTaggingBinding;
import co.wishroll.viewmodel.TaggingViewModel;

public class TaggingActivity extends AppCompatActivity {
    private static final String TAG = "TaggingActivity";

    ActivityTaggingBinding activityTaggingBinding;
    TaggingViewModel taggingViewModel;

    private TextView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_tagging);


        activityTaggingBinding = DataBindingUtil.setContentView(this, R.layout.activity_tagging);
        taggingViewModel = new ViewModelProvider(this).get(TaggingViewModel.class);
        activityTaggingBinding.setTaggingviewmodel(taggingViewModel);


        backButton = findViewById(R.id.taggingBack);


    }
}