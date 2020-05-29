package co.WishRoll.Profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import co.WishRoll.R;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton ibBackProfile;
    TextView tvPrivacyPolicy, tvLogout, tvContactUs, tvEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_settings);

       tvPrivacyPolicy = findViewById(R.id.tvPrivacyPolicy);
       tvLogout = findViewById(R.id.tvLogout);
       tvContactUs = findViewById(R.id.tvContactUs);
       tvEditProfile = findViewById(R.id.tvEditProfile);
       ibBackProfile = findViewById(R.id.ibBackProfile);


       tvPrivacyPolicy.setOnClickListener(this);
       tvLogout.setOnClickListener(this);
       tvContactUs.setOnClickListener(this);
       tvEditProfile.setOnClickListener(this);
       ibBackProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ibBackProfile:
               startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.tvPrivacyPolicy:
                startActivity(new Intent(this, PrivacyPolicyFrag.class));
                break;
            case R.id.tvLogout:
                //startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.tvContactUs:
                //startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.tvEditProfile:
                //startActivity(new Intent(this, ProfileActivity.class));
                break;
        }


    }
}
