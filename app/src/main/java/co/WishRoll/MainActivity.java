package co.WishRoll;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView displayWelcome = findViewById(R.id.tvWelcomeMessage);
        TextView displayName = findViewById(R.id.tvDisplayName);
        TextView displayEmail = findViewById(R.id.tvDisplayEmail);
    }
}