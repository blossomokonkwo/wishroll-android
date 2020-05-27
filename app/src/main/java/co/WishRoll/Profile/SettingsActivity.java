package co.WishRoll.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import co.WishRoll.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibBackProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_settings);





       ibBackProfile = findViewById(R.id.ibBackProfile);
       ibBackProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ibBackProfile:
               startActivity(new Intent(this, ProfileActivity.class));
                break;
        }


    }
}
